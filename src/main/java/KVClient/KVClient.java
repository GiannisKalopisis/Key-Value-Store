package KVClient;

import GeneralCode.ReadFile;

import java.util.ArrayList;
import java.util.Map;

import java.net.*;
import java.io.*;

public class KVClient {

    public KVClient(ArrayList<ServerInfo> servers, ArrayList<String> dataToIndex) {

        ArrayList<SocketStructure> sockets = new ArrayList<>();

        // create all servers - sockets
        for (int i = 0; i < servers.size(); i++) {
            SocketStructure newSocket = new SocketStructure(i+1);
            newSocket.initNewSocket(servers.get(i).getIpAddress(), servers.get(i).getPort());
            sockets.add(newSocket);
        }

        String line = "";
        DataInputStream terminalInput = new DataInputStream(System.in);

        // keep reading until "OVER" is terminalInput
        while (!line.equals("OVER")) {
            try {
                line = terminalInput.readLine();
                for (int i = 0; i < sockets.size(); i++) {
                    sockets.get(i).writeToSocket(line);
                    String serverResponse = sockets.get(i).readFromSocket();
                    System.out.println(serverResponse);
                }
            } catch(IOException i) {
                System.out.println(i);
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

        new KVClient(servers, dataToIndex);
    }
}
