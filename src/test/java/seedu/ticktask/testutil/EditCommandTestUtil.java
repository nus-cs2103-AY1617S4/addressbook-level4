package seedu.ticktask.testutil;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.EditCommand;

//@@author A0139964M
/**
 * Utility class containing the constants required for tests related to EditCommand
 */
public class EditCommandTestUtil {
    public static final String VALID_NAME_MEETING = "Meeting";
    public static final String VALID_NAME_EVENT = "Event";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    
    public static final EditTaskDescriptorBuilder DESC_MEETING;
    public static final EditCommand.EditTaskDescriptor DESC_EVENT;
    
    static {
        try {
            DESC_MEETING = new EditTaskDescriptorBuilder().withName(VALID_NAME_MEETING).withTags(VALID_TAG_HUSBAND);
            
            DESC_EVENT = new EditTaskDescriptorBuilder().withName(VALID_NAME_EVENT)
                    .withTags().build();
        } catch (IllegalValueException ive) {
            throw new AssertionError("Method should not fail.");
        }
    }
}
//@@author