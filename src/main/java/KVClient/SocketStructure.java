package KVClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketStructure {

    private int socketId;
    private Socket socket = null;
    private DataInputStream socketInput = null;
    private DataOutputStream socketOutput = null;

    public SocketStructure(int socketId) {
        this.socketId = socketId;
    }

    public void initNewSocket(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to server with IP " + address + ", at port " + port + ".");

            socketInput = new DataInputStream(socket.getInputStream());
            socketOutput = new DataOutputStream(socket.getOutputStream());
        } catch(UnknownHostException unknownHostException) {
            System.err.println("UnknownHostException error while connecting to servers.");
            unknownHostException.printStackTrace();
            System.exit(-1);
        } catch(IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }


    public void writeToSocket(String msg) {
        try {
            socketOutput.writeUTF(msg);
            socketOutput.flush();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public String readFromSocket() {
        try {
            return socketInput.readUTF();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public void closeSocket() {
        try {
            socketOutput.close();
            socket.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}
