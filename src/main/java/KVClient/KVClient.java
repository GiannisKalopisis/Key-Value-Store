package KVClient;

import GeneralCode.ReadFile;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

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
        boolean indexingDone = requestHelper.replicateInitDataToServers(sockets, dataToIndex, parametersController.getReplicationFactor());

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
        for (int i = 0; i < sockets.size(); i++) {
            sockets.get(i).closeSocket();
        }
        try {
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
