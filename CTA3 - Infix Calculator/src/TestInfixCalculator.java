/**
 * A test class for the InfixCalculator, evaluating both hardcoded expressions and
 * expressions read from a text file using the calculator's file evaluation method.
 *
 */
public class TestInfixCalculator {
    /**
     * Main method to test the InfixCalculator with hardcoded and file-based expressions.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        InfixCalculator calculator = new InfixCalculator();

        // Hardcoded test expressions
        String[] expressions = {
            "(4+2)*3",
            "5+(3*7)",
            "10/(2+3)",
            "15%4",
            "4+2*3",
            "(4+2",
            "4++2",
            "12/0"
        };

        System.out.println("Testing hardcoded expressions:");
        for (int i = 0; i < expressions.length; i++) {
            System.out.println("Expression " + (i + 1) + ": " + expressions[i]);
            System.out.println("Result " + (i + 1) + ": " + calculator.evaluateInfix(expressions[i]));
            System.out.println();
        }

        // Test expressions from file
        System.out.println("Testing expressions from file:");
        calculator.evaluateFromFile("expressions.txt");
    }
}