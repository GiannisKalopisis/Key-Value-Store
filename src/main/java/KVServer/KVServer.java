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
        Socket socket;
        ServerSocket server;

        // starts server and waits for a connection
        try {
            InetAddress addr = InetAddress.getByName(address);
            server = new ServerSocket(port, 0, addr);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");
            System.out.println("==================================================");


            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String query;
            String answer;
            RequestHandler handler = new RequestHandler();

            while ((query = in.readLine()) != null){
                System.out.println("Query: '" + query + "'");
                answer = handler.execute(query);
                System.out.println(answer);
                out.println(answer);
                out.flush();
                System.out.println("==================================================");
            }

            // close connection
            System.out.println("\n\nClosing connection");
            socket.close();
            in.close();
            out.close();
            server.close();
        } catch(IOException i) {
            i.printStackTrace();
        }
    }
}
