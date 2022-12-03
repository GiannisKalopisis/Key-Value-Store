package DataCreation;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ReadFile {

    public static Map<String, String> getDataFromFile(String filePath) {
        Map<String, String> dataMap = new HashMap<>();

        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] args = line.split(" ");
                dataMap.put(args[0], args[1]);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find file: " + filePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Couldn't read line of file: " + filePath);
            throw new RuntimeException(e);
        }

        return dataMap;
    }
}
