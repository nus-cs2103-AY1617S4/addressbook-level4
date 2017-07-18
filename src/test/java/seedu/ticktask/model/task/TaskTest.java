package seedu.ticktask.model.task;

import static org.junit.Assert.*;

import java.util.TreeSet;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.tag.Tag;

//@@author A0147928N
public class TaskTest {
    Task task;
    
    @Test
    public void testResetTaskType() throws IllegalValueException {
        task = new Task(new Name("random"), new DueTime(""), new TaskType("deadline"), new DueDate(""), new TreeSet<Tag>());
        task.resetTaskType();
        
        assertEquals(task.getTaskType().toString(), "floating");
        
        task = new Task(new Name("random"), new DueTime(""), new TaskType("floating"), new DueDate("12/25/2018"), new TreeSet<Tag>());
        task.resetTaskType();
        
        assertEquals(task.getTaskType().toString(), "deadline");
        
        task = new Task(new Name("random"), new DueTime(""), new TaskType("floating"), new DueDate("12/25/2018 - 12/25/2019"), new TreeSet<Tag>());
        task.resetTaskType();
        
        assertEquals(task.getTaskType().toString(), "event");
    }
    
    @Test
    public void testGetCompleted() throws IllegalValueException {
        task = new Task(new Name("random"), new DueTime(""), new TaskType("deadline"), new DueDate(""), new TreeSet<Tag>());

        task.setCompleted(true);
        assertTrue(task.getCompleted());
    }
}
