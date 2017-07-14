package seedu.whatsnext.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");

    //@@author A0156106M
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("m/");
    public static final Prefix PREFIX_TO = new Prefix("to");
    public static final Prefix PREFIX_ON = new Prefix("on");
    public static final Prefix PREFIX_ALL = new Prefix("all");
    public static final Prefix PREFIX_COMPLETED = new Prefix("completed");
    public static final Prefix PREFIX_INCOMPLETE = new Prefix("incomplete");

    //@@author A0142675B
    public static final Prefix PREFIX_NAME_ALTERNATIVE = new Prefix("name");
    public static final Prefix PREFIX_NAME_ALTERNATIVE_TO = new Prefix("name to");
    public static final Prefix PREFIX_DATE_TO = new Prefix("date to");
    public static final Prefix PREFIX_TIME_TO = new Prefix("time to");
    public static final Prefix PREFIX_DELETE_TAG = new Prefix("-t/");
    public static final Prefix PREFIX_NEW_TAG = new Prefix("+t/");
    public static final Prefix PREFIX_START_DATETIME = new Prefix("s/");
    public static final Prefix PREFIX_END_DATETIME = new Prefix("e/");
    public static final Prefix PREFIX_TAG_CLI = new Prefix("t/");
    public static final Prefix PREFIX_MESSAGE = new Prefix("m/");
}
