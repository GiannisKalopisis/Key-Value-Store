package KVClient;

import java.util.ArrayList;

public class Compute {

    private final String request;
    private String mathExpression;
    private final ArrayList<String[]> queriesData;  // [varName, query, value]

    public Compute(String request) {
        this.request = request;
        this.queriesData = new ArrayList<>();
    }

    public void exportMathExpression() {
        String[] querySplittedAtWhere = request.split(" ",2)[1].split("WHERE", 2);
        this.mathExpression = querySplittedAtWhere[0].replaceAll(" ","");
    }

    public boolean exportQueries() {
        String[] querySplittedAtWhere = request.split(" ",2)[1].split("WHERE", 2);
        String queryParts = querySplittedAtWhere[1].trim();
        String[] queries = queryParts.split("AND");
        for (int i = 0; i < queries.length; i++) {
            queries[i] = queries[i].trim();
            if (!storeQuery(queries[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean storeQuery(String query) {
        String[] var_query = query.split(" = ");
        String[] temp = new String[3];
        if (validVariableName(var_query[0])) {
            temp[0] = var_query[0];
        } else {
            return false;
        }
        temp[1] = var_query[1];
        temp[2] = null;
        this.queriesData.add(temp);
        return true;
    }

    private boolean validVariableName(String varName) {
        switch (varName) {
            case "l":
            case "c":
            case "s":
            case "t":
            case "a":
            case "n":
            case "i":
            case "o":
            case "g":
            case "lo":
            case "og":
            case "log":
            case "co":
            case "os":
            case "cos":
            case "si":
            case "in":
            case "sin":
            case "ta":
            case "an":
            case "tan":
                return false;
            default:
                return true;
        }
    }

    public String sendQueries(ArrayList<SocketStructure> sockets) {
        for (String[] query : queriesData) {
            String serverResponse;
            int counter = 0;
            for (SocketStructure socket : sockets) {
                socket.writeToSocket(query[1]);
                serverResponse = socket.readFromSocket();
                if (!serverResponse.equals("NOT FOUND")) {
                    if (isNumber(serverResponse)) {
                        query[2] = serverResponse;
                        break;
                    } else {
                        return query[1].split(" ")[1] + " is NaN";
                    }
                }
                counter++;
            }
            if (counter == sockets.size()) {
                return query[1].split(" ")[1] + " NOT FOUND";
            }
        }
        return "OK";
    }

    private boolean isNumber(String response) {
        try {
            Integer.parseInt(response);
            return true;
        } catch (NumberFormatException nfe_Integer) {
            try {
                Float.parseFloat(response);
                return true;
            } catch (NumberFormatException nfe_float) {
                return false;
            }
        }
    }

    public void printQueries() {
        for (String[] temp : queriesData) {
            System.out.println("varName: '" + temp[0] + "', query: '" + temp[1] + "', queryVal: '" + temp[2] + "'");
        }
    }

    public String replaceVarsWithVals() {
        String newMathExpression = mathExpression;
        for (String[] query : queriesData) {
            newMathExpression = newMathExpression.replaceAll(query[0], query[2]);
        }
        return newMathExpression;
    }
}
