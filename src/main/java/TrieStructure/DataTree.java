package TrieStructure;

public class DataTree {

    private LeafNode root;

    public DataTree() {
        root = new LeafNode();
    }

    public LeafNode getRoot() {
        return root;
    }

    public String insert(String data, LeafNode parentNode) {
//        LeafNode current = parentNode;
//        String data = data.split("-> \\[",2)[1];
//        System.out.println("Starting data: \"" + data + "\"");

        while (data!= null) {
            String[] temp = data.split(" \\| | \\[ | ]", 2);
            if (temp.length == 1) return data;
            data = temp[1];

            // go at PREVIOUS LEVEL
            if (temp[0].equals("")) {
                System.out.println(" ---> previous level");
                System.out.println(data);
                System.out.println("---------------------------------");
                return data;
                // go at NEW LEVEL
            } else if (temp[0].matches("([a-z]|[A-Z])\\w+ ->")) {
                String[] key_value = temp[0].split(" ->",2);
                String key = key_value[0];
                System.out.print("key: \"" + key + "\"");
                System.out.println(" ---> new level");
                System.out.println(data);
                LeafNode newNode = new LeafNode(key);
                parentNode.getChildren().add(newNode);
                data = insert(data, newNode);
                System.out.println("---------------------------------");
            // put HERE
            } else {
                String[] key_value = temp[0].split("->");
                if (temp[0].equals("]")) {
//                    String[] temp1 = data.split(" \\| | \\[ | ]", 2);
                    return data;
                }
                String key = key_value[0];
                String value = key_value[1];
                LeafNode newNode = new LeafNode(key, value);
                parentNode.getChildren().add(newNode);
                System.out.print("key: \"" + key + "\", value: \"" + value + "\"");
                System.out.println(" ---> insertion at this level");
                System.out.println(data);
                System.out.println("---------------------------------");
            }
        }
        return "";
    }

}
