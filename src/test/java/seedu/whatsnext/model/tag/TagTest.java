package seedu.whatsnext.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsnext.commons.exceptions.IllegalValueException;



public class TagTest {

    //@@author A0156106M
    @Test
    public void isValidTag() {
        // invalid tag
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName("#tag")); // contains special characters

        // valid tag
        assertTrue(Tag.isValidTagName("tag")); // empty string
        assertTrue(Tag.isValidTagName("high")); // empty string
    }

    @Test
    public void isValidPriorityTag() throws IllegalValueException {
        // invalid reserved tag
        Tag nonPriorityTag = new Tag("tag");
        assertFalse(nonPriorityTag.isPriorityTag()); // empty string

        Tag priorityTagHigh = new Tag("high");
        assertTrue(priorityTagHigh.isPriorityTag());

        Tag priorityTagMedium = new Tag("medium");
        assertTrue(priorityTagMedium.isPriorityTag());

        Tag priorityTagLow = new Tag("low");
        assertTrue(priorityTagLow.isPriorityTag());
    }

}
