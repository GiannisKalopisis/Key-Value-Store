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

    public boolean initNewSocket(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to server with IP " + address + ", at port " + port + ".");
            socketOutput = new PrintWriter(socket.getOutputStream(), true);
            socketInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch(UnknownHostException unknownHostException) {
            System.err.println("UnknownHostException error while connecting to servers.");
            System.out.println("Not connected to server with IP " + address + ", at port " + port + ".");
            unknownHostException.printStackTrace();
            return false;
        } catch(IOException ioException) {
            System.out.println("An IOException occurred. Try to start servers first and then client.");
            System.out.println("Not connected to server with IP " + address + ", at port " + port + ".");
            return false;
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
