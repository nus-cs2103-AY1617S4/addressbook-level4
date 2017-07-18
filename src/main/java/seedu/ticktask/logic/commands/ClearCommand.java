package seedu.ticktask.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.parser.Prefix;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.logic.commands.exceptions.CommandException;

/**
 * Clears the TickTask program.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "The TickTask program has been cleared!";
    public static final String MESSAGE_USAGE = "";
    
    private Prefix listIndicatorPrefix;

    public ClearCommand(Prefix listIndicatorPrefix) {
         this.listIndicatorPrefix = listIndicatorPrefix;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        System.out.println("Run\n");
        if (listIndicatorPrefix.toString().equals(PREFIX_ACTIVE))
        {
        	model.resetActiveData(new TickTask());
        	System.out.println("Active\n");
        }
        else {
            model.resetCompleteData(new TickTask());
            System.out.println("Complete\n");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
