package KVClient;

import java.util.ArrayList;
import java.util.Arrays;

import static GeneralCode.RandomGenerators.getRandomInt;

public class RequestHelper {

    public void replicateDataToServers(ArrayList<SocketStructure> sockets, ArrayList<String> dataToIndex, int replicationFactor) {

        int[] serverRepArray = new int[replicationFactor];

        Arrays.fill(serverRepArray, -1);

        for (int i = 0; i < dataToIndex.size(); i++) {
            int j = 0;
            while (j < replicationFactor){
                int randomServer = getRandomInt(sockets.size() - 1);
                if (containedIntoServerArray(randomServer, serverRepArray)) {
                    continue;
                }
                serverRepArray[j] = randomServer;
                String put = "PUT " + dataToIndex.get(i);
                System.out.println("PUT string: \"" + put + "\"");
                sockets.get(randomServer).writeToSocket(put);
                String response = sockets.get(randomServer).readFromSocket();
                System.out.println("Response from server: \"" + response + "\"");
                j++;
            }
            Arrays.fill(serverRepArray,-1);
        }
    }

    private boolean containedIntoServerArray(int randomServer, int[] serverRepArray){
        for (int j : serverRepArray) {
            if (randomServer == j) return true;
        }
        return false;
    }
}
