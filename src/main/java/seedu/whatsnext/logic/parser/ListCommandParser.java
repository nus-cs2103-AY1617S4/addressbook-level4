package seedu.whatsnext.logic.parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.ListCommand;

//@@author A0154986L
/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser {

    public Command parse(String args) {

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
        return new ListCommand(keyWordSet);
    }

}
