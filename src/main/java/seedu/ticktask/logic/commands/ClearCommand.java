package seedu.ticktask.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.parser.CliSyntax;
import seedu.ticktask.logic.parser.Prefix;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.logic.commands.exceptions.CommandException;

/**
 * Clears the TickTask program.
 */
//@@author A0131884B
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "The target list has been cleared!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Clear all tasks, active tasks or completed tasks.\n"
            + "Example: " + COMMAND_WORD + " all\n"
            + "Example: " + COMMAND_WORD + " active\n"
            + "Example: " + COMMAND_WORD + " complete\n";
    public static final String MESSAGE_NOT_SUCCESS = "The clear command should be either 'clear all' or 'clear active' or 'clear complete'.";
    private Prefix listIndicatorPrefix;

    public ClearCommand(Prefix listIndicatorPrefix) {
         this.listIndicatorPrefix = listIndicatorPrefix;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        if (listIndicatorPrefix.toString().equals(CliSyntax.PREFIX_ACTIVE.toString()))
        {
        	model.resetActiveData(new TickTask());
            return new CommandResult(MESSAGE_SUCCESS);
        }
        else if (listIndicatorPrefix.toString().equals(CliSyntax.PREFIX_COMPLETE.toString())) {
            model.resetCompleteData(new TickTask());
            return new CommandResult(MESSAGE_SUCCESS);
        }
        else if (listIndicatorPrefix.toString().equals(CliSyntax.PREFIX_ALL.toString())){
            model.resetData(new TickTask());
            return new CommandResult(MESSAGE_SUCCESS);
        }else {
            return new CommandResult(MESSAGE_NOT_SUCCESS);
        }
    }
}
