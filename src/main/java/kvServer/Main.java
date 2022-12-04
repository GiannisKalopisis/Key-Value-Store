package kvServer;


public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Client side of Key-Value Database!");

        ParametersController parametersController = new ParametersController();
        parametersController.readParameters(args);




    }
}
