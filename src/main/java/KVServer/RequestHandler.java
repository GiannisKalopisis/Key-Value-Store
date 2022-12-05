package KVServer;

import TrieStructure.Trie;

import java.util.Arrays;

public class RequestHandler {

    private Trie trie = new Trie();
    private RequestParser parser = new RequestParser();

    public String execute(String query) {

        String[] queryParts = parser.parseKeyValue(query);

        switch (queryParts[0]) {
            case "PUT":
                return executePutRequest(queryParts[1]);
            case "GET":
                return executeGetRequest(queryParts[1]);
            case "DELETE":
                return executeDeleteRequest(queryParts[1]);
            case "QUERY":
                return executeQueryRequest(queryParts[1]);
            case "COMPUTE":
                return executeComputeRequest(queryParts[1]);
            default:
                System.out.println("Wrong type of query. Request must be: PUT, GET, DELETE, QUERY, COMPUTE.");
                return "WRONG QUERY TYPE";
        }
    }

    private String executePutRequest(String query) {
        String[] splittedData = parser.parseKeyValue(query);
        if (trie.search(splittedData[0]) == null) {
            System.out.println("key: \"" + splittedData[0] + "\"");
            System.out.println("value: \"" + splittedData[1] + "\"");
            trie.insert(splittedData[0], splittedData[1]);
            return "Added " + query;
        } else {
            return "Data \"" + query + "\" already exists.";
        }
    }

    private String executeGetRequest(String query) {
        return "null";
    }

    private String executeDeleteRequest(String query) {
        return null;
    }

    private String executeQueryRequest(String query) {
        return null;
    }

    private String executeComputeRequest(String query) {
        return null;
    }
}
