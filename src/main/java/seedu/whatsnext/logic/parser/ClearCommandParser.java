package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

/**
 * Parse input arguments and creates a new ClearCommand Object
 * */
public class ClearCommandParser {

    /**
     * Parse the given {@code String} of arguments in the context of the ClearCommand
     * and returns a ClearCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     * */
    public ClearCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        try {
            String clearArgument = argMultimap.getPreamble().trim();
            if (!isArgumentValidPrefixesPresent(clearArgument)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
            }
            return new ClearCommand(clearArgument);

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
    }

    private boolean isArgumentValidPrefixesPresent(String clearArgument) {
        return clearArgument.matches(".*\\b(all|mark|unmark)\\b.*");
    }
}
