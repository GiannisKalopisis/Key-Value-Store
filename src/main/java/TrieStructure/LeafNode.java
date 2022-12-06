package TrieStructure;

import java.util.HashMap;

public class LeafNode {

    private String key;
    private String value;
    private HashMap<String, LeafNode> children;

    public LeafNode() {
        key = null;
        value = null;
        children = new HashMap<>();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public HashMap<String, LeafNode> getChildren() {
        return children;
    }

//    public void addChildren(String key) {
//        children.put()
//    }

}
