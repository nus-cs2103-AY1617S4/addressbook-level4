package seedu.ticktask.model.task;

import static org.junit.Assert.*;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;
import seedu.ticktask.testutil.TaskBuilder;
import seedu.ticktask.testutil.TypicalTasks;

//@@author A0138471A
public class UniqueTaskListTest {
    
    UniqueTaskList list = new UniqueTaskList();
    UniqueTaskList listCopy = new UniqueTaskList();
    
    @Test
    public void testAdd() throws DuplicateTaskException {
        assertEquals(list, listCopy);
        
        ReadOnlyTask task = new TypicalTasks().getTypicalTasks()[INDEX_FIRST_TASK.getZeroBased()];
        list.add(task); 
        assertFalse(list.equals(listCopy));

    }
    
    @Test
    public void testEventClash() throws IllegalValueException {
        ReadOnlyTask task = new TaskBuilder().withDate("12/25/2019 - 2/25/2020").withType("event").build();
        ReadOnlyTask clashingTask = new TaskBuilder().withDate("12/25/2019 - 2/25/2020").withType("event").build();
        
        list.add(task);
        assertEquals(list.eventClash(clashingTask), task.getName().toString());
        
        ReadOnlyTask nonClashingTask = new TaskBuilder().withDate("12/25/2022 - 2/25/2023").withType("event").build();
        assertTrue(list.eventClash(nonClashingTask) == null);
    }
    
    @Test
    public void testArchive() throws IllegalValueException {
        ReadOnlyTask task = new TaskBuilder().withDate("12/25/2019 - 2/25/2020").withType("event").build();
        ReadOnlyTask taskCopy = new TaskBuilder().withDate("12/25/2019 - 2/25/2020").withType("event").build();
        
        assertTrue(task.equals(taskCopy));
        assertTrue(list.equals(listCopy));
        
        list.archive(task);
        listCopy.add(taskCopy);
        
        assertFalse(task.equals(taskCopy));
        assertFalse(list.equals(listCopy));
    }
    
    @Test
    public void testUpdateTask() throws IllegalValueException, TaskNotFoundException {
        ReadOnlyTask task = new TaskBuilder().withDate("12/25/2019 - 2/25/2020").withType("event").build();
        
        list.add(task);
        
        ReadOnlyTask editedTask = new TaskBuilder().withDate("12/25/2021 - 2/25/2023").withType("event").build();
        list.updateTask(task, editedTask);
        
        assertFalse(list.equals(listCopy));
    }
    
    @Test
    public void testRemove() throws IllegalValueException, TaskNotFoundException {
        ReadOnlyTask task = new TaskBuilder().withDate("12/25/2019 - 2/25/2020").withType("event").build();
        
        list.add(task);
        list.remove(task);
        
        assertEquals(list, listCopy);
    }
    
    @Test
    public void testSetTasks() throws IllegalValueException {
        ReadOnlyTask task = new TaskBuilder().withDate("12/25/2019 - 2/25/2020").withType("event").build();
        
        list.add(task);
        listCopy.setTasks(list);
        
        assertEquals(list, listCopy);
    }
}
