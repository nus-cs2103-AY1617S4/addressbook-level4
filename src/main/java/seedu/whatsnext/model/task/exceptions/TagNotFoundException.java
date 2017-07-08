package seedu.whatsnext.model.task.exceptions;

/*
 * Throws exceptions when the tag provided by the user is not found.
 * @@author A0142675B
 */
public class TagNotFoundException extends Exception {
    public TagNotFoundException() {
        super("The given tag is not found");
    }
}
