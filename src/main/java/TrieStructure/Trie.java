package TrieStructure;

public class Trie {

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word, String data) throws ArrayIndexOutOfBoundsException{
        TrieNode current = root;
        for (char ch: word.toCharArray()) {
            if (!current.getChildren().containsKey(ch)) {
                current.getChildren().put(ch, new TrieNode());
            }
            current = current.getChildren().get(ch);
        }
        current.setLeaf(data);
        current.setEndOfWord(true);
    }

    public TrieNode search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current.isEndOfWord() ? current : null;
    }

    public void delete(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            if (!current.getChildren().containsKey(ch))
                return;
            current = current.getChildren().get(ch);
        }
        current.setEndOfWord(false);
    }
}
