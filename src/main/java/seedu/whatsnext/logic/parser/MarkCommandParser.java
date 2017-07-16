package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.MarkCommand;
import seedu.whatsnext.logic.commands.UnmarkCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

public class MarkCommandParser {

    //@@author A0156106M
    /**
     * Parses the given {@code String} of arguments in the context of the MarkCommand
     * and returns a MarkCommand object for execution
     * @throws ParseException if the user input does not confirm the expected format
     * */
    public MarkCommand parseMarkCommand(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MarkCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkCommand
     * and returns an UnmarkCommand object for execution
     * @throws ParseException if the user input does not confirm the expected format
     * */
    public UnmarkCommand parseUnmarkCommand(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnmarkCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkCommand.MESSAGE_USAGE));
        }
    }

}
