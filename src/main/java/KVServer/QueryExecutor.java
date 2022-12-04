package KVServer;

public class QueryExecutor {

    public String execute(String query) {

        String[] queryParts = QueryParser.parseQuery(query);

        switch (queryParts[0]) {
            case "PUT":
                return executePutQuery(queryParts[1]);
            case "GET":
                return executeGetQuery(queryParts[1]);
            case "DELETE":
                return executeDeleteQuery(queryParts[1]);
            case "QUERY":
                return executeQueryQuery(queryParts[1]);
            case "COMPUTE":
                return executeComputeQuery(queryParts[1]);
            default:
                System.out.println("Wrong type of query. Query must be: PUT, GET, DELETE, QUERY, COMPUTE.");
                return "WRONG QUERY TYPE";
        }
    }

    private String executePutQuery(String query) {
        return null;
    }

    private String executeGetQuery(String query) {
        return null;
    }

    private String executeDeleteQuery(String query) {
        return null;
    }

    private String executeQueryQuery(String query) {
        return null;
    }

    private String executeComputeQuery(String query) {
        return null;
    }
}
