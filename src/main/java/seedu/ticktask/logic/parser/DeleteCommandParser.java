package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.logic.commands.DeleteCompleteCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteCommand object
 */


public class DeleteCommandParser {

    //Pattern if user specifies list to delete from
    public static final Pattern LIST_SPECIFIED_DELETE_COMMAND_FORMAT =
            Pattern.compile("(\\p{Alpha}+)(\\s)(\\p{Digit}+)");

    //Pattern if user specifies list to delete from
    public static final Pattern LIST_UNSPECIFIED_DELETE_COMMAND_FORMAT = Pattern.compile("(\\p{Digit}+)");

    public static final String ACTIVE_TASKS_STRING = "active";
    public static final String COMPLETE_TASKS_STRING = "complete";


    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {

        Matcher matcherIfListSpecified = LIST_SPECIFIED_DELETE_COMMAND_FORMAT.matcher(args.trim());
        Matcher matcherNoListSpecified = LIST_UNSPECIFIED_DELETE_COMMAND_FORMAT.matcher(args.trim());

        try {

            //If-else statement to return either DeleteCommand or DeleteCompleteCommand based on user input
            if (matcherNoListSpecified.matches()) {
                Index index = ParserUtil.parseIndex(matcherNoListSpecified.group(1));
                return new DeleteCommand(index);
            } else if (matcherIfListSpecified.matches()) {
                String listType = matcherIfListSpecified.group(1);
                Index index = ParserUtil.parseIndex(matcherIfListSpecified.group(3));

                if (listType.equals(ACTIVE_TASKS_STRING)) {
                    return new DeleteCommand(index);
                }
                if (listType.equals(COMPLETE_TASKS_STRING)) {
                    return new DeleteCompleteCommand(index);
                }
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            return null; //Will never be called

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }
}


