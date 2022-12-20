package KVClient;

import GeneralCode.ReadFile;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class KVClient {

    public KVClient(ArrayList<ServerInfo> servers, ArrayList<String> dataToIndex, ParametersController parametersController) {

        ArrayList<SocketStructure> sockets = new ArrayList<>();

        // create all servers - sockets
        for (int i = 0; i < servers.size(); i++) {
            SocketStructure newSocket = new SocketStructure(i+1);
            if (newSocket.initNewSocket(servers.get(i).getIpAddress(), servers.get(i).getPort())) {
                sockets.add(newSocket);
            }
        }
        parametersController.setReplicationFactor(sockets.size());

        String line = "";
        DataInputStream terminalInput = new DataInputStream(System.in);

        // send init data
        RequestHelper requestHelper = new RequestHelper();
        long startTime = System.currentTimeMillis();
        boolean indexingDone = requestHelper.replicateInitDataToServers(sockets, dataToIndex, parametersController.getReplicationFactor());
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;
        long milliseconds = TimeUnit.MILLISECONDS.toMillis(elapsedTime) % 1000;
        System.out.println("Indexing completed after: " + hours + " hours " + minutes + " minutes " + seconds + " seconds " + milliseconds + " milliseconds");
        System.out.println("You can start doing queries: GET, DELETE, QUERY, COMPUTE");
        System.out.println("==================================================");

        // keep reading until "OVER" is terminalInput
        if (indexingDone) {
            while (!line.equals("OVER")) {
                try {
                    line = terminalInput.readLine();
                    requestHelper.processAndSendRequest(sockets, line, parametersController.getReplicationFactor());
                    System.out.println("==================================================");
                } catch(IOException i) {
                    System.out.println("An IOException occurred while reading from terminal. Try again.");
                }
            }
        }

        // close the connection
        System.out.println("Closing sockets...");
        for (int i = 0; i < sockets.size(); i++) {
            sockets.get(i).closeSocket();
        }
        try {
            System.out.println("Closing terminal input connection...");
            terminalInput.close();
        }
        catch(IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }


    public static void main(String[] args) {
        System.out.println("Welcome to Client side of Key-Value Database!");

        ParametersController parametersController = new ParametersController();
        parametersController.readParameters(args);

        ArrayList<ServerInfo> servers = ReadFile.getServersFromFile(parametersController.getServerFilePath());
        ArrayList<String> dataToIndex = ReadFile.readDataToIndexFromFile(parametersController.getDataToIndexPath());

        new KVClient(servers, dataToIndex, parametersController);
    }
}
