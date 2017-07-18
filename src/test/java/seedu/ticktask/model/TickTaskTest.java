package seedu.ticktask.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;

import java.util.Stack;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;
import seedu.ticktask.testutil.TaskBuilder;
import seedu.ticktask.testutil.TypicalTasks;
import seedu.ticktask.testutil.TypicalTasksCompleted;

//@@author A0147928N

public class TickTaskTest {
    
    TickTask tickTask = new TypicalTasks().getTypicalTickTask();
    TickTask tickTaskCompleted = new TypicalTasksCompleted().getTypicalTickTask();
    
    @Test
    public void testRemoveFindTask_success() throws TaskNotFoundException {
        ReadOnlyTask task = tickTask.getTaskList().get(0);
        
        tickTask.removeFindTask(task);
        
        assertFalse(tickTask.getTaskList().contains(task));
        
        ReadOnlyTask completedTask = tickTaskCompleted.getCompletedTaskList().get(0);
        
        tickTaskCompleted.removeFindTask(completedTask);
        
        assertFalse(tickTask.getCompletedTaskList().contains(completedTask));
    }
    
    @Test
    public void testRemoveFindTask_failure() throws IllegalValueException {
        ReadOnlyTask task = new TaskBuilder().build();
        
        try {
            tickTask.removeFindTask(task);
        } catch (TaskNotFoundException e) {
            return;
        } fail();
    }
    
    @Test
    public void testRemoveIndexActiveTask_success() throws TaskNotFoundException {
        ReadOnlyTask task = tickTask.getTaskList().get(0);
        
        tickTask.removeFindTask(task);
        
        assertFalse(tickTask.getTaskList().contains(task));
    }
    
    @Test
    public void testRemoveIndexActiveTask_failure() throws IllegalValueException {
        ReadOnlyTask task = new TaskBuilder().build();
        
        try {
            tickTask.removeIndexActiveTask(task);
        } catch (TaskNotFoundException e) {
            return;
        } fail();
    }
    
    @Test
    public void testCompleteTask_success() throws IllegalValueException, TaskNotFoundException {
        ReadOnlyTask task = tickTask.getTaskList().get(0);
        tickTask.completeTask(task);
        
        assertTrue(tickTask.getCompletedTaskList().contains(task));
        assertFalse(tickTask.getTaskList().contains(task));
    }
    
    @Test
    public void testCompleteTask_failure() throws IllegalValueException {
        ReadOnlyTask task = new TaskBuilder().build();
        
        try {
            tickTask.completeTask(task);
        } catch (TaskNotFoundException e) {
            return;
        } fail();
    }
    
    @Test
    public void testRestoreTask_success() throws IllegalValueException, TaskNotFoundException {
        ReadOnlyTask task = tickTask.getTaskList().get(0);
        tickTask.completeTask(task);
        tickTask.restoreTask(task);
        
        assertTrue(tickTask.getTaskList().contains(task));
        assertFalse(tickTask.getCompletedTaskList().contains(task));
    }
    
    @Test
    public void testRestoreTask_failure() throws IllegalValueException {
        ReadOnlyTask task = new TaskBuilder().build();
        
        try {
            tickTask.restoreTask(task);
        } catch (TaskNotFoundException e) {
            return;
        } fail();
    }
    
}
//@@author