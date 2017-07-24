package seedu.whatsnext.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Contains integration test (interaction with the Model) and unit test for the name of the Task.
 */
public class NameTest {
    //@@author A0156106M
    @Test
    public void isValidName() {
        // invalid name
        assertFalse(TaskName.isValidName("")); // empty string
        assertFalse(TaskName.isValidName(" ")); // spaces only
        assertFalse(TaskName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(TaskName.isValidName("tom*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TaskName.isValidName("peter jack")); // alphabets only
        assertTrue(TaskName.isValidName("12345")); // numbers only
        assertTrue(TaskName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(TaskName.isValidName("Capital Tan")); // with capital letters
        assertTrue(TaskName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
