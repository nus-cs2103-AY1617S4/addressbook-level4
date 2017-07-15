package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.RemindCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

//@@author A0154986L
/**
* Parses input arguments and creates a new RemindCommand object
*/
public class RemindCommandParser {

    public Command parse(String args) throws ParseException {

        args = args.trim();

        try {
            if (!args.matches(".*\\b(\\d+)\\s+(minute|hour|day|week|month|year)\\b.*")) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
            }
            return new RemindCommand(args);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
        }
    }

}
