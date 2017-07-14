package seedu.whatsnext.testutil;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.EditCommand.EditTaskDescriptor;

/**
 * Utility class containing the constants required for tests related to EditCommand
 */
public class EditCommandTestUtil {

    public static final String VALID_NAME_PROJECTMEETING = "Project Meeting";
    public static final String VALID_NAME_PROJECTDEMO = "Project Demo";
    public static final String VALID_NAME_READBORNACRIME = "Read Born a Crime";
    public static final String VALID_STARTDATETIME_PROJECTMEETING = "this saturday 10am";
    public static final String VALID_ENDDATETIME_PROJECTMEETING = "this saturday 10pm";
    public static final String VALID_ENDDATETIME_PROJECTDEMO = "next monday";
    public static final String VALID_TAG_CS2103 = "CS2103";
    public static final String VALID_TAG_HIGH = "HIGH";
    public static final String VALID_TAG_MEDIUM = "MEDIUM";
    public static final String VALID_TAG_LOW = "LOW";
    public static final String VALID_DESCRIPTION_PROJECTMEETING = "Meet at Mac Common";
    public static final String VALID_DESCRIPTION_PROJECTDEMO = "Use Jar file instead of IDE";
    public static final String VALID_DESCRIPTION_READBORNACRIME = "By Trevor Noah";

    public static final EditTaskDescriptor DESC_PROJECTMEETING;
    public static final EditTaskDescriptor DESC_PROJECTDEMO;
    public static final EditTaskDescriptor DESC_READBORNACRIME;

    static {
        try {

            DESC_PROJECTMEETING = new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECTMEETING)
                    .withStartDateTime(VALID_STARTDATETIME_PROJECTMEETING)
                    .withEndDateTime(VALID_ENDDATETIME_PROJECTMEETING)
                    .withTags(VALID_TAG_CS2103)
                    .withDescription(VALID_DESCRIPTION_PROJECTMEETING).build();
            DESC_PROJECTDEMO = new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECTDEMO)
                    .withEndDateTime(VALID_ENDDATETIME_PROJECTDEMO)
                    .withTags(VALID_TAG_CS2103, VALID_TAG_HIGH)
                    .withDescription(VALID_DESCRIPTION_PROJECTDEMO).build();
            DESC_READBORNACRIME = new EditTaskDescriptorBuilder().withName(VALID_NAME_READBORNACRIME)
                    .withTags(VALID_TAG_LOW)
                    .withDescription(VALID_DESCRIPTION_READBORNACRIME).build();
        } catch (IllegalValueException ive) {
            throw new AssertionError("Method should not fail.");
        }
    }
}
