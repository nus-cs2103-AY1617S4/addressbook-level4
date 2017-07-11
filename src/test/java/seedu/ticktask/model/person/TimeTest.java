package seedu.ticktask.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ticktask.model.task.DueTime;

public class TimeTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(DueTime.isValidTime("")); // empty string
        assertFalse(DueTime.isValidTime(" ")); // spaces only
        assertFalse(DueTime.isValidTime("91")); // less than 3 numbers
        assertFalse(DueTime.isValidTime("phone")); // non-numeric
        assertFalse(DueTime.isValidTime("9011p041")); // alphabets within digits
        assertFalse(DueTime.isValidTime("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(DueTime.isValidTime("911")); // exactly 3 numbers
        assertTrue(DueTime.isValidTime("93121534"));
        assertTrue(DueTime.isValidTime("124293842033123")); // long phone numbers
    }
}
