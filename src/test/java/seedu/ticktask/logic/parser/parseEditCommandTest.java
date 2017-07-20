package seedu.ticktask.logic.parser;

import org.junit.Test;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.EditCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.testutil.EditTaskDescriptorBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.*;
import static seedu.ticktask.testutil.EditCommandTestUtil.*;
import static seedu.ticktask.testutil.TypicalTasksCompleted.INDEX_FIRST_TASK;
import static seedu.ticktask.testutil.TypicalTasksCompleted.INDEX_SECOND_TASK;

//@@author A0139964M
public class parseEditCommandTest {
    
    private static final String NAME_DESC_MEETING = " " + PREFIX_NAME + VALID_NAME_MEETING;
    private static final String NAME_DESC_EVENT = " " + PREFIX_NAME + VALID_NAME_EVENT;
    private static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_HOMEWORK;
    private static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_TODO;
    private static final String TAG_EMPTY = " " + PREFIX_TAG;
    
    private static final String INVALID_NAME_DESC = " " + PREFIX_NAME + ""; // empty names not allowed
    private static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCommand.MESSAGE_USAGE);
    
    private ParseEditCommand parser = new ParseEditCommand();
    
    @Test
    public void parse_missingParts_failure() throws IllegalValueException {
        // no field specified
        assertParseFailure(PREFIX_NAME + "1", MESSAGE_INVALID_FORMAT);
        
        // no index and no field specified
        assertParseFailure("", MESSAGE_INVALID_FORMAT);
    
        // no index specified
        assertParseFailure(VALID_NAME_MEETING, MESSAGE_INVALID_FORMAT);
    }
    
    @Test
    public void parse_invalidPreamble_failure() throws IllegalValueException {
        // negative index
        assertParseFailure("-5" + NAME_DESC_MEETING, MESSAGE_INVALID_FORMAT);
    
        // zero index
        assertParseFailure("0" + NAME_DESC_MEETING, MESSAGE_INVALID_FORMAT);
    
        // invalid arguments being parsed as preamble
        assertParseFailure("1 some random string", MESSAGE_INVALID_FORMAT);
    
        // invalid prefix being parsed as preamble
        assertParseFailure("1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidName_failure() throws IllegalValueException {
        assertParseFailure("" + INDEX_FIRST_TASK + PREFIX_NAME + "@", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_byIndexAllFieldsSpecified_success() throws Exception {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = "2 " + PREFIX_NAME + VALID_NAME_EVENT  + PREFIX_DATE_EDIT + VALID_DATE_5_DEC
                           + PREFIX_TIME_EDIT + VALID_TIME_5PM + PREFIX_TAG + VALID_TAG_TODO;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_MEETING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex,descriptor);
        assertParseSuccess(userInput, expectedCommand);
    }
    
    @Test
    public void parse_byIndexSomeFieldsSpecified_success() throws Exception {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = "2 " + PREFIX_NAME + VALID_NAME_EVENT  + PREFIX_DATE_EDIT + VALID_DATE_5_DEC;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_MEETING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex,descriptor);
        assertParseSuccess(userInput, expectedCommand);
    }
    
    
    
    /**
     * Asserts the parsing of {@code userInput} is successful and the result matches {@code expectedCommand}
     */
    private void assertParseSuccess(String userInput, EditCommand expectedCommand) throws Exception {
        EditCommand command = parser.parse(userInput);
        assertTrue(expectedCommand instanceof EditCommand);
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
//@@author