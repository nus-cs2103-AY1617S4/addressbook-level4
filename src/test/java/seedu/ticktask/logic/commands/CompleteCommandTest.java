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
import seedu.ticktask.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code CompleteCommand}.
 */
public class CompleteCommandTest {

    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    
    public CompleteCommandTest() {    
    }
    
//    @Test
//    public void execute_validIndexUnfilteredList_success() throws Exception {
//        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
//        CompleteCommand completeCommand = prepareCommand(INDEX_FIRST_TASK);
//
//        String expectedMessage = String.format(CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS, taskToDelete);
//
//        ModelManager expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());
//        expectedModel.deleteIndexActiveTask(taskToDelete);
//
//        CommandTestUtil.assertCommandSuccess(completeCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        CompleteCommand completeCommand = prepareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(completeCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

//    @Test
//    public void execute_validIndexFilteredList_success() throws Exception {
//        showFirstTaskOnly(model);
//
//        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
//        CompleteCommand completeCommand = prepareCommand(INDEX_FIRST_TASK);
//
//        String expectedMessage = String.format(CompleteCommand.MESSAGE_COMPLETE_TASK_SUCCESS, taskToDelete);
//
//        Model expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());
//        expectedModel.deleteIndexActiveTask(taskToDelete);
//        showNoTask(expectedModel);
//
//        CommandTestUtil.assertCommandSuccess(completeCommand, model, expectedMessage, expectedModel);
//    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of TickTask list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTickTask().getTaskList().size());

        CompleteCommand completeCommand = prepareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(completeCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code CompleteCommand} with the parameter {@code index}.
     */
    private CompleteCommand prepareCommand(Index index) {
        CompleteCommand completeCommand = new CompleteCommand(index);
        completeCommand.setData(model, new CommandHistory());
        return completeCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first task from the TickTask.
     */
    private void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getTickTask().getTaskList().get(0);
        final String[] splitName = task.getName().fullName.split("\\s+");
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
