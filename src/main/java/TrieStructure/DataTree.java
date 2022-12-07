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

        while (startData!= null) {
            String[] temp = startData.split(" \\| | \\[ | ]", 2);
            if (temp.length == 1) return;
            startData = temp[1];
            System.out.print("\"" + temp[0] + "\"");
            if (temp[0].equals("")) {
                System.out.println(" ---> previous level");
            } else if (temp[0].matches("([a-z]|[A-Z])\\w+ ->")) {
                System.out.println(" ---> new level");
            } else {
                System.out.println(" ---> insertion at this level");
            }
        }
    }

}
