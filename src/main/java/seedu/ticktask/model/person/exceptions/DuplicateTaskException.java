package seedu.ticktask.model.person.exceptions;

import seedu.ticktask.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateTaskException extends DuplicateDataException {
    public DuplicateTaskException() {
        super("Operation would result in duplicate persons");
    }
}
