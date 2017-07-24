package seedu.ticktask.testutil;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.EditCommand;

//@@author A0139964M
/**
 * Utility class containing the constants required for tests related to EditCommand
 */
public class EditCommandTestUtil {
    public static final String VALID_NAME_MEETING = "Meeting ";
    public static final String VALID_NAME_EVENT = "Event ";
    public static final String VALID_TAG_HOMEWORK = "homework ";
    public static final String VALID_TAG_TODO = "todo ";
    public static final String VALID_DATE_29_JULY = "29 july ";
    public static final String VALID_DATE_5_DEC = "5 december ";
    public static final String VALID_DATE_16_NOV = "16 nov";
    public static final String VALID_TIME_5PM = "5pm ";
    public static final String VALID_TIME_1600 = "1600 ";

    
    public static final EditCommand.EditTaskDescriptor DESC_MEETING;
    public static final EditCommand.EditTaskDescriptor DESC_EVENT;
    
    static {
        try {
            DESC_MEETING = new EditTaskDescriptorBuilder().withName(VALID_NAME_MEETING).withDate(VALID_DATE_5_DEC)
                                                          .withTime(VALID_TIME_5PM).withTags(VALID_TAG_HOMEWORK);
            
            DESC_EVENT = new EditTaskDescriptorBuilder().withName(VALID_NAME_EVENT)
                    .withTags().build();
        } catch (IllegalValueException ive) {
            throw new AssertionError("Method should not fail.");
        }
    }
}
//@@author