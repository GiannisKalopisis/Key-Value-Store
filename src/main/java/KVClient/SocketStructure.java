package KVClient;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SocketStructure {

    private int socketId;
    private Socket socket = null;
    private BufferedReader socketInput = null;
    private PrintWriter socketOutput = null;

    public SocketStructure(int socketId) {
        this.socketId = socketId;
    }

    public Socket getSocket() {
        return socket;
    }

    public void initNewSocket(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to server with IP " + address + ", at port " + port + ".");
            socketOutput = new PrintWriter(socket.getOutputStream(), true);
            socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch(UnknownHostException unknownHostException) {
            System.err.println("UnknownHostException error while connecting to servers.");
            unknownHostException.printStackTrace();
            System.exit(-1);
        } catch(IOException ioException) {
            System.out.println("An IOException occurred. Try to start servers first and then client.");
            ioException.printStackTrace();
            System.exit(-1);
        }
    }

    public void writeToSocket(String msg) {
        socketOutput.println(msg);
        socketOutput.flush();
    }

    public String readFromSocket() {
        try {
            return socketInput.readLine();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public void closeSocket() {
        try {
            socketInput.close();
            socketOutput.close();
            socket.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
