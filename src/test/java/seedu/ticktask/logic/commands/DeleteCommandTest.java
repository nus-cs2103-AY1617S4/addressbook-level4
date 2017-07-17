package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_FLOATINGTASK;
//import static seedu.ticktask.testutil.SampleEntries.INDEX_FIRST_ENTRY;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_SECOND_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.commands.CommandTestUtil;
import seedu.ticktask.logic.commands.DeleteFindCommand;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.ReadOnlyTask;
//import seedu.ticktask.testutil.SampleEntries;
import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.EventClashException;
import seedu.ticktask.model.task.exceptions.PastTaskException;
import seedu.ticktask.testutil.TypicalTasks;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    
    public DeleteCommandTest() throws PastTaskException, EventClashException {    
    }
// @@author A0131884B
    @Test
    public void execute_validFindUnfilteredList_success() throws Exception {
        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        
        String searchString = taskToDelete.getName().fullName.replaceAll("\\\\", "");
        HashSet<String> keywords = new HashSet<>(Arrays.asList(searchString.split("\\s+")));

        DeleteCommand deleteFindCommand = prepareFindCommand(model, keywords);
        String expectedMessage = String.format(DeleteFindCommand.MESSAGE_SUCCESS, taskToDelete);
        ModelManager expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());

        CommandResult result = deleteFindCommand.execute();
        assertEquals(result.feedbackToUser, expectedMessage);
    }
    
    @Test
    public void execute_validIndexActiveUnfilteredList_success() throws Exception {
        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        DeleteCommand deleteIndexCommand = prepareIndexActiveCommand(model, PREFIX_ACTIVE.toString(), INDEX_FIRST_TASK);
        String expectedMessage = String.format(DeleteIndexCommand.MESSAGE_SUCCESS, taskToDelete);
        ModelManager expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());
        CommandResult result = deleteIndexCommand.execute();
        assertEquals(result.feedbackToUser, expectedMessage);
    }
 // @@author    
    
//    @Test
//    public void execute_validIndexCompleteUnfilteredList_success() throws Exception {
//        ReadOnlyTask taskToDelete = model.getFilteredCompletedTaskList().get(INDEX_FIRST_TASK.getZeroBased());
//
//        DeleteCommand deleteIndexCommand = prepareIndexCompleteCommand(model, PREFIX_COMPLETE.toString(), INDEX_FIRST_TASK);
//        String expectedMessage = String.format(DeleteIndexCommand.MESSAGE_SUCCESS, taskToDelete);
//        ModelManager expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());
//        CommandResult result = deleteIndexCommand.execute();
//        assertEquals(result.feedbackToUser, expectedMessage);
//    }
 
    
//    @Test
//    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
//        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
//
//        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//    }

//    @Test
//    public void execute_validIndexFilteredList_success() throws Exception {
//        showFirstTaskOnly(model);
//
//        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
//        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_TASK);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);
//
//        Model expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());
//        expectedModel.deleteTask(taskToDelete);
//        showNoTask(expectedModel);
//
//        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
//    }

//    @Test
//    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
//        showFirstTaskOnly(model);
//
//        Index outOfBoundIndex = INDEX_SECOND_TASK;
//        // ensures that outOfBoundIndex is still in bounds of TickTask list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getTickTask().getTaskList().size());
//
//        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
//
//        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code keywords}.
     */
    private DeleteCommand prepareFindCommand(Model model, Set<String> keywords) {
        DeleteCommand deleteCommand = new DeleteFindCommand(keywords);
        deleteCommand.setData(model, new CommandHistory());
        return deleteCommand;
    }
    
 // @@author A0131884B   
    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index in active list}.
     */
    private DeleteCommand prepareIndexActiveCommand(Model model, String keywords, Index index) {
        DeleteCommand deleteCommand = new DeleteIndexCommand(index, PREFIX_ACTIVE);
        deleteCommand.setData(model, new CommandHistory());
        return deleteCommand;
    }
 // @@author   
//    /**
//     * Returns a {@code DeleteCommand} with the parameter {@code index in complete list}.
//     */
//    private DeleteCommand prepareIndexCompleteCommand(Model model, String keywords, Index index) {
//        DeleteCommand deleteCommand = new DeleteIndexCommand(index, PREFIX_COMPLETE);
//        deleteCommand.setData(model, new CommandHistory());
//        return deleteCommand;
//    }

    
    /**
     * Updates {@code model}'s filtered list to show only the first task from the TickTask.
     */
//    private void showFirstTaskOnly(Model model) {
//        ReadOnlyTask task = model.getTickTask().getTaskList().get(0);
//        final String[] splitName = task.getName().fullName.split("\\s+");
//        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));
//
//        assert model.getFilteredTaskList().size() == 1;
//    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(Collections.emptySet());

        assert model.getFilteredTaskList().isEmpty();
    }
}
