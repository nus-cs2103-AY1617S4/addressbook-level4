package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.EditCommand;
import seedu.ticktask.logic.commands.ListCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;
//@@author A0138471A
/**
 * Parses input arguments for ListCommand Object
 */
public class ListCommandParser {
	
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns an ListCommand object for execution.
     */
	public Command parse(String args) throws ParseException{
        String typeOfList = args.trim().toLowerCase();

        if (!ListCommand.isValidCommand(typeOfList)) {
        	throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            
        }

        return new ListCommand(typeOfList);
    }

}
