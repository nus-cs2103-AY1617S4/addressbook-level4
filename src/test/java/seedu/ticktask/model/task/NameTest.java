package seedu.ticktask.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ticktask.model.task.Name;

public class NameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("wash dog*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("wash dog")); // alphabets only
        assertTrue(Name.isValidName("tutorial")); // numbers only
        assertTrue(Name.isValidName("22nd birthday party")); // alphanumeric characters
        assertTrue(Name.isValidName("PARTAY")); // with capital letters
        assertTrue(Name.isValidName("meeting with the executive board")); // long names
    }
}
