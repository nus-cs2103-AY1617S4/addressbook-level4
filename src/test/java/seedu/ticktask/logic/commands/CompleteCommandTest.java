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
import seedu.ticktask.testutil.TypicalTasks;

//@@author A0147928N
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code CompleteCommand}.
 */
public class CompleteCommandTest {

    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    private ReadOnlyTask taskToComplete = model.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
    private CompleteCommand firstCommand = prepareIndexCommand(model, INDEX_FIRST_TASK);
    
    //UNIT TESTS
    
    /**
     * Tests whether the correct CommandResult is returned during execute()
     * @throws CommandException
     */
    @Test 
    public void testExecuteMessageReturnExpected_success() throws CommandException {
       
        String expectedMessage = String.format(firstCommand.MESSAGE_COMPLETE_TASK_SUCCESS, taskToComplete);
        CommandResult result = firstCommand.execute();
        assertEquals(result.feedbackToUser, expectedMessage);
    }
    
    //INTEGRATION TESTS
    
    /**
     * Tests whether command updates 'filteredActiveTasks' in model correctly
     * @throws CommandException
     */
    @Test
    public void testTaskListUpdatedCorrectly_success() throws CommandException {
        firstCommand.execute();
        UnmodifiableObservableList<ReadOnlyTask> currentTaskList = model.getFilteredActiveTaskList();
        assertFalse(currentTaskList.contains(taskToComplete));
    }
    
    /**
     * Tests whether command updates 'filteredCompletedTasks' in model correctly
     * @throws CommandException
     */
    @Test
    public void testCompletedTaskListUpdatedCorrectly_success() throws CommandException {
        firstCommand.execute();
        UnmodifiableObservableList<ReadOnlyTask> currentCompletedTaskList = model.getFilteredCompletedTaskList();
        assertTrue(currentCompletedTaskList.contains(taskToComplete));
    }
    
    //METHODS FOR TESTING
    
    /**
     * 
     * Creates new CompleteCommand to be used for testing
     * @param model
     * @param index
     * @return
     */
    private CompleteCommand prepareIndexCommand(Model model, Index index) {
        CompleteCommand completeCommand = new CompleteCommand(index);
        completeCommand.setData(model, new CommandHistory());
        return completeCommand;
    }
}

//@@author
