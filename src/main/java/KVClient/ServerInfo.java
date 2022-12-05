package KVClient;

import java.util.ArrayList;

public class ServerInfo {

    private final String ipAddress;
    private final Integer port;

    public ServerInfo(String ipAddress, Integer port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Integer getPort() {
        return port;
    }
}
