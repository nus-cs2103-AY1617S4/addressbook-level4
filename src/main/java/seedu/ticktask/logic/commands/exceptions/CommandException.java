package seedu.ticktask.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link CommandException}.
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}
