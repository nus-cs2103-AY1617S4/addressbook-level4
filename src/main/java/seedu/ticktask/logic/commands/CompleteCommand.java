//@@author A0147928N

package seedu.ticktask.logic.commands;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

/**
 * Marks a task identified using it's last displayed index as complete.
 */
public class CompleteCommand extends Command {

    public static final String COMMAND_WORD = "complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the task identified by the index number as being completed. Task is moved to completed list. \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COMPLETE_TASK_SUCCESS = "Completed task: %1$s";

    public final Index targetIndex;

    public CompleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskCompleted = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.completeTask(taskCompleted);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_COMPLETE_TASK_SUCCESS, taskCompleted ));
    }
}
//@@author
