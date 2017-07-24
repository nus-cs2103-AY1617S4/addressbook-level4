package seedu.ticktask.model.task;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
//@@author A0138471A
public class DueDateTest {

    @Test
    public void isValidTime_returnsFalse() {

        assertFalse(DueDate.isValidDate(""));//empty string
        assertFalse(DueDate.isValidDate(" "));//space
        assertFalse(DueDate.isValidDate("garbage string")); //garbage string
        assertFalse(DueDate.isValidDate("garbage string with time 10/10/2017")); //garbage string with date
    }
    
    @Test
    public void isValidTime_returnsTrue() {
        assertTrue(DueDate.isValidDate("start date"));//start time string without a time
        assertTrue(DueDate.isValidDate("start date with a time 10/10/2017"));//start time with a date
    }   
    //@@author
    
    //@@author A0147928N
    @Test
    public void testDueDateConstructor_success() throws IllegalValueException {        
        String dateString = "03/22/2019 - 03/22/2020";
        DueDate date = new DueDate(dateString);
        
        assertEquals(dateString, date.toString());
    }
    
    @Test
    public void testDueDateStartConstructor_success() throws IllegalValueException {
        DueDate date = new DueDate("start date 03/22/2019");
        
        assertEquals(date.getStartDate(), "03/22/2019");
    }
    
    @Test
    public void testDueDateEndConstructor_success() throws IllegalValueException {
        DueDate date = new DueDate("end date 03/22/2019");
        
        assertEquals(date.getEndDate(), "03/22/2019");
    }
    
    @Test
    public void testSetFloating_success() throws IllegalValueException {
        DueDate date = new DueDate("");
        date.setFloating();
        assertTrue(date.isFloating());
    }
    
    @Test
    public void testSetDeadline_success() throws IllegalValueException {
        DueDate date = new DueDate("03/22/2019");
        date.setDeadline();
        assertTrue(date.isDeadline());
    }
    
    @Test
    public void testSetRange_success() throws IllegalValueException {
        DueDate date = new DueDate("03/22/2019 - 03/22/2020");
        date.setRange();
        assertTrue(date.isRange());
    }
    
    @Test
    public void testExtractDate_success() throws IllegalValueException {
        DueDate date = new DueDate("");
        DueDate dateCopy = new DueDate("");
        
        date.extractDate("03/22/2019");
        assertFalse(date.equals(dateCopy));
        
        DueDate dateSameAsDate = new DueDate("03/22/2019");
        assertEquals(date, dateSameAsDate);
    }
    
    @Test
    public void testGetValue_success() throws IllegalValueException {
        DueDate date = new DueDate("03/22/2019");
        assertEquals(date.getValue(), "03/22/2019");
    }
    
    @Test
    public void testSetValue_success() throws IllegalValueException {
        DueDate date = new DueDate("03/22/2019");
        date.setValue("04/22/2019");
        assertEquals(date.getValue(), "04/22/2019");
    }
    
    @Test
    public void testSetEndDate_success() throws IllegalValueException {
        DueDate date = new DueDate("");
        
        LocalDate endDate = LocalDate.parse("2019-04-22");
        date.setEndDate(endDate);
        assertEquals(date.getLocalEndDate(), endDate);
    }
    
    @Test
    public void testSetStartDate_success() throws IllegalValueException {
        DueDate date = new DueDate("");
        
        LocalDate startDate = LocalDate.parse("2019-04-22");
        date.setStartDate(startDate);
        assertEquals(date.getLocalStartDate(), startDate);
    }
}
