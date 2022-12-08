package TrieStructure;

import java.util.HashMap;

public class TrieNode {

    private boolean isEndOfWord;
    private final HashMap<Character, TrieNode> children;
    private DataTree leaf;

    public TrieNode(){
        children = new HashMap<>();
        isEndOfWord = false;
        leaf = new DataTree();
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public void setLeaf(String data) {
        leaf.insert(data.trim(),leaf.getRoot());
    }

    public String getLeafData() {
        return leaf.traverse(leaf.getRoot(), "").replaceAll(" +", " ");
    }

    public DataTree getLeafNode() {
        return leaf;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

}
