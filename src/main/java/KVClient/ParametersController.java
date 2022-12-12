package KVClient;

public class ParametersController {

    private String serverFilePath;
    private String dataToIndexPath;
    private int replicationFactor;


    public void readParameters(String [] args){
        if (!hasCorrectNumOfParameters(args.length)) {
            throw new RuntimeException("Error at number of parameters. The correct number of arguments is 6. " +
                    "You gave: " + args.length);
        }
        assignParameters(args);
    }

    private boolean hasCorrectNumOfParameters(int argsLength) {
        return argsLength == 6;
    }

    private void assignParameters(String [] args) {
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-s":
                    serverFilePath = args[i + 1];
                    break;
                case "-i":
                    dataToIndexPath = args[i + 1];
                    break;
                case "-k":
                    try {
                        replicationFactor = Integer.parseInt(args[i+1]);
                    } catch (NumberFormatException nfe) {
                        throw new NumberFormatException("Couldn't convert Replication Factor \"" + args[i+1] + "\" to Integer.");
                    }
                    break;
                default:
                    throw new RuntimeException("Wrong parameter flag: " + args[i]);
            }
        }
    }

    public String getServerFilePath() {
        return serverFilePath;
    }

    public String getDataToIndexPath() {
        return dataToIndexPath;
    }

    public int getReplicationFactor() {
        return replicationFactor;
    }

    public void setReplicationFactor(int numOfServers) {
        if (numOfServers < replicationFactor) {
            System.out.println("Replication factor is bigger than the number of servers. " +
                    "Setting Replication factor to (" + numOfServers + ") number of servers.");
            replicationFactor = numOfServers;
        }
    }
}
