package TrieStructure;

import java.util.ArrayList;

public class LeafNode {

    private final String key;
    private final String value;
    private final ArrayList<LeafNode> children;

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
}
