package KVClient;

import java.util.Stack;
import java.lang.Math;

public class MathExpressionEvaluator {

    public Double evaluate(String expression) {
        char[] tokens = expression.toCharArray();

        // Stack for numbers: 'values'
        Stack<Double> values = new Stack<>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {

            // Current token is a
            // whitespace, skip it
            if (tokens[i] == ' ')
                continue;

            // Current token is a number,
            // push it to stack for numbers
            if ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.') {
                StringBuilder tempNumber = new StringBuilder();

                // There may be more than one
                // digits in number
                while (i < tokens.length && ((tokens[i] >= '0' && tokens[i] <= '9') || tokens[i] == '.')) {
                    tempNumber.append(tokens[i++]);
                }

                values.push(Double.parseDouble(tempNumber.toString()));

                // right now the i points to
                // the character next to the digit,
                // since the for loop also increases
                // the i, we would skip one
                //  token position; we need to
                // decrease the value of i by 1 to
                // correct the offset.
                i--;
            }
            // Current token is an opening brace,
            // push it to 'ops'
            else if (tokens[i] == '(') {
                ops.push(tokens[i]);
            }
            // Closing brace encountered,
            // solve entire brace
            else if (tokens[i] == ')') {
                while (ops.peek() != '(') {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
                // CHECK IF NEXT OPS IS LOG/SIN/TAN/COS
                // AND APPLY VALUE THERE
                if (ops.peek() == 'l' || ops.peek() == 'c' || ops.peek() == 's' || ops.peek() == 't') {
                    values.push(calcFunction(ops.pop(), values.pop()));
                }
            }
            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
                // While top of 'ops' has same
                // or greater precedence to current
                // token, which is an operator.
                // Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }

                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
            else if (tokens[i] == 'l' || tokens[i] == 'c' || tokens[i] == 's' || tokens[i] == 't') {
                ops.push(tokens[i]);
                i += 2; // to skip the rest of the function name (log, cos, sin, tan)
            }
        }

        // Entire expression has been
        // parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        // Top of 'values' contains
        // result, return it
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

    // Returns true if 'op2' has higher
    // or same precedence as 'op1',
    // otherwise returns false.
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        return (op1 != '*' && op1 != '/') || (op2 != '+' && op2 != '-');
    }

    // A utility method to apply an
    // operator 'op' on operands 'a'
    // and 'b'. Return the result.
    private static Double applyOp(char op, Double b, Double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0.0;
    }
}
