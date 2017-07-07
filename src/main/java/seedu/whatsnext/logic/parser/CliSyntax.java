package seedu.whatsnext.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_TAG = new Prefix("h/");
    //@@author A0156106M
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("m/");
    public static final Prefix PREFIX_TASK_BASIC = new Prefix("basic");
    public static final Prefix PREFIX_TASK_DEADLINE = new Prefix("deadline");
    public static final Prefix PREFIX_TASK_EVENT = new Prefix("event");

}
