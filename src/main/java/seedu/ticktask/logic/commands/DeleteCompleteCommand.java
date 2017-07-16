
package seedu.ticktask.logic.commands;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

//@@author A0147928N
/**
 * Deletes a task identified using it's last displayed index from the TickTask.
 */
public class DeleteCompleteCommand extends Command {

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final Index targetIndex;

    public DeleteCompleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredCompletedTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteCompletedTask(taskToMark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToMark));
    }
}
//@@author
