package seedu.ticktask.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ticktask.model.task.Name;

//@@author A0147928N
public class NameTest {

    @Test
    public void isValidNameReturnFalse_success() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("wash dog*")); // contains non-alphanumeric characters
    }

    @Test
    public void isValidNameReturnTrue_success() {
        // valid name
        assertTrue(Name.isValidName("wash dog")); // alphabets only
        assertTrue(Name.isValidName("2200")); // numbers only
        assertTrue(Name.isValidName("22nd birthday party")); // alphanumeric characters
        assertTrue(Name.isValidName("PaRTAY")); // with capital letters
        assertTrue(Name.isValidName("meeting with the executive board")); // long names
    }
}