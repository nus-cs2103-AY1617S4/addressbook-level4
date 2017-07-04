package seedu.whatsnext.logic.commands;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
<<<<<<< HEAD
import seedu.whatsnext.model.person.ReadOnlyPerson;
import seedu.whatsnext.model.person.exceptions.TaskNotFoundException;
=======
import seedu.whatsnext.model.person.BaseTask;
import seedu.whatsnext.model.person.exceptions.PersonNotFoundException;
>>>>>>> 1a0c747d27b60d55b19c56f833684db002183136

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

<<<<<<< HEAD
        UnmodifiableObservableList<ReadOnlyPerson> lastShownList = model.getFilteredTaskList();
=======
        UnmodifiableObservableList<BaseTask> lastShownList = model.getFilteredPersonList();
>>>>>>> 1a0c747d27b60d55b19c56f833684db002183136

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        BaseTask taskToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
<<<<<<< HEAD
            model.deleteTask(personToDelete);
        } catch (TaskNotFoundException pnfe) {
=======
            model.deleteTask(taskToDelete);
        } catch (PersonNotFoundException pnfe) {
>>>>>>> 1a0c747d27b60d55b19c56f833684db002183136
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, taskToDelete));
    }

}
