package seedu.ticktask.logic.commands;

import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_SECOND_TASK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.testutil.TypicalTasks;

//@@author A0147928N
public class FindCommandTest {
        
    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    
    @Test
    public void testExecuteFullWord() {
        Set<String> keywords1 = new TreeSet<String>();
        keywords1.add("Wash");
        
        FindCommand command1 = new FindCommand(keywords1);
        command1.setData(model, new CommandHistory());
        
        ReadOnlyTask taskToFind = model.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ReadOnlyTask taskToNotFind = model.getFilteredActiveTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        
        command1.execute();
        
        assertTrue(model.getFilteredActiveTaskList().contains(taskToFind));
        assertFalse(model.getFilteredActiveTaskList().contains(taskToNotFind));
    }
    
    @Test
    public void testExecuteSubStringReturnsCorrectTask_success() {
        Set<String> keywords1 = new TreeSet<String>();
        keywords1.add("wa");
        
        FindCommand command1 = new FindCommand(keywords1);
        command1.setData(model, new CommandHistory());
        
        ReadOnlyTask taskToFind = model.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ReadOnlyTask taskToNotFind = model.getFilteredActiveTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        
        command1.execute();
        
        assertTrue(model.getFilteredActiveTaskList().contains(taskToFind));
        assertFalse(model.getFilteredActiveTaskList().contains(taskToNotFind));
    }
    
    @Test
    public void testExecutePowerSearchReturnsCorrectTask_sucessful() {
        Set<String> keywords1 = new TreeSet<String>();
        keywords1.add("hd");
        
        FindCommand command1 = new FindCommand(keywords1);
        command1.setData(model, new CommandHistory());
        
        ReadOnlyTask taskToFind = model.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ReadOnlyTask taskToNotFind = model.getFilteredActiveTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        
        command1.execute();
        
        assertTrue(model.getFilteredActiveTaskList().contains(taskToFind));
        assertFalse(model.getFilteredActiveTaskList().contains(taskToNotFind));
    }
            
}   
//@@author
