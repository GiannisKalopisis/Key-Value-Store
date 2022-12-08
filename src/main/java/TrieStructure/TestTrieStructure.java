package TrieStructure;

public class TestTrieStructure {

    public static void main(String[] args) {
        System.out.println("Test trie insertion:");
//        Trie trie = new Trie();
//
//        TrieNode testNode = trie.search("key3");
//        if (testNode != null) {
//            System.out.println(testNode.getLeaf());
//        }
//        else {
//            System.out.println("Word key3 doesn't exists in trie");
//        }
//
//        trie.insert("key1", "blabla");
//        trie.insert("key2", "computer -> \"QiYa\" | small -> [ hospital -> \"CxLC\" | stage -> \"ehtB\" | color -> \"kYeV\" | machine -> [ day -> \"yLZM\" | office -> 81 ] ] | college -> \"dkKe\" | language -> \"mqTq\" | factor -> 16.31885");
//        trie.insert("key3", "series -> 52.7502 | center -> 13.168743 | pull -> 76.8985");
//        trie.insert("key4", "statement -> \"rKcI\" | customer -> 69");

        DataTree dataTree = new DataTree();
        String data  = "computer -> [ lang -> [ chapter -> [ damage -> [ ] ] | gun -> [ bullet -> [ ] ] ] ] ]";
        dataTree.insert(data, dataTree.getRoot());
        String traverseString = dataTree.traverse(dataTree.getRoot(), "") + " ]";
        traverseString = traverseString.replaceAll(" +", " ");
        System.out.println(traverseString);
        if (data.equals(traverseString)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
            System.out.println(data);
            System.out.println(traverseString);
        }
        System.out.println("=========================================");

        DataTree dataTree1 = new DataTree();
        String data1  = "series -> 52.7502 | center -> 13.168743 | pull -> 76.8985 ]";
        dataTree1.insert(data1, dataTree1.getRoot());
        String traverseString1 = dataTree1.traverse(dataTree1.getRoot(), "") + " ]";
        traverseString1 = traverseString1.replaceAll(" +", " ");
        System.out.println(traverseString1);
        if (data1.equals(traverseString1)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
            System.out.println(data1);
            System.out.println(traverseString1);
        }
        System.out.println("=========================================");
//        dataTree1.traverse(dataTree1.getRoot());


        DataTree dataTree2 = new DataTree();
        String data2  = "computer -> \"QiYa\" | small -> [ hospital -> \"CxLC\" | stage -> \"ehtB\" | color -> \"kYeV\" | machine -> [ day -> \"yLZM\" | office -> 81 ] ] | college -> \"dkKe\" | language -> \"mqTq\" | factor -> 16.31885 ]";
        dataTree2.insert(data2, dataTree2.getRoot());
        String traverseString2 = dataTree2.traverse(dataTree2.getRoot(), "") + " ]";
        traverseString2 = traverseString2.replaceAll(" +", " ");
        System.out.println(traverseString2);
        if (data2.equals(traverseString2)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
            System.out.println(data2);
            System.out.println(traverseString2);
        }
        System.out.println("=========================================");

        DataTree dataTree3 = new DataTree();
        String data3 = " ]";
        dataTree3.insert(data3, dataTree3.getRoot());
        String traverseString3 = dataTree3.traverse(dataTree3.getRoot(), "") + " ]";
        traverseString3 = traverseString3.replaceAll(" +", " ");
        System.out.println(traverseString3);
        if (data3.equals(traverseString3)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
            System.out.println(data3);
            System.out.println(traverseString3);
        }
        System.out.println("=========================================");


        DataTree dataTree4 = new DataTree();
        String data4  = "computer -> [ lang -> [ ] ] ]";
        dataTree4.insert(data4, dataTree4.getRoot());
        String traverseString4 = dataTree4.traverse(dataTree4.getRoot(), "") + " ]";
        traverseString4 = traverseString4.replaceAll(" +", " ");
        System.out.println(traverseString4);
        if (data4.equals(traverseString4)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
            System.out.println(data4);
            System.out.println(traverseString4);
        }
        System.out.println("=========================================");


        DataTree dataTree5 = new DataTree();
        String data5  = "computer -> \"QiYa\" | small -> [ hospital -> \"CxLC\" | stage -> \"ehtB\" | color -> \"kYeV\" | machine -> [ day -> [ ] ] ] | college -> \"dkKe\" | language -> \"mqTq\" | factor -> 16.31885 ]";
        dataTree5.insert(data5, dataTree5.getRoot());
        String traverseString5 = dataTree5.traverse(dataTree5.getRoot(), "") + " ]";
        traverseString5 = traverseString5.replaceAll(" +", " ");
        System.out.println(traverseString5);
        if (data5.equals(traverseString5)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
            System.out.println(data5);
            System.out.println(traverseString5);
        }
        System.out.println("=========================================");

        DataTree dataTree6 = new DataTree();
        String data6  = "computer -> \"QiYa\" | small -> [ hospital -> \"CxLC\" | stage -> \"ehtB\" | color -> \"kYeV\" | machine -> [ ] ] | college -> \"dkKe\" | language -> \"mqTq\" | factor -> 16.31885 ]";
        dataTree6.insert(data6, dataTree6.getRoot());
        String traverseString6 = dataTree6.traverse(dataTree6.getRoot(), "") + " ]";
        traverseString6 = traverseString6.replaceAll(" +", " ");
        System.out.println(traverseString6);
        if (data6.equals(traverseString6)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
            System.out.println("'" + data6 + "'");
            System.out.println("'" + traverseString6 + "'");
        }
        System.out.println("=========================================");


        DataTree dataTree7 = new DataTree();
        String data7  = "course -> [ material -> \"XXmQ\" ] ]";
        dataTree7.insert(data7, dataTree7.getRoot());
        String traverseString7 = dataTree7.traverse(dataTree7.getRoot(), "") + " ]";
        traverseString7 = traverseString7.replaceAll(" +", " ");
        System.out.println(traverseString7);
        if (data7.equals(traverseString7)) {
            System.out.println("Success");
        } else {
            System.out.println("Failure");
            System.out.println("'" + data7 + "'");
            System.out.println("'" + traverseString7 + "'");
        }
        System.out.println("=========================================");

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
