package TrieStructure;

import java.util.HashMap;

public class TrieNode {

    private boolean isEndOfWord;
    private HashMap<Character, TrieNode> children;
    private String leaf;

    public TrieNode(){
        children = new HashMap<>();
        isEndOfWord = false;
        leaf = null;
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }

    public String getLeaf() {
        return leaf;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

}
