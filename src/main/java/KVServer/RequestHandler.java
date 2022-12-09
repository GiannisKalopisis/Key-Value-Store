package KVServer;

import TrieStructure.DataTree;
import TrieStructure.LeafNode;
import TrieStructure.Trie;
import TrieStructure.TrieNode;
import org.jetbrains.annotations.NotNull;

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
            System.out.println(splittedData[0] + " ### \"" + splittedData[1].split("-> \\[", 2)[1] + "\"");
            trie.insert(splittedData[0], splittedData[1].split("-> \\[", 2)[1]);
            return "OK";
        } else {
            return "Data \"" + query + "\" already exists.";
        }
    }

    private String executeGetRequest(String query) {
        System.out.println("--> \"" + query + "\"");
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
            String returnData = node.getLeafNode().search(newArray);
            return sendAppropriateMsgQR(query, returnData);
        }
    }

    private static String sendAppropriateMsgQR(String query, String returnData) {
        if (returnData.equals("NOT FOUND")) {
            return returnData;
        }
        if (returnData.contains("->")) {
            return query + " -> [ " + returnData + " ]";
        }
        return query + " -> " + returnData;
    }

    private String executeComputeRequest(String query) {
        return null;
    }

}
