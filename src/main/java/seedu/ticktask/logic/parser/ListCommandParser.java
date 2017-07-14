package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.ListCommand;
import seedu.ticktask.logic.commands.SelectCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

public class ListCommandParser {
	public Command parse(String args) throws ParseException{
		 String typeOfList = args.trim().toLowerCase();
		 if (!ListCommand.isValidCommand(typeOfList)) {
			 throw new ParseException(
	                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
	        }
        return new ListCommand(typeOfList);
    }
}
