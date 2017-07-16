package seedu.ticktask.model.task.exceptions;

import seedu.ticktask.commons.exceptions.IllegalValueException;

/**
 * Signals that the operation will result in duplicate Task objects.
 */
public class EventClashException extends IllegalValueException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EventClashException() {
        super("Operation would result in duplicate tasks");
    }

    public EventClashException(String string) {
        super(string);
    }
}
