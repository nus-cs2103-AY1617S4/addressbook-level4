package seedu.whatsnext.model.task.exceptions;

public class TagNotFoundException extends Exception {
    public TagNotFoundException() {
        super("The given tag is not found");
    }
}
