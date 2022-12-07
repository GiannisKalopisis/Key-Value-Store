package TrieStructure;

import java.util.ArrayList;
import java.util.HashMap;

public class LeafNode {

    private String key;
    private String value;
    private ArrayList<LeafNode> children;

    public LeafNode() {
        key = null;
        value = null;
        children = new ArrayList<>();
    }

    public LeafNode(String key, String value) {
        this.key = key;
        this.value = value;
        children = new ArrayList<>();
    }

    public LeafNode(String key) {
        this.value = null;
        this.key = key;
        children = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public ArrayList<LeafNode> getChildren() {
        return children;
    }

    public void addChildren(String key, String value) {
        LeafNode child = new LeafNode(key,value);
//        children.put(child);
    }

}
