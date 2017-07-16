
package seedu.ticktask.logic.commands;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.commands.exceptions.WarningException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

//@@author A0147928N

/**
 * Marks a task identified using it's last displayed index as complete.
 */
public class ConfirmCommand extends Command {

    public static final String MESSAGE_USAGE = "";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Confirmed task: %1$s";
    public static final String COMMAND_WORD = "yes";
    public static final String COMMAND_WORD1 = "no";

    public final boolean userDecision;

    public ConfirmCommand(boolean bool) {
        userDecision = bool;
    }


    @Override
    public CommandResult execute() throws CommandException, WarningException {
        return new CommandResult("");
    }
}
//@@author
