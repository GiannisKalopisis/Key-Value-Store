package DataCreation;

import GeneralCode.ReadFile;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to DataCreation! This program generates syntactically correct data that will be loaded to a key value database.");

        ParametersController parametersController = new ParametersController();
        parametersController.readParameters(args);

        Map<String, String> dataMap = ReadFile.getDataFromFile(parametersController.getKeyFilePath());
        parametersController.setMaxKeyNumOfValue(dataMap.size());

        CreateValues createValues = new CreateValues(dataMap,parametersController);
        createValues.createData();

    }
}