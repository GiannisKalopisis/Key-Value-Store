package KVClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Compute {

    private final String request;
    private String mathExpression;
    private final ArrayList<String[]> queriesData;  // <varName, <query, value>>

    public Compute(String request) {
        this.request = request;
        this.queriesData = new ArrayList<>();
    }

    public void exportMathExpression() {
        String[] querySplittedAtWhere = request.split(" ",2)[1].split("WHERE", 2);
        this.mathExpression = querySplittedAtWhere[0].replaceAll(" ","");
    }

    public void exportQueries() {
        String[] querySplittedAtWhere = request.split(" ",2)[1].split("WHERE", 2);
        String queryParts = querySplittedAtWhere[1].trim();
        String[] queries = queryParts.split("AND");
        for (int i = 0; i < queries.length; i++) {
            queries[i] = queries[i].trim();
            storeQuery(queries[i]);
        }
    }

    private void storeQuery(String query) {
        String[] var_query = query.split(" = ");
        String[] temp = new String[3];
        temp[0] = var_query[0];
        temp[1] = var_query[1];
        temp[2] = null;
        this.queriesData.add(temp);
    }

    public String sendQueries(ArrayList<SocketStructure> sockets) {
        for (String[] query : queriesData) {
            String serverResponse;
            int counter = 0;
            for (SocketStructure socket : sockets) {
                socket.writeToSocket(query[1]);
                serverResponse = socket.readFromSocket();
                if (!serverResponse.equals("NOT FOUND")) {
                    System.out.println("response: " + serverResponse);
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

    public String getMathExpression() {
        return mathExpression;
    }

    public String replaceVarsWithVals() {
        String newMathExpression = mathExpression;
        for (String[] query : queriesData) {
            newMathExpression = newMathExpression.replaceAll(query[0], query[2]);
        }
        return newMathExpression;
    }

    public String[] getVariablesNames() {
        String[] vars = new String[queriesData.size()];
        for (int i = 0; i < queriesData.size(); i++) {
            vars[i] = queriesData.get(i)[0];
        }
        return vars;
    }

    public Map<String, Double> getVariablesValue() {
        Map<String, Double> values = new HashMap<>();
        for (int i = 0; i < queriesData.size(); i++) {
            values.put(queriesData.get(i)[0],stringToDouble(queriesData.get(i)[2]));
        }
        return values;
    }

    public String reconstructMathExpression() {
        StringBuilder newMathExpression = new StringBuilder();
        for (int i = 0; i < queriesData.size(); i++) {
            newMathExpression.append(queriesData.get(i)[0]).append("=").append(queriesData.get(i)[2]).append("; ");
        }
        newMathExpression.append(this.mathExpression).append(";");
        return newMathExpression.toString();
    }

    private double stringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.out.println("Couldn't convert " + value + " to double. Convert it to 0.0");
            return 0.00;
        }
    }
}