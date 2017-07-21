package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
//import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_FLOATINGTASK;
//import static seedu.ticktask.testutil.SampleEntries.INDEX_FIRST_ENTRY;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_SECOND_TASK;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.ticktask.model.util.SampleDataUtil;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.logic.parser.Prefix;
import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.testutil.TypicalTasks;

import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    
    public DeleteCommandTest() {    
    }
    
// @@author A0131884B
    
    /**
     * Unit tests for {@code DeleteIndexCommand} with integration tests (interaction with the Model class)
     */
    
    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyTask taskToDelete = model.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        DeleteCommand deleteIndexCommand = prepareIndexCommand(model, PREFIX_ACTIVE.toString(), INDEX_FIRST_TASK);
        String expectedMessage = String.format(DeleteIndexCommand.MESSAGE_SUCCESS, taskToDelete);
        CommandResult result = deleteIndexCommand.execute();
        assertEquals(result.feedbackToUser, expectedMessage);
    }
    
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActiveTaskList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex, PREFIX_ACTIVE);

        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
   
    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
    	showFirstTaskListOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of entry book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTickTask().getActiveTaskList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex, PREFIX_ACTIVE);

        CommandTestUtil.assertCommandFailure(deleteCommand, model,Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }
    /**
     * Unit tests for {@code DeleteFindCommand} with integration tests (interaction with the Model class)
     */
    
    @Test
    public void execute_validFindUnfilteredList_success() throws Exception {
        ReadOnlyTask taskToDelete = model.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        
        String searchString = taskToDelete.getName().fullName;
//        HashSet<String> keywords = new HashSet<>(Arrays.asList(searchString));

        DeleteCommand deleteFindCommand = prepareFindCommand(model, searchString);
        String expectedMessage = String.format(DeleteFindCommand.MESSAGE_SUCCESS, taskToDelete);
    
        CommandResult result = deleteFindCommand.execute();
        assertEquals(result.feedbackToUser, expectedMessage);
    }
    
    @Test
    public void execute_noEntryFoundUnfilteredList_returnsNoEntriesMessage() throws Exception {
        Model model = new ModelManager(SampleDataUtil.getSampleTickTask(), new UserPrefs());
        String searchString = "testing";
        DeleteCommand deleteCommand = prepareFindCommand(model, searchString);
        CommandResult result = deleteCommand.execute();

        assertEquals(result.feedbackToUser, DeleteFindCommand.MESSAGE_NO_TASKS);
    }
    
    /**
     * Returns a {@code DeleteCommand} with the parameter {@code keywords}.
     */
    private DeleteCommand prepareCommand(Index index, Prefix prefix) {
        DeleteCommand deleteCommand = new DeleteIndexCommand(index, prefix);
        deleteCommand.setData(model, new CommandHistory());
        return deleteCommand;
    }
     
    
    /**
     * Returns a {@code DeleteCommand} with the parameter {@code keywords}.
     */
    private DeleteCommand prepareFindCommand(Model model, String keywords) {
        DeleteCommand deleteCommand = new DeleteFindCommand(keywords);
        deleteCommand.setData(model, new CommandHistory());
        return deleteCommand;
    }
    
    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index in active list}.
     */
    private DeleteCommand prepareIndexCommand(Model model, String keywords, Index index) {
        DeleteCommand deleteCommand = new DeleteIndexCommand(index, PREFIX_ACTIVE);
        deleteCommand.setData(model, new CommandHistory());
        return deleteCommand;
    }
 // @@author   
    
    /**
     * Updates {@code model}'s filtered list to show only the first task from the TickTask.
     */
    private void showFirstTaskListOnly(Model model) {
        ReadOnlyTask task = model.getTickTask().getActiveTaskList().get(0);
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));

        assert model.getFilteredActiveTaskList().size() == 1;
    }
}

    /**
     * Updates {@code model}'s filtered list to show no one.
     */