package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.whatsnext.logic.commands.ListCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;


//@@author A0154986L
//@@author A0142675B
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    private static final String ARGUMENT_COMPLETED = "[completed]";
    private static final String ARGUMENT_INCOMPLETE = "[incomplete]";
    private static final String ARGUMENT_ALL = "[all]";

    public ListCommand parse(String args) throws ParseException {

        Set<String> keyWordSet = Collections.emptySet();
        String[] keyWordArray = null;

        /**
         * If the command "list" is used without any arguments, return an empty keyword set.
         */
        if (args.trim().isEmpty()) {
            return new ListCommand(keyWordSet);
        }

        keyWordArray = new String[] { args.trim() };
        keyWordSet = new HashSet<>(Arrays.asList(keyWordArray));
        if (!(keyWordSet.toString().equals(ARGUMENT_COMPLETED)
            || keyWordSet.toString().equals(ARGUMENT_INCOMPLETE)
            || keyWordSet.toString().equals(ARGUMENT_ALL))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
        return new ListCommand(keyWordSet);
    }

}
