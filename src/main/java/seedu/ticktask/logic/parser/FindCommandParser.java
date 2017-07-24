package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.FindActiveCommand;
import seedu.ticktask.logic.commands.FindCommand;
import seedu.ticktask.logic.commands.FindCompleteCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

//@@author A0147928N
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {
    
    public static final Pattern FIND_COMMAND_ACTIVE = Pattern.compile("active.*"); 
    public static final Pattern FIND_COMMAND_COMPLETED = Pattern.compile("complete.*"); 

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        
        final String[] keywords = trimmedArgs.split("\\s+");
        final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
        
        Matcher matcherActive = FIND_COMMAND_ACTIVE.matcher(trimmedArgs);
        Matcher matcherCompleted = FIND_COMMAND_COMPLETED.matcher(trimmedArgs);
        
        if (matcherActive.matches()) {
            keywordSet.remove("active");
            return new FindActiveCommand(keywordSet);
        } else if (matcherCompleted.matches()) {
            keywordSet.remove("complete");
            return new FindCompleteCommand(keywordSet);
        } else {
            return new FindCommand(keywordSet);
        }       
    }

}
