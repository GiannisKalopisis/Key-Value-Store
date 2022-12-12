package KVClient;

import java.util.Stack;
import java.lang.Math;

public class MathExpressionEvaluator {

    public Double evaluate(String expression) {
        char[] tokens = expression.toCharArray();

        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') {
                StringBuilder tempNumber = new StringBuilder();
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.')) {
                    tempNumber.append(tokens[i++]);
                }
                values.push(Double.parseDouble(tempNumber.toString()));
                i--;
            } else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            } else if (tokens[i] == ')') {
                // solve entire brace
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
                if (ops.peek() == 'l' || ops.peek() == 'c' || ops.peek() == 's' || ops.peek() == 't') {
                    values.push(calcFunction(ops.pop(), values.pop()));
                }
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                // While top of 'ops' has same
                // or greater precedence to current
                // token, which is an operator.
                // Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(tokens[i]);
            }
            else if (tokens[i] == 'l' || tokens[i] == 'c' || tokens[i] == 's' || tokens[i] == 't') {
                ops.push(tokens[i]);
                i += 2; // to skip the rest of the function name (log, cos, sin, tan)
            }
        }

        // apply remaining ops to remaining values
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private static Double calcFunction(char op, Double value) {
        switch (op) {
            case 'l':
                return Math.log10(value);
            case 'c':
                return Math.cos(Math.toRadians(value));
            case 's':
                return Math.sin(Math.toRadians(value));
            case 't':
                return Math.tan(Math.toRadians(value));
            default:
                return 0.0;
        }
    }

    // Returns true if 'op2' has higher or same precedence as 'op1'
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    private static Double applyOp(char op, Double b, Double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0.0) {
                    System.err.println("Cannot divide by zero. Convert it to one.");
                    b = 1.0;
                }
                return a / b;
        }
        return 0.0;
    }
}
