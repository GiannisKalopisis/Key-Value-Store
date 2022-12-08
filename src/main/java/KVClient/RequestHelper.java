package KVClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static GeneralCode.RandomGenerators.getRandomInt;

public class RequestHelper {

    public boolean replicateInitDataToServers(ArrayList<SocketStructure> sockets, ArrayList<String> dataToIndex, int replicationFactor) {

        int[] serverRepArray = new int[replicationFactor];

        Arrays.fill(serverRepArray, -1);

        for (int i = 0; i < dataToIndex.size(); i++) {
            int j = 0;
            int downServers = kServersAreDown(sockets);
            if (downServers != 0) {
                System.out.println("Couldn't complete index process because " + downServers + " are down.");
                System.out.println("Shutting down...");
                return false;
            }
            String put = "PUT " + dataToIndex.get(i);
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
                    System.out.println("Indexing process will terminate. Start again.");
                    return false;
                }
                serverRepArray[j] = randomServer;
                j++;
            }
            System.out.println(put);
            System.out.println(response);
            System.out.println("=========================o=========================");
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

    public int kServersAreDown(ArrayList<SocketStructure> sockets) {
        int serversDown = 0;
        for (int i = 0; i < sockets.size(); i++) {
            sockets.get(i).writeToSocket("PING");
            if (!Objects.equals(sockets.get(i).readFromSocket(), "OK")) {
                serversDown++;
                System.out.println("Server with IP " + sockets.get(i).getSocket().getInetAddress() +
                        " at port " + sockets.get(i).getSocket().getPort() + " is down.");
            }
        }
        return serversDown;
    }

    public void processAndSendRequest(ArrayList<SocketStructure> sockets,String request, int replicationFactor) {
        int downServers = kServersAreDown(sockets);
        String[] requestParts = request.split(" ");
        switch (requestParts[0]) {
            case "GET":
                if ( downServers >= replicationFactor) {
                    System.out.println("Cannot perform GET because " + downServers + " are down. " +
                            "System can handle up to " + (replicationFactor - 1) + " server faults.");
                } else {
                    performGet(sockets, request);
                }
                return;
            case "DELETE":
                if (downServers != 0) {
                    System.out.println("Cannot perform DELETE because not all servers are up and running.");
                } else {
                    performDeletion(sockets, request, replicationFactor);
                }
                return;
            case "QUERY":
                if ( downServers >= replicationFactor) {
                    System.out.println("Cannot perform QUERY because " + downServers + " are down. " +
                            "System can handle up to " + (replicationFactor - 1) + " server faults.");
                } else {
                    performQuery(sockets, request);
                }
                return;
            case "COMPUTE":
                if ( downServers >= replicationFactor) {
                    System.out.println("Cannot perform COMPUTE because " + downServers + " are down. " +
                            "System can handle up to " + (replicationFactor - 1) + " server faults.");
                } else {
                    // do COMPUTE
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
        } else {
            System.out.println("Couldn't delete it from all servers. Deleted only at (" + found + ") servers.");
        }
    }

    private static void performQuery(ArrayList<SocketStructure> sockets, String request) {
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
}
