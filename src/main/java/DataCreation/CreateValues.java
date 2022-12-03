package DataCreation;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class CreateValues {

    private final String topLvlKeyPrefix = "key";   // the value of top-level key (i.e. key1, key2, ..., keyN)
    private final String createdFilePath = "src/main/resources/dataToIndex.txt";
    Map<String, String> dataMap;
    ParametersController parametersController;
    private float nestedValuePossibility = 0.25F;

    public CreateValues(Map<String, String> dataMap, ParametersController parametersController) {
        this.dataMap = dataMap;
        this.parametersController = parametersController;
    }

    public void createData() {
        StringBuilder tempString = new StringBuilder();
        for (int i = 0; i < this.parametersController.getNumOfLines(); i++) {
            tempString.append(topLvlKeyPrefix).append(i).append(" ->");
            tempString.append(createValue(parametersController.getMaxNestingLevel()));
            tempString.append("\n");
        }
        writeToFile(tempString.toString());
    }

    private String createValue(int nestingLvl) {
        StringBuilder valueString = new StringBuilder();
        valueString.append(" [ ");

        ArrayList<String> usedKeys = new ArrayList<>();
        int numKeysOfCurrentLvl = getRandomInt(parametersController.getMaxKeyNumOfValue());
        for (int i = 0; i < numKeysOfCurrentLvl; i++) {
            // get a key that is  not written in current level
            String newKey = getAcceptedKey(new ArrayList<>(dataMap.keySet()), usedKeys);
            // write key
            valueString.append(newKey).append(" ->");

            // check if put nesting
            if (nestingLvl > 0 && addNestedValue()) {
//                System.out.println("GO FOR NESTING");
                valueString.append(createValue(nestingLvl - 1));
            } else {    // else put value of key type
                switch (dataMap.get(newKey)) {
                    case "int":
                        valueString.append(" ").append(getRandomInt(100));
                        break;
                    case "float":
                        valueString.append(" ").append(getRandomFloat(0, 100));
                        break;
                    case "string":
                        valueString.append(" ").append("\"")
                                .append(getRandomSting(parametersController.getMaxStringLength()))
                                .append("\"");
                        break;
                    default:
                        System.err.println("Type \"" + dataMap.get(newKey) + "\" of key \"" + newKey + "\" is invalid." +
                                "It should be int, float or string. Instead, I will put int.");
                        valueString.append(" ").append(getRandomInt(100));
                }
            }


            if (i < (numKeysOfCurrentLvl - 1)) valueString.append(" | ");    // not the last, so add value delimiter
        }
        valueString.append(" ]");
        return valueString.toString();
    }

    private boolean addNestedValue() {
        return !(getRandomFloat(0, 1) > nestedValuePossibility);
    }

    private String getAcceptedKey(ArrayList<String> keySet, ArrayList<String> usedKeys) {
        int randomPos = getRandomInt(keySet.size() - 1);
        while (true) {
            if (usedKeys.contains(keySet.get(randomPos))) {
                randomPos = getRandomInt(keySet.size());
                continue;
            }
            break;
        }
        return keySet.get(randomPos);
    }

    private int getRandomInt(int max) {
        return (int)(Math.random() * (max + 1));
    }

    private float getRandomFloat(int min, int max) {
        return (float)(Math.random() * (max - min + 1) + min);
    }

    private String getRandomSting(int maxLength) {
        return RandomStringUtils.randomAlphabetic(maxLength);
    }

    private void writeToFile(String data) {
        File file = createFile();
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while trying to write to file \"" + createdFilePath + "\".");
            e.printStackTrace();
        }
    }

    private File createFile() {
        try {
            File file = new File(createdFilePath);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {    // file already exists
                if (file.delete()) {       // delete and re-create
                    System.out.println("File " + file.getName() + " deleted.");
                    if (file.createNewFile()) {     // re-create file
                        System.out.println("File " + file.getName() + " created.");
                    }
                    else {      // error at re-creation
                        System.err.println("Couldn't create file " + file.getName() + ". Terminate and try again");
                        System.exit(-1);
                    }
                } else {    // error at deletion
                    System.err.println("Couldn't delete file " + file.getName() + ". Terminate and try again");
                    System.exit(-2);
                }
            }
            return file;
        } catch (IOException e) {
            System.err.println("An error occurred while writing to file dataToIndex.txt.");
            throw new RuntimeException(e);
        }
    }
}
