package KVServer;

import TrieStructure.DataTree;
import TrieStructure.LeafNode;
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
            return query + " -> [ " + node.getLeafData() + " ]";
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
        for (int i = 0; i < pathParts.length; i++) {
            System.out.println(pathParts[i]);
        }
        if (pathParts.length == 1) {
            return executeGetRequest(pathParts[0]);
        } else {
            String[] newArray = Arrays.copyOfRange(pathParts,1,pathParts.length);
            for (int i = 0; i < newArray.length; i++) {
                System.out.println("    " + newArray[i]);
            }
            TrieNode node = trie.search(pathParts[0]);
            if (node == null) {
                return "NOT FOUND";
            }
            System.out.println("found node");
            return node.getLeafNode().search(newArray);
        }
    }

    private String executeComputeRequest(String query) {
        return null;
    }
}
