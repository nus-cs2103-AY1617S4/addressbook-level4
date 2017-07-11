package seedu.ticktask.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ticktask.model.task.DueDate;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(DueDate.isValidDate("")); // empty string
        assertFalse(DueDate.isValidDate(" ")); // spaces only

        // valid addresses
        assertTrue(DueDate.isValidDate("Blk 456, Den Road, #01-355"));
        assertTrue(DueDate.isValidDate("-")); // one character
        assertTrue(DueDate.isValidDate("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
