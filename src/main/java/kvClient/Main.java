package kvClient;

import GeneralCode.ReadFile;

import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Client side of Key-Value Database!");

        ParametersController parametersController = new ParametersController();
        parametersController.readParameters(args);

        Map<String, String> servers = ReadFile.getDataFromFile(parametersController.getServerFilePath());
        ArrayList<String> dataToIndex = ReadFile.readDataToIndexFromFile(parametersController.getDataToIndexPath());




    }
}
