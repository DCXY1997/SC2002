package src.Helper;

/**
 * The {@code OutOfRange} exception is thrown to indicate that a token retrieved
 * by a {@code Scanner} is not within the specified range.
 *
 * <p>
 * This exception can be used in scenarios where user input validation is
 * necessary, and the input falls outside the allowed range.</p>
 *
 * <p>
 * <b>Usage:</b></p>
 * <pre>
 *     if (input < min || input > max) {
 *         throw new OutOfRange("Input must be between " + min + " and " + max);
 *     }
 * </pre>
 *
 * <p>
 * <b>Features:</b></p>
 * <ul>
 * <li>Customizable error messages for better debugging and user feedback.</li>
 * <li>Extends the {@link Exception} class to provide meaningful context for
 * invalid input errors.</li>
 * </ul>
 *
 * @author Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
public class OutOfRange extends Exception {

    /**
     * Constructs an {@code OutOfRange} exception with a default error message.
     *
     * <p>
     * This constructor initializes the exception with a standard message
     * indicating that the input is out of the allowed range.</p>
     */
    public OutOfRange() {
        super("Input is out of allowed range");
    }

    /**
     * Constructs an {@code OutOfRange} exception with a specified error
     * message.
     *
     * <p>
     * This constructor allows the exception to be initialized with a custom
     * message, providing more detailed information about the error.</p>
     *
     * @param message The error message to be displayed.
     */
    public OutOfRange(String message) {
        super(message);
    }
}
