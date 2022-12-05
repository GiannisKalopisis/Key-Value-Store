package GeneralCode;

import KVClient.ServerInfo;

import java.io.*;
import java.util.ArrayList;
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

    public static ArrayList<ServerInfo> getServersFromFile(String filePath) {
        ArrayList<ServerInfo> serversList = new ArrayList<>();

        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] args = line.split(" ");
                try {
                    Integer port = Integer.parseInt(args[1]);
                    serversList.add(new ServerInfo(args[0], port));
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Couldn't convert port \"" + args[1] + "\" to Integer.");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find file: " + filePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Couldn't read line of file: " + filePath);
            throw new RuntimeException(e);
        }

        return serversList;
    }

    public static ArrayList<String> readDataToIndexFromFile(String filePath) {
        ArrayList<String> data = new ArrayList<>();

        File file = new File(filePath);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find file: " + filePath);
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Couldn't read line of file: " + filePath);
            throw new RuntimeException(e);
        }

        return data;
    }
}
