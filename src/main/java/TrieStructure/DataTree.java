package TrieStructure;

import java.util.ArrayList;

public class DataTree {

    private final LeafNode root;

    public DataTree() {
        root = new LeafNode();
    }

    public LeafNode getRoot() {
        return root;
    }

    public String insert(String data, LeafNode parentNode) throws ArrayIndexOutOfBoundsException{

        while (data!= null) {
            String[] temp = data.split(" \\| | \\[ | ]", 2);
            if (temp.length == 1) return data;
            data = temp[1];

            // go at PREVIOUS LEVEL
            if (temp[0].equals("")) {
                return data;
            // go at NEW LEVEL
            } else if (temp[0].matches("([a-z]|[A-Z])\\w+ ->")) {
                String[] key_value = temp[0].split(" ->",2);
                String key = key_value[0].trim();
                LeafNode newNode = new LeafNode(key);
                parentNode.getChildren().add(newNode);
                data = insert(data, newNode);
            // put HERE
            } else {
                String[] key_value = temp[0].split("->");
                if (temp[0].equals("]")) {
                    return data;
                }
                String key = key_value[0].trim();
                String value = key_value[1].trim();
                LeafNode newNode = new LeafNode(key, value);
                parentNode.getChildren().add(newNode);
            }
        }
        return "";
    }

    public String traverse(LeafNode startNode, String startString) {
        String currentString = startString;

        for (int i = 0; i < startNode.getChildren().size(); i++) {
            LeafNode child = startNode.getChildren().get(i);
            String key = child.getKey();
            String value = child.getValue();
            currentString += key + " ->";
            if (hasChildren(child)) {
                currentString += " [ ";
                currentString = traverse(child, currentString) + " ]";
            } else {
                currentString += " " + value;
            }
            if (i < (startNode.getChildren().size() - 1)) {
                currentString += " | ";
            }
        }
        return currentString;
    }

    public String search(String[] paths) {
        LeafNode current = root;
        for (String path : paths) {
            current = searchChildren(path, current.getChildren());
            if (current == null) {
                return "NOT FOUND";
            }
        }

        String traversedString = traverse(current,"");
        if (traversedString.equals("")) {   // no children, just value
            traversedString += current.getValue();
        }

        return traversedString;
    }

    private LeafNode searchChildren(String keyPath, ArrayList<LeafNode> children) {
        for (LeafNode child : children) {
            if (keyPath.equals(child.getKey())) {
                return child;
            }
        }
        return null;
    }

    private boolean hasChildren(LeafNode node) {
        return node.getValue() == null;
    }
}
