package KVServer;

import java.util.ArrayList;

public class QueryParser {

    public static String[] parseQuery(String query) {
        return query.split(" ");
    }
}
