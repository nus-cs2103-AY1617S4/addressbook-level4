package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_SECOND_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        BasicTaskFeatures taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstTaskOnly(model);

        BasicTaskFeatures taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of tasks list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskManager().getTaskList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }



    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first task from the TaskManager.
     */
    private void showFirstTaskOnly(Model model) {

        BasicTaskFeatures task = model.getTaskManager().getTaskList().get(0);
        final String[] splitName = {task.getName().toString()};

        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));
        assert model.getFilteredTaskList().size() == 1;

    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(Collections.emptySet());

        assert model.getFilteredTaskList().isEmpty();
    }
}
