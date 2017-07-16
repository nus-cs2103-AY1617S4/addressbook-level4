package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.whatsnext.testutil.EditCommandTestUtil.DESC_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.DESC_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_DESCRIPTION_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_ENDDATETIME_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_NAME_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_HIGH;

import org.junit.Test;

import seedu.whatsnext.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.whatsnext.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() throws Exception {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_PROJECTMEETING);
        assertTrue(DESC_PROJECTMEETING.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PROJECTMEETING.equals(DESC_PROJECTMEETING));

        // null -> returns false
        assertFalse(DESC_PROJECTMEETING.equals(null));

        // different types -> returns false
        assertFalse(DESC_PROJECTMEETING.equals(5));

        // different values -> returns false
        assertFalse(DESC_PROJECTMEETING.equals(DESC_PROJECTDEMO));

        // different name -> returns false
        EditTaskDescriptor editedProjectMeeting =
                new EditTaskDescriptorBuilder(DESC_PROJECTMEETING).withName(VALID_NAME_PROJECTDEMO).build();
        assertFalse(DESC_PROJECTMEETING.equals(editedProjectMeeting));

        // different DateTime -> returns false
        editedProjectMeeting = new EditTaskDescriptorBuilder(DESC_PROJECTMEETING)
                               .withEndDateTime(VALID_ENDDATETIME_PROJECTDEMO).build();
        assertFalse(DESC_PROJECTMEETING.equals(editedProjectMeeting));

        // different description -> returns false
        editedProjectMeeting = new EditTaskDescriptorBuilder(DESC_PROJECTMEETING)
                               .withDescription(VALID_DESCRIPTION_PROJECTDEMO).build();
        assertFalse(DESC_PROJECTMEETING.equals(editedProjectMeeting));


        // different tags -> returns false
        editedProjectMeeting = new EditTaskDescriptorBuilder(DESC_PROJECTMEETING).withNewTags(VALID_TAG_HIGH).build();
        assertFalse(DESC_PROJECTMEETING.equals(editedProjectMeeting));
    }
}
