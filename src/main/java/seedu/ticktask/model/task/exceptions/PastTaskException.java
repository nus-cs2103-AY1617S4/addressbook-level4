package seedu.ticktask.model.task.exceptions;

import seedu.ticktask.commons.exceptions.IllegalValueException;

//@@author A0139964M
/**
 * Signals that the operation will result in duplicate Task objects.
 */
public class PastTaskException extends IllegalValueException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PastTaskException() {
        super("");
    }

    public PastTaskException(String string) {
        super(string);
    }
}
//@@author 