package seedu.ticktask.model.task;

import static org.junit.Assert.*;

import org.junit.Test;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.ClearCommand;

//@@author A0138471A
public class TaskTypeTest {
    private static final String DEADLINE = "deadline";
    private static final String EVENT = "event";

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
    //@@author

    //@@author A0139964M
    @Test
    public void isValidTaskGetValue() throws IllegalValueException {
        String deadLine = "deadline";
        TaskType taskType = new TaskType(deadLine);
        assertEquals(taskType.getValue(),deadLine);
    }

    @Test
    public void isValidSetType() throws IllegalValueException {
        String deadLine = "deadline";
        String event = "event";
        TaskType taskType = new TaskType(deadLine);
        taskType.setValue(event);
        assertEquals(taskType.toString(),event);

    }

    @Test
    public void equals() throws IllegalValueException  {
        final TaskType standardTaskType = new TaskType(DEADLINE);
        TaskType taskTypeWithSameValue = new TaskType(DEADLINE);
        TaskType taskWithDifferentValue = new TaskType(EVENT);

        // same values -> returns true
        assertTrue(standardTaskType.equals(taskTypeWithSameValue));

        // same object -> returns true
        assertTrue(standardTaskType.equals(standardTaskType));

        // null -> returns false
        assertFalse(standardTaskType.equals(null));

        // different types -> returns false
        assertFalse(standardTaskType.equals(new ClearCommand()));

        // different type -> returns false
        assertFalse(standardTaskType.equals(taskWithDifferentValue));
    }
    //@@author

}
