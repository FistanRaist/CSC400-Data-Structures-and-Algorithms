import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

/**
 * A class that evaluates arithmetic infix expressions with support for addition, subtraction,
 * multiplication, division, and modulo operations. Expressions must use parentheses to group
 * operations, and invalid expressions trigger appropriate error messages. Supports evaluation
 * of expressions from a text file.
 *
 */
public class InfixCalculator {
    /**
     * Represents an operand with its value and metadata about its origin.
     */
    private static class Operand {
        int value;
        boolean isSimpleNumber;
        boolean isFromParenthesized;

        Operand(int value, boolean isSimpleNumber, boolean isFromParenthesized) {
            this.value = value;
            this.isSimpleNumber = isSimpleNumber;
            this.isFromParenthesized = isFromParenthesized;
        }
    }

    /**
     * Evaluates an infix arithmetic expression and returns the result.
     * Supports +, -, *, /, % operators and requires parentheses for operation grouping.
     *
     * @param infixExpression the infix expression to evaluate
     * @return the result of the expression, or 0 if an error occurs
     * @throws IllegalArgumentException if the expression is invalid
     */
    public int evaluateInfix(String infixExpression) {
        try {
            if (infixExpression == null) {
                throw new IllegalArgumentException("Expression cannot be null");
            }
            infixExpression = infixExpression.replaceAll("\\s+", "");
            Stack<Operand> operands = new Stack<>();
            Stack<Character> operators = new Stack<>();
            int parenDepth = 0;

            for (int i = 0; i < infixExpression.length(); ) {
                char c = infixExpression.charAt(i);

                if (Character.isDigit(c)) {
                    ParseResult result = parseNumber(infixExpression, i);
                    operands.push(new Operand(result.value, true, false));
                    i = result.nextIndex;
                } else if (c == '(') {
                    operators.push(c);
                    parenDepth++;
                    i++;
                } else if (c == ')') {
                    if (parenDepth == 0) {
                        throw new IllegalArgumentException("Mismatched parentheses");
                    }
                    while (!operators.isEmpty() && operators.peek() != '(') {
                        evaluateOperation(operands, operators, true);
                    }
                    if (!operators.isEmpty()) {
                        operators.pop();
                        parenDepth--;
                    } else {
                        throw new IllegalArgumentException("Mismatched parentheses");
                    }
                    i++;
                } else if (isOperator(c)) {
                    while (!operators.isEmpty() && operators.peek() != '(' &&
                           hasHigherPrecedence(operators.peek(), c)) {
                        evaluateOperation(operands, operators, parenDepth > 0);
                    }
                    operators.push(c);
                    i++;
                } else {
                    throw new IllegalArgumentException("Invalid character: " + c);
                }
            }

            while (!operators.isEmpty()) {
                if (operators.peek() == '(') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                evaluateOperation(operands, operators, false);
            }

            if (operands.size() != 1) {
                throw new IllegalArgumentException("Invalid expression");
            }

            return operands.pop().value;
        } catch (Exception e) {
            System.out.println("Error: Invalid infix expression - " + e.getMessage());
            return 0;
        }
    }

    /**
     * Reads and evaluates infix expressions from a specified text file.
     * Each line in the file is treated as a separate expression.
     *
     * @param filename the name of the text file containing expressions
     */
    public void evaluateFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int index = 1;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    System.out.println("File Expression " + index + ": " + line);
                    System.out.println("File Result " + index + ": " + evaluateInfix(line));
                    System.out.println();
                    index++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to read file '" + filename + "' - " + e.getMessage());
        }
    }

    /**
     * Parses a multi-digit number from the expression starting at the given index.
     *
     * @param expression the input expression
     * @param startIndex the starting index for parsing
     * @return a ParseResult containing the parsed number and the next index
     * @throws IllegalArgumentException if the number is invalid
     */
    private ParseResult parseNumber(String expression, int startIndex) {
        StringBuilder num = new StringBuilder();
        int i = startIndex;
        while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
            num.append(expression.charAt(i));
            i++;
        }
        if (num.length() == 0) {
            throw new IllegalArgumentException("Invalid number at position " + startIndex);
        }
        return new ParseResult(Integer.parseInt(num.toString()), i);
    }

    /**
     * Checks if a character is a valid arithmetic operator.
     *
     * @param c the character to check
     * @return true if the character is +, -, *, /, or %, false otherwise
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    /**
     * Determines if the first operator has higher precedence than the second.
     *
     * @param op1 the first operator
     * @param op2 the second operator
     * @return true if op1 has higher precedence than op2
     */
    private boolean hasHigherPrecedence(char op1, char op2) {
        return (op1 == '*' || op1 == '/' || op1 == '%') && (op2 == '+' || op2 == '-');
    }

    /**
     * Evaluates a single operation using the top two operands and the top operator.
     *
     * @param operands the stack of operands
     * @param operators the stack of operators
     * @param withinParentheses true if the operation is within parentheses
     * @throws IllegalArgumentException if the operation is invalid
     */
    private void evaluateOperation(Stack<Operand> operands, Stack<Character> operators, boolean withinParentheses) {
        if (operands.size() < 2 || operators.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression");
        }

        Operand operand2 = operands.pop();
        Operand operand1 = operands.pop();
        char op = operators.pop();

        validateOperands(operand1, operand2);

        int result = performOperation(operand1.value, operand2.value, op);
        operands.push(new Operand(result, false, withinParentheses));
    }

    /**
     * Validates that both operands are either simple numbers or from parenthesized expressions.
     *
     * @param operand1 the first operand
     * @param operand2 the second operand
     * @throws IllegalArgumentException if either operand is invalid
     */
    private void validateOperands(Operand operand1, Operand operand2) {
        if (!operand1.isSimpleNumber && !operand1.isFromParenthesized) {
            throw new IllegalArgumentException("Invalid left operand: not a simple number or from parenthesized expression");
        }
        if (!operand2.isSimpleNumber && !operand2.isFromParenthesized) {
            throw new IllegalArgumentException("Invalid right operand: not a simple number or from parenthesized expression");
        }
    }

    /**
     * Performs the arithmetic operation on two values.
     *
     * @param num1 the first operand
     * @param num2 the second operand
     * @param op the operator
     * @return the result of the operation
     * @throws IllegalArgumentException if the operation is invalid
     */
    private int performOperation(int num1, int num2, char op) {
        switch (op) {
            case '+': return num1 + num2;
            case '-': return num1 - num2;
            case '*': return num1 * num2;
            case '/':
                if (num2 == 0) throw new IllegalArgumentException("Division by zero");
                return num1 / num2;
            case '%':
                if (num2 == 0) throw new IllegalArgumentException("Modulo by zero");
                return num1 % num2;
            default: throw new IllegalArgumentException("Invalid operator");
        }
    }

    /**
     * Helper class to store parsing results.
     */
    private static class ParseResult {
        int value;
        int nextIndex;

        ParseResult(int value, int nextIndex) {
            this.value = value;
            this.nextIndex = nextIndex;
        }
    }
}