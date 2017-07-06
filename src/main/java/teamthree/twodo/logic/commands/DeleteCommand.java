package teamthree.twodo.logic.commands;

import teamthree.twodo.commons.core.Messages;
import teamthree.twodo.commons.core.UnmodifiableObservableList;
import teamthree.twodo.commons.core.index.Index;
import teamthree.twodo.logic.commands.exceptions.CommandException;
import teamthree.twodo.model.task.ReadOnlyTask;
import teamthree.twodo.model.task.exceptions.TaskNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Task: %1$s";

    public final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyTask personToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteTask(personToDelete);
        } catch (TaskNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

}
