package TrieStructure;

public class DataTree {

    private final LeafNode root;

    public DataTree() {
        root = new LeafNode();
    }

    public void insert(String data) {
//        LeafNode current = root;
        String startData = data.split("-> \\[",2)[1];
        System.out.println("Starting data: \"" + startData + "\"");
        for (String datum : startData.split(" \\|")) {
            System.out.print(datum + " ");
            if (datum.contains("[")) {
                System.out.println("new level");
            } else if (datum.contains("]")) {
                System.out.println("previous level");
            } else {
                System.out.println();
            }
        }
    }

}
