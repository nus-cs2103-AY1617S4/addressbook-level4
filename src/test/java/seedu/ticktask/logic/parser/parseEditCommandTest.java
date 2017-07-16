package seedu.ticktask.logic.parser;

import org.junit.Test;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.EditCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ticktask.testutil.EditCommandTestUtil.*;

public class parseEditCommandTest {
    
    private static final String NAME_DESC_MEETING = " " + PREFIX_NAME + VALID_NAME_MEETING;
    private static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_EVENT;
    private static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    private static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    
    private static final String INVALID_NAME_DESC = " " + PREFIX_NAME + ""; // empty names not allowed
    private static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE);
    
    private parseEditCommand parser = new parseEditCommand();
    
    @Test
    public void parse_missingParts_failure() throws IllegalValueException {
        // no field specified
        assertParseFailure(PREFIX_NAME + "1", MESSAGE_INVALID_FORMAT);
        
        // no index and no field specified
        assertParseFailure("", MESSAGE_INVALID_FORMAT);
    
        // no index specified
        assertParseFailure(VALID_NAME_MEETING, MESSAGE_INVALID_FORMAT);
    }
    
 
    /**
     * Asserts the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) throws IllegalValueException, IndexOutOfBoundsException {
        try {
            parser.parse(userInput);
            fail("An exception should have been thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}