package seedu.ticktask.logic.commands;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the TickTask.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: TASK TYPE(\"complete\" or \"active\" or blank) + INDEX (must be a positive integer)\n"
            + "Example: \"" + COMMAND_WORD + " complete 1\""
            + " or \"" + COMMAND_WORD + " active 1\""
            + " or \"" + COMMAND_WORD + " 1\"";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted task: %1$s";

    public final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToMark = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteTask(taskToMark);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target task cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToMark ));
    }

}