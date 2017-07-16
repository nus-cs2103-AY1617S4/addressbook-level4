package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_SECOND_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.EventClashException;
import seedu.ticktask.model.task.exceptions.PastTaskException;
import seedu.ticktask.testutil.TypicalTasksCompleted;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCompleteCommand}.
 */
public class DeleteCompleteCommandTest {

    private Model model = new ModelManager(new TypicalTasksCompleted().getTypicalTickTask(), new UserPrefs());
        
    public DeleteCompleteCommandTest() throws PastTaskException, EventClashException {    
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyTask taskToDelete = model.getFilteredCompletedTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteCompleteCommand deleteCompleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteCompleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());
        expectedModel.deleteCompletedTask(taskToDelete);

        CommandTestUtil.assertCommandSuccess(deleteCompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompletedTaskList().size() + 1);
        DeleteCompleteCommand deleteCompleteCommand = prepareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskToDelete = model.getFilteredCompletedTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteCompleteCommand deleteCompleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteCompleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());
        expectedModel.deleteCompletedTask(taskToDelete);
        showNoTask(expectedModel);

        CommandTestUtil.assertCommandSuccess(deleteCompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of TickTask list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTickTask().getCompletedTaskList().size());

        DeleteCompleteCommand deleteCompleteCommand = prepareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(deleteCompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code DeleteCompleteCommand} with the parameter {@code index}.
     */
    private DeleteCompleteCommand prepareCommand(Index index) {
        DeleteCompleteCommand deleteCompleteCommand = new DeleteCompleteCommand(index);
        deleteCompleteCommand.setData(model, new CommandHistory());
        return deleteCompleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first task from the TickTask.
     */
    private void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getTickTask().getCompletedTaskList().get(0);
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));

        assert model.getFilteredCompletedTaskList().size() == 1;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(Collections.emptySet());

        assert model.getFilteredCompletedTaskList().isEmpty();
    }
}
