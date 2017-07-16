package seedu.ticktask.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("name ");
    public static final Prefix PREFIX_DATE = new Prefix("by ");
    public static final Prefix PREFIX_TIME = new Prefix("at ");
    public static final Prefix PREFIX_TASK_TYPE = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("# ");
 //@@author A0131884B
    public static final Prefix PREFIX_COMPLETE = new Prefix("/complete");
    public static final Prefix PREFIX_ACTIVE = new Prefix("/active");
 //@@author

}
