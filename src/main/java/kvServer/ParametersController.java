package kvServer;

public class ParametersController {

    private String ipAddress;
    private String port;


    public void readParameters(String [] args){
        if (!hasCorrectNumOfParameters(args.length)) {
            throw new RuntimeException("Error at number of parameters. The correct number of arguments is 4. " +
                    "You gave: " + args.length);
        }
        assignParameters(args);
    }

    private boolean hasCorrectNumOfParameters(int argsLength) {
        return argsLength == 4;
    }

    private void assignParameters(String [] args) {
        for (int i = 0; i < args.length; i += 2) {
            switch (args[i]) {
                case "-a":
                    ipAddress = args[i + 1];
                    break;
                case "-p":
                    port = args[i + 1];
                    break;
                default:
                    throw new RuntimeException("Wrong parameter flag: " + args[i]);
            }
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getPort() {
        return port;
    }
}
