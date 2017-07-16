//@@author A0147928N

package seedu.ticktask.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link Command}.
 */
public class WarningException extends Exception {
    public WarningException(String message) {
        super(message + "\nAre you sure you would like to coninue? (yes/no)");
    }
}

//@@author
