package be.kdg.repaircafe.dom.exceptions;

/**
 * @author deketelw
 */
public class UserException extends RuntimeException {
    // https://programmeren3-repaircafe.rhcloud.com/repair-cafe-applicatie/repair-cafe-frontend/presentation-layer/error-handling/

    /**
     * This exception is thrown when a User related error occurs
     * For example: wrong password, or incorrect user name
     *
     * @param message
     */
    public UserException(String message) {
        super(message);
    }
}
