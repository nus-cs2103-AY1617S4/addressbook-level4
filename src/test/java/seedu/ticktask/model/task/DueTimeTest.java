package seedu.ticktask.model.task;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
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
    //@@author
    
    //@@author A0147928N
    @Test
    public void testSetEndTime_success() throws IllegalValueException {
        DueTime dueTime = new DueTime("end time 22:00");
        DueTime dueTimeCopy = new DueTime("end time 22:00");
        
        assertEquals(dueTime, dueTimeCopy);
        dueTime.setEndTime(LocalTime.parse("23:00"));
        assertFalse(dueTime.equals(dueTimeCopy));
        
        dueTimeCopy.setEndTime(LocalTime.parse("23:00"));
        assertEquals(dueTime, dueTimeCopy);
    }
    
    @Test
    public void testSetStartTime_success() throws IllegalValueException {
        DueTime dueTime = new DueTime("start time 22:00");
        DueTime dueTimeCopy = new DueTime("start time 22:00");
        
        assertEquals(dueTime, dueTimeCopy);
        dueTime.setStartTime(LocalTime.parse("23:00"));
        assertFalse(dueTime.equals(dueTimeCopy));
        
        dueTimeCopy.setStartTime(LocalTime.parse("23:00"));
        assertEquals(dueTime, dueTimeCopy);
    }
    
    @Test
    public void testExtractTime_success() throws IllegalValueException {
        DueTime dueTime = new DueTime("start time 22:00");
        DueTime dueTimeCopy = new DueTime("start time 22:00");
        assertEquals(dueTime, dueTimeCopy);
        
        dueTime.extractTime("22:00");
        assertFalse(dueTime.equals(dueTimeCopy));
        
        assertTrue(dueTime.getStartTime().equals("22:00"));
    }
    
    @Test
    public void testGetValue_success() throws IllegalValueException {
        DueTime dueTime = new DueTime("start time 22:00");
        assertEquals(dueTime.getValue(), "22:00");
    }
    
    @Test
    public void testGetEndTime_success() throws IllegalValueException {
        DueTime dueTime = new DueTime("end time 22:00");
        dueTime.setStartTime(LocalTime.parse("21:00"));
        assertEquals(dueTime.getEndTime(), "22:00");
    }
    
    @Test
    public void testGetStartTime_success() throws IllegalValueException {
        DueTime dueTime = new DueTime("start time 22:00");
        dueTime.setEndTime(LocalTime.parse("23:00"));
        assertEquals(dueTime.getStartTime(), "22:00");
    }
    
    @Test
    public void testSetFloating_success() throws IllegalValueException {
        DueTime time = new DueTime("");
        time.setFloating();
        assertTrue(time.isFloating());
    }
    
    @Test
    public void testSetDeadline_success() throws IllegalValueException {
        DueTime time = new DueTime("03/22/2019");
        time.setDeadline();
        assertTrue(time.isDeadline());
    }
    
    @Test
    public void testSetRange_success() throws IllegalValueException {
        DueTime time = new DueTime("03/22/2019 - 03/22/2020");
        time.setRange();
        assertTrue(time.isRange());
    }
}
