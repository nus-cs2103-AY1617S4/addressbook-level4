package seedu.whatsnext.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {
    //@@author A0156106M
    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(TaskDescription.isValidDescription("")); // empty string
        assertFalse(TaskDescription.isValidDescription(" ")); // spaces only
        assertFalse(TaskDescription.isValidDescription("^")); // only non-alphanumeric characters
        assertFalse(TaskDescription.isValidDescription("peter*")); // contains non-alphanumeric characters

        // valid description
        assertTrue(TaskDescription.isValidDescription("peter jack")); // alphabets only
        assertTrue(TaskDescription.isValidDescription("12345")); // numbers only
        assertTrue(TaskDescription.isValidDescription("peter the 2nd")); // alphanumeric characters
        assertTrue(TaskDescription.isValidDescription("Capital Tan")); // with capital letters
        assertTrue(TaskDescription.isValidDescription("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
