package TrieStructure;

public class Trie {

    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word, String data) {
        TrieNode current = root;
        for (char l: word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(l, c -> new TrieNode());
        }
        current.setLeaf(data);
        current.setEndOfWord(true);
    }

    public TrieNode search(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChildren().get(ch);
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current;
    }

    public void delete(String word) {
        delete(root, word, 0);
    }

    private boolean delete(TrieNode currentNode, String word, int index) {
        if (index == word.length()) {
            if (!currentNode.isEndOfWord()) {
                return false;
            }
            currentNode.setEndOfWord(false);
            return currentNode.getChildren().isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode node = currentNode.getChildren().get(ch);

        // character doesn't exist -> so word too
        if (node == null) {
            return false;
        }

        boolean shouldDeleteCurrentNode = delete(node, word, index + 1) && !node.isEndOfWord();

        if (shouldDeleteCurrentNode) {
            currentNode.getChildren().remove(ch);
            return currentNode.getChildren().isEmpty();
        }
        return false;
    }
}
