package KVServer;

import TrieStructure.Trie;
import TrieStructure.TrieNode;

import java.util.Arrays;


public class RequestHandler {

    private final Trie trie = new Trie();
    private final RequestParser parser = new RequestParser();

    public String execute(String query) {

        String[] queryParts = parser.parseKeyValue(query);

        switch (queryParts[0]) {
            case "PING":
                return "OK";
            case "PUT":
                return executePutRequest(queryParts[1]);
            case "GET":
                return executeGetRequest(queryParts[1]);
            case "DELETE":
                return executeDeleteRequest(queryParts[1]);
            case "QUERY":
                return executeQueryRequest(queryParts[1]);
            default:
                System.out.println("Wrong type of query. Request must be: PUT, GET, DELETE, QUERY, COMPUTE.");
                return "WRONG QUERY TYPE";
        }
    }

    private String executePutRequest(String query) {
        String[] splittedData = parser.parseKeyValue(query);
        if (trie.search(splittedData[0]) == null) {
            try {
                trie.insert(splittedData[0], splittedData[1].split("-> \\[", 2)[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                trie.delete(splittedData[0]);
                System.err.println("Error while indexing data of \"" + query + "\". " +
                        "Data don't have the right format.");
                System.err.println("Server will not index \"" + splittedData[0] + "\".");
                return "ERROR";
            }
            return "OK";
        } else {
            return "Data \"" + query + "\" already exists.";
        }
    }

    private String executeGetRequest(String query) {
        TrieNode node = trie.search(query);
        if (node == null) {
            return "NOT FOUND";
        } else {
            String returnData = node.getLeafData();
            if (returnData.equals("")) {
                return query + " -> [ ]";
            } else {
                return query + " -> [ " + returnData + " ]";
            }
        }
    }

    private String executeDeleteRequest(String query) {
        if (trie.search(query) != null) {
            trie.delete(query);
            return "OK";
        } else {
            return "NOT FOUND";
        }
    }

    private String executeQueryRequest(String query) {
        String[] pathParts = query.split("\\.");
        if (pathParts.length == 1) {
            return executeGetRequest(pathParts[0]);
        } else {
            String[] newArray = Arrays.copyOfRange(pathParts,1,pathParts.length);
            TrieNode node = trie.search(pathParts[0]);
            if (node == null) {
                return "NOT FOUND";
            }
            return node.getLeafNode().search(newArray);
        }
    }
}
