package seedu.ticktask.model.task;

import static org.junit.Assert.*;

import org.junit.Test;
//@@author A0138471A
public class DueDateTest {

    @Test
    public void isValidTimeReturnsFalse() {

        assertFalse(DueDate.isValidDate(""));//emptystring
        assertFalse(DueDate.isValidDate(" "));//space
        assertFalse(DueDate.isValidDate("garbage string")); //garbage string
        assertFalse(DueDate.isValidDate("garbage string with time 10/10/2017")); //garbage string with date
    }
    
    @Test
    public void isVlaidTimeReturnsTrue() {
        assertTrue(DueDate.isValidDate("start date"));//start time string without a time
        assertTrue(DueDate.isValidDate("start date with a time 10/10/2017"));//start time with a date
    }

}
