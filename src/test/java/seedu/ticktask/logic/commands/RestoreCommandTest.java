package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.testutil.TypicalTasksCompleted;

//@@author A0147928N
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code RestoreCommand}.
 */
public class RestoreCommandTest {

    private Model model = new ModelManager(new TypicalTasksCompleted().getTypicalTickTask(), new UserPrefs());
    private ReadOnlyTask taskToRestore = model.getFilteredCompletedTaskList().get(INDEX_FIRST_TASK.getZeroBased());
    private RestoreCommand firstCommand = prepareIndexCommand(model, INDEX_FIRST_TASK);
    
    //UNIT TESTS
    
    /**
     * Tests whether the correct CommandResult is returned during execute()
     * @throws CommandException
     * @throws DuplicateTaskException 
     */
    @Test 
    public void testExecuteMessage() throws CommandException, DuplicateTaskException {
       
        String expectedMessage = String.format(firstCommand.MESSAGE_COMPLETE_TASK_SUCCESS, taskToRestore);
        CommandResult result = firstCommand.execute();
        assertEquals(result.feedbackToUser, expectedMessage);
    }
    
    //INTEGRATION TESTS
    
    /**
     * Tests whether command updates 'filteredActiveTasks' in model correctly
     * @throws CommandException
     * @throws DuplicateTaskException 
     */
    @Test
    public void testTaskListUpdatedCorrectly() throws CommandException, DuplicateTaskException {
        firstCommand.execute();
        UnmodifiableObservableList<ReadOnlyTask> currentTaskList = model.getFilteredTaskList();
        assertTrue(currentTaskList.contains(taskToRestore));
    }
    
    /**
     * Tests whether command updates 'filteredRestoredTasks' in model correctly
     * @throws CommandException
     * @throws DuplicateTaskException 
     */
    @Test
    public void testCompletedTaskListUpdatedCorrectly() throws CommandException, DuplicateTaskException {
        firstCommand.execute();
        UnmodifiableObservableList<ReadOnlyTask> currentRestoredTaskList = model.getFilteredCompletedTaskList();
        assertFalse(currentRestoredTaskList.contains(taskToRestore));
    }
    
    //METHODS FOR TESTING
    
    /**
     * 
     * Creates new RestoreCommand to be used for testing
     * @param model
     * @param index
     * @return
     */
    private RestoreCommand prepareIndexCommand(Model model, Index index) {
        RestoreCommand restoreCommand = new RestoreCommand(index);
        restoreCommand.setData(model, new CommandHistory());
        return restoreCommand;
    }
}

//@@author
