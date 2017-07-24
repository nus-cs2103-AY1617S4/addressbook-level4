package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_SECOND_TASK;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.testutil.TypicalTasks;


//@@author A0142675B
public class ResetCommandTest {

    private Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        BasicTaskFeatures taskToReset = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ResetCommand resetCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(ResetCommand.MESSAGE_RESET_TASK_SUCCESS, taskToReset);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        BasicTaskFeatures resetedTask = EditCommand.createNonOverlapTask(taskToReset);

        DateTime initDateTime = new DateTime();

        resetedTask.setStartDateTime(initDateTime);
        resetedTask.setEndDateTime(initDateTime);

        expectedModel.updateTask(taskToReset, resetedTask);

        CommandTestUtil.assertCommandSuccess(resetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeResetFloating_validIndexUnfilteredList_success() throws Exception {
        Index lastIndex = new Index(model.getFilteredTaskList().size() - 1);
        BasicTaskFeatures taskToReset = model.getFilteredTaskList().get(lastIndex.getZeroBased());
        ResetCommand resetCommand = prepareCommand(lastIndex);

        String expectedMessage = String.format(ResetCommand.MESSAGE_RESET_FLOATING_TASK, taskToReset);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        BasicTaskFeatures resetedTask = EditCommand.createNonOverlapTask(taskToReset);

        DateTime initDateTime = new DateTime();

        resetedTask.setStartDateTime(initDateTime);
        resetedTask.setEndDateTime(initDateTime);

        expectedModel.updateTask(taskToReset, resetedTask);

        CommandTestUtil.assertCommandSuccess(resetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ResetCommand resetCommand = prepareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(resetCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        String[] splitName = showFirstTaskOnly(model);

        BasicTaskFeatures taskToReset = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ResetCommand resetCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(ResetCommand.MESSAGE_RESET_TASK_SUCCESS, taskToReset);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        BasicTaskFeatures resetedTask = EditCommand.createNonOverlapTask(taskToReset);

        DateTime initDateTime = new DateTime();

        resetedTask.setStartDateTime(initDateTime);
        resetedTask.setEndDateTime(initDateTime);

        expectedModel.updateTask(taskToReset, resetedTask);
        showResetedTask(expectedModel, splitName);

        CommandTestUtil.assertCommandSuccess(resetCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of taskManager list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskManager().getTaskList().size());

        ResetCommand resetCommand = prepareCommand(outOfBoundIndex);

        CommandTestUtil.assertCommandFailure(resetCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    private ResetCommand prepareCommand(Index index) {
        ResetCommand resetCommand = new ResetCommand(index);
        resetCommand.setData(model, new CommandHistory());
        return resetCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show only the first task from the TaskManager.
     */
    private String[] showFirstTaskOnly(Model model) {

        BasicTaskFeatures task = model.getTaskManager().getTaskList().get(0);
        final String[] splitName = {task.getName().toString()};

        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));
        assert model.getFilteredTaskList().size() == 1;

        return splitName;
    }

    /**
     * Updates {@code model}'s filtered list to show the reseted Task.
     */
    private void showResetedTask(Model model, String[] splitName) {
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));
        assert model.getFilteredTaskList().size() == 1;
    }
}
