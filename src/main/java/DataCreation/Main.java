package DataCreation;

import GeneralCode.ReadFile;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to DataCreation! This program generates syntactically correct data that will be loaded to a key value database.");

        ParametersController parametersController = new ParametersController();
        parametersController.readParameters(args);

        Map<String, String> dataMap = ReadFile.getDataFromFile(parametersController.getKeyFilePath());
        parametersController.setMaxKeyNumOfValue(dataMap.size());

        CreateValues createValues = new CreateValues(dataMap,parametersController);
        long startTime = System.currentTimeMillis();
        createValues.createData();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60;
        long milliseconds = TimeUnit.MILLISECONDS.toMillis(elapsedTime) % 1000;
        System.out.println("Data created after: " + hours + " hours " + minutes + " minutes " + seconds + " seconds " + milliseconds + " milliseconds");
    }
}