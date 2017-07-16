package seedu.ticktask.model.task;

import static org.junit.Assert.*;

import org.junit.Test;
//@@author A0138471A
public class TaskTypeTest {

    @Test
    public void isValidTaskTypeReturnsTrue() {
        assertTrue(TaskType.isValidTaskType("deadline"));
        assertTrue(TaskType.isValidTaskType("event"));
        assertTrue(TaskType.isValidTaskType("floating"));
    }
    @Test
    public void isValidTaskTypeReturnsFalse() {
        assertFalse(TaskType.isValidTaskType(""));
        assertFalse(TaskType.isValidTaskType(" "));
        assertFalse(TaskType.isValidTaskType("garbage"));
    }
    

}
