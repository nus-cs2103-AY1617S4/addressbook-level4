package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

//@@author A0156106M
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
        String clearArgument = argMultimap.getPreamble().trim();
        if (!isArgumentValidPrefixesPresent(clearArgument)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        }
        return new ClearCommand(clearArgument);
    }

    private boolean isArgumentValidPrefixesPresent(String clearArgument) {
        return clearArgument.matches(".*\\b(completed|incomplete|all)\\b.*");
    }
}
