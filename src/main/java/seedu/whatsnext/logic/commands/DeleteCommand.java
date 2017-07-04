package seedu.whatsnext.logic.commands;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.person.BaseTask;
import seedu.whatsnext.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a task identified using it's last displayed index from the task manager.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the task identified by the index number used in the last task listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";

    public final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<BaseTask> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        BaseTask taskToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteTask(taskToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
      
        return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
    }

}
