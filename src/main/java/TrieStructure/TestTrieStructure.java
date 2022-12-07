package TrieStructure;

public class TestTrieStructure {

    public static void main(String[] args) {
        System.out.println("Test trie insertion:");
        Trie trie = new Trie();

        TrieNode testNode = trie.search("key3");
        if (testNode != null) {
            System.out.println(testNode.getLeaf());
        }
        else {
            System.out.println("Word key3 doesn't exists in trie");
        }

        trie.insert("key1", "blabla");
        trie.insert("key2", "computer -> \"QiYa\" | small -> [ hospital -> \"CxLC\" | stage -> \"ehtB\" | color -> \"kYeV\" | machine -> [ day -> \"yLZM\" | office -> 81 ] ] | college -> \"dkKe\" | language -> \"mqTq\" | factor -> 16.31885");
        trie.insert("key3", "series -> 52.7502 | center -> 13.168743 | pull -> 76.8985");
        trie.insert("key4", "statement -> \"rKcI\" | customer -> 69");

        DataTree root = new DataTree();
        root.insert("-> [ computer -> \"QiYa\" | small -> [ hospital -> \"CxLC\" | stage -> \"ehtB\" | color -> \"kYeV\" | machine -> [ day -> \"yLZM\" | office -> 81 ] ] | college -> \"dkKe\" | language -> \"mqTq\" | factor -> 16.31885 ]");
        System.out.println("=========================================");
        root.insert("-> [ ]");

//        testNode = trie.search("key3");
//        if (testNode != null) {
//            System.out.println(testNode.getLeaf());
//        }
//        else {
//            System.out.println("Word key3 doesn't exists in trie");
//        }
//
//        testNode = trie.search("key");
//        if (testNode != null) {
//            System.out.println(testNode.getLeaf());
//        }
//        else {
//            System.out.println("Word key doesn't exists in trie");
//        }
//
//        trie.delete("key3");
//        testNode = trie.search("key3");
//        if (testNode != null) {
//            System.out.println(testNode.getLeaf());
//        }
//        else {
//            System.out.println("Word key doesn't exists in trie");
//        }
//
//        testNode = trie.search("key4");
//        if (testNode != null) {
//            System.out.println(testNode.getLeaf());
//        }
//        else {
//            System.out.println("Word key4 doesn't exists in trie");
//        }
//
//        trie.delete("key5");
//
//        trie.delete("key2");
//        testNode = trie.search("key2");
//        if (testNode != null) {
//            System.out.println(testNode.getLeaf());
//        }
//        else {
//            System.out.println("Word key2 doesn't exists in trie");
//        }
//
//        testNode = trie.search("key1");
//        if (testNode != null) {
//            System.out.println(testNode.getLeaf());
//        }
//        else {
//            System.out.println("Word key1 doesn't exists in trie");
//        }
    }
}
