package KVClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static GeneralCode.RandomGenerators.getRandomInt;

public class RequestHelper {

    public boolean replicateInitDataToServers(ArrayList<SocketStructure> sockets, ArrayList<String> dataToIndex, int replicationFactor) {

        int[] serverRepArray = new int[replicationFactor];

        Arrays.fill(serverRepArray, -1);
        System.out.println("\n\n");

        for (int i = 0; i < dataToIndex.size(); i++) {
            int j = 0;
            ArrayList<SocketStructure> subSockets = kServersAreDown(sockets);
            if ((sockets.size() - subSockets.size()) != 0) {
                System.out.println("Couldn't complete index process because " + (sockets.size() - subSockets.size()) + " are down.");
                System.out.println("Shutting down...");
                return false;
            }
            String put = "PUT " + dataToIndex.get(i);
            put = put.trim().replaceAll("\\s+", " ");
            System.out.println(put);
            String response = "";
            while (j < replicationFactor){
                int randomServer = getRandomInt(sockets.size() - 1);
                if (containedIntoServerArray(randomServer, serverRepArray)) {
                    continue;
                }
                sockets.get(randomServer).writeToSocket(put);
                response = sockets.get(randomServer).readFromSocket();
                if (response.equals("ERROR")) {
                    System.out.println("An error occurred while performing \"" + put + "\" request at server " +
                            sockets.get(randomServer).getSocket().getInetAddress() + " with port " +
                            sockets.get(randomServer).getSocket().getPort());
                    System.out.println("Server will not index that entry...");
                    break;
                }
                serverRepArray[j] = randomServer;
                j++;
            }
            System.out.println(response);
            System.out.println("==================================================");
            Arrays.fill(serverRepArray,-1);
        }
        return true;
    }

    private boolean containedIntoServerArray(int randomServer, int[] serverRepArray){
        for (int j : serverRepArray) {
            if (randomServer == j) return true;
        }
        return false;
    }

    public ArrayList<SocketStructure> kServersAreDown(ArrayList<SocketStructure> sockets) {
        ArrayList<SocketStructure> subSockets = new ArrayList<>();
        for (int i = 0; i < sockets.size(); i++) {
            sockets.get(i).writeToSocket("PING");
            if (!Objects.equals(sockets.get(i).readFromSocket(), "OK")) {
                System.out.println("Server with IP " + sockets.get(i).getSocket().getInetAddress() +
                        " at port " + sockets.get(i).getSocket().getPort() + " is down.");
            } else {
                subSockets.add(sockets.get(i));
            }
        }
        return subSockets;
    }

    public void processAndSendRequest(ArrayList<SocketStructure> sockets,String request, int replicationFactor) {
        request = request.trim().replaceAll("\\s+", " ");
        String[] requestParts = request.split(" ");
        if (requestParts.length == 1) {
            System.out.println("Wrong request syntax. Please give request arguments");
            System.out.println("Not sending it to servers.");
            return;
        }
        ArrayList<SocketStructure> subSockets = kServersAreDown(sockets);
        switch (requestParts[0]) {
            case "GET":
                if ((sockets.size() - subSockets.size()) >= replicationFactor) {
                    System.out.println("Cannot perform GET because " + (sockets.size() - subSockets.size()) + " server(s) is/are down. " +
                            "System can handle up to " + (replicationFactor - 1) + " server faults.");
                } else {
                    performGet(subSockets, request);
                }
                return;
            case "DELETE":
                if ((sockets.size() - subSockets.size()) != 0) {
                    System.out.println("Cannot perform DELETE because not all servers are up and running.");
                } else {
                    performDeletion(subSockets, request, replicationFactor);
                }
                return;
            case "QUERY":
                if ((sockets.size() - subSockets.size()) >= replicationFactor) {
                    System.out.println("Cannot perform QUERY because " + (sockets.size() - subSockets.size()) + " server(s) is/are down. " +
                            "System can handle up to " + (replicationFactor - 1) + " server faults.");
                } else {
                    performQuery(subSockets, request);
                }
                return;
            case "COMPUTE":
                if ((sockets.size() - subSockets.size()) >= replicationFactor) {
                    System.out.println("Cannot perform COMPUTE because " + (sockets.size() - subSockets.size()) + " server(s) is/are down. " +
                            "System can handle up to " + (replicationFactor - 1) + " server faults.");
                } else {
                    // do COMPUTE
                    performCompute(subSockets, request);
                }
                return;
            default:
                System.out.println("Wrong request type. Request must be: GET, DELETE, QUERY, COMPUTE.");
                System.out.println("Not sending it to servers.");
        }
    }

    private static void performGet(ArrayList<SocketStructure> sockets, String request) {
        String serverResponse = null;
        for (SocketStructure socket : sockets) {
            socket.writeToSocket(request);
            serverResponse = socket.readFromSocket();
            if (!serverResponse.equals("NOT FOUND")) {
                System.out.println(serverResponse);
                return;
            }
        }
        System.out.println(serverResponse);
    }

    private static void performDeletion(ArrayList<SocketStructure> sockets, String request, int replicationFactor) {
        String serverResponse;
        int found = 0;
        int notFound = 0;
        for (SocketStructure socket : sockets) {
            socket.writeToSocket(request);
            serverResponse = socket.readFromSocket();
            if (serverResponse.equals("OK")) {
                found++;
            }
            if (serverResponse.equals("NOT FOUND")) {
                notFound++;
            }
        }
        if (notFound == sockets.size()) {
            System.out.println("NOT FOUND");
        }
        if (found == replicationFactor) {
            System.out.println("Deleted successfully from all servers (" + found + ").");
        }
    }

    private static void performQuery(ArrayList<SocketStructure> sockets, String request) {
        String serverResponse = null;
        for (SocketStructure socket : sockets) {
            socket.writeToSocket(request);
            serverResponse = socket.readFromSocket();
            if (!serverResponse.equals("NOT FOUND")) {
                System.out.println(formatResponse(request,serverResponse));
                return;
            }
        }
        System.out.println(serverResponse);
    }

    private static String formatResponse(String request, String response) {
        String queryPath = request.split(" ", 2)[1];
        if (response.contains("->")) {
            return queryPath + " -> [ " + response + " ]";
        }
        return queryPath + " -> " + response;
    }

    private static void performCompute(ArrayList<SocketStructure> sockets, String request) {

        Compute computeClass = new Compute(request);
        computeClass.exportMathExpression();
        if (!computeClass.exportQueries()) {
            System.out.println("Parameters can't have the same name (or substring) of trigonometric and logarithmic functions.");
            System.out.println("Rename parameters and try again.");
            return;
        }
        String response = computeClass.sendQueries(sockets);
        if (!response.equals("OK")) {
            System.out.println(response);
            return;
        }
        String mathExpWithNumbers = computeClass.replaceVarsWithVals();
        MathExpressionEvaluator evaluator = new MathExpressionEvaluator();
        try {
            System.out.println(evaluator.evaluate(mathExpWithNumbers));
        } catch (Exception e) {
            System.err.println("Couldn't evaluate the mathematical expression. Check again:");
            System.err.println("(1) the parenthesis and the syntax of the expression");
            System.err.println("(2) the names of trigonometric and logarithmic functions");
            System.out.println("(3) the names of variables to match the variables of the queries.");
        }
    }
}
