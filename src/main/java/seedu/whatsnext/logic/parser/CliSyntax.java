package seedu.whatsnext.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    //@@author A0156106M
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("m/");
    public static final Prefix PREFIX_TO = new Prefix("to");
    public static final Prefix PREFIX_ON = new Prefix("on");
    public static final Prefix PREFIX_TAG = new Prefix("tag");

    //@@author A0142675B
    public static final Prefix PREFIX_NAME_ALTERNATIVE = new Prefix("name");
    public static final Prefix PREFIX_NAME_ALTERNATIVE_TO = new Prefix("name to");
    public static final Prefix PREFIX_DATE_TO = new Prefix("date to");
    public static final Prefix PREFIX_TIME_TO = new Prefix("time to");
}
