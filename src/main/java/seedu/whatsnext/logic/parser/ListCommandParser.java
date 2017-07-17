package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.whatsnext.logic.commands.ListCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;


//@@author A0154986L
//@@author A0142675B
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    public ListCommand parse(String args) throws ParseException {
        /**
         * If the command "list" is used without any arguments, return an empty argument.
         */
        if (args.trim().isEmpty()) {
            return new ListCommand("");
        }

        if (!(args.trim().equals(ListCommand.LIST_COMPLETED)
            || args.trim().equals(ListCommand.LIST_INCOMPLETE)
            || args.trim().equals(ListCommand.LIST_ALL))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(args.trim());
    }

}
