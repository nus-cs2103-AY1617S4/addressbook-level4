package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.RemindCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

//@@author A0154986L
/**
 * Parses input arguments and creates a new RemindCommand object
 */
public class RemindCommandParser {
    public static final String BLANK_STRING = "";
    public Command parse(String args) throws ParseException {

        args = args.trim();

        if (args.matches(".*\\b(^[1-9]+[0-9]*)\\s+(minute|hour|day|week|month|year)\\b.*")) {
            return new RemindCommand(args);
        } else if (args == null || args.isEmpty()) {
            return new RemindCommand(BLANK_STRING);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }
    }

}
