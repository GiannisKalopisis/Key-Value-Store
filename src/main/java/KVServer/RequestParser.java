package KVServer;

public class RequestParser {

    public String[] parseKeyValue(String query) {
        return query.split(" ",2);
    }
}
