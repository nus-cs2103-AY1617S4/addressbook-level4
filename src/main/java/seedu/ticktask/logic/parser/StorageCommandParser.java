package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import java.io.File;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.StorageCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

//@@author A0138471A
/**
 * Parses input arguments for ListCommand Object
 */
public class StorageCommandParser {

	public static final String MESSAGE_INVALID_LOCATION = "Location does not exist!";
    /**
     * Parses the given {@code String} of arguments in the context of the StorageCommand
     * and returns an StorageCommand object for execution.
     */
	public Command parse(String args) throws ParseException {

		String stringLocation = args;
		File trimmedLocation = new File(args.trim());

		if (("".equals(stringLocation))) {
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StorageCommand.MESSAGE_USAGE));

		}

		if (!trimmedLocation.exists()) {
			throw new ParseException(String.format(MESSAGE_INVALID_LOCATION, StorageCommand.MESSAGE_USAGE));
		}

		if (!(stringLocation.substring(stringLocation.length() - 1).equals("/"))) {
			stringLocation += "/";
		}
		
		stringLocation += "ticktask.xml";
		File location = new File(stringLocation.trim());

		return new StorageCommand(location);
	}
}
