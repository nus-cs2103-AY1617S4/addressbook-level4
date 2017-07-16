//@@author A0147928N
package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.CompleteCommand;
import seedu.ticktask.logic.commands.ConfirmCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ConfirmCommandParser {
    

    /**
     * Parses the given {@code String} of arguments in the context of the ConfirmCommand
     * and returns an ConfirmCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ConfirmCommand parse(String args) throws ParseException {
        String trimmedArg = args.trim();
        if (trimmedArg.equalsIgnoreCase("yes")) return new ConfirmCommand(true);
        else return new ConfirmCommand(false);
    }
}
//@@author
