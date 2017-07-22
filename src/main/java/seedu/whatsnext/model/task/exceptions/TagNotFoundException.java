package seedu.whatsnext.model.task.exceptions;

//@@author A0142675B
/**
 * Throws exceptions when the tag provided by the user is not found.
 */
public class TagNotFoundException extends Exception {
    public TagNotFoundException(String message) {
        super(message);
    }
}
