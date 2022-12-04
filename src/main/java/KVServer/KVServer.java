package KVServer;

import java.net.*;
import java.io.*;

public class KVServer {


    public static void main(String[] args) {
        System.out.println("Welcome to Server side of Key-Value Database!");

        ParametersController parametersController = new ParametersController();
        parametersController.readParameters(args);

        String address = parametersController.getIpAddress();
        int port = parametersController.getPort();
        Socket socket = null;
        ServerSocket server = null;

        // starts server and waits for a connection
        try {
            InetAddress addr = InetAddress.getByName(address);
            server = new ServerSocket(port, 0, addr);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String query = "";
//            String answer;
//            QueryExecutor queryExecutor = new QueryExecutor();

            // reads message from client until "OVER" is sent
            while (!query.equals("OVER")) {
                try {
                    query = in.readUTF();
//                    answer = queryExecutor.execute(query);
//                    out.writeUTF(answer);
                    System.out.println("client send: " + query);
                    out.writeUTF("server: " + query);
                    out.flush();
                } catch(IOException i) {
                    i.printStackTrace();
                }
            }

            // close connection
            System.out.println("Closing connection");
            socket.close();
            in.close();
            out.close();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }
}
