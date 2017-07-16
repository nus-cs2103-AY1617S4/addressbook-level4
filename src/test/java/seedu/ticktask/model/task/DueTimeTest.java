package seedu.ticktask.model.task;

import static org.junit.Assert.*;

import org.junit.Test;
//@@author A0138471A 
public class DueTimeTest {

    @Test
    public void isValidTimeReturnsFalse() {
        assertFalse(DueTime.isValidTime(""));//emptystring
        assertFalse(DueTime.isValidTime(" "));//space
        assertFalse(DueTime.isValidTime("garbage string")); //garbage string
        assertFalse(DueTime.isValidTime("Garbage Strign with time 12:30")); //garbage string with time
    }
    
    @Test
    public void isVlaidTimeReturnsTrue() {
        assertTrue(DueTime.isValidTime("start time"));//start time string without a time
        assertTrue(DueTime.isValidTime("start time with a time 12:30"));//start time with a time
    }
    

}
