package seedu.ticktask.logic.parser;

import static org.junit.Assert.*;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.ticktask.testutil.EditCommandTestUtil.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0139964M
public class parseAddCommandTest {
    
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                         AddCommand.MESSAGE_USAGE);
    private static final String NAME_INVALID_NONALPHANUMERIC = "abc!";
    private static final String NAME_INVALID_EMPTY = "";
            
    private ParseAddCommand parser = new ParseAddCommand();
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void parse_emptyArgs_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(AddCommand.MESSAGE_USAGE));
        parser.parse("");
    }
    
    @Test
    public void parse_validArgs_returnsAddCommand() throws Exception {
        // floating task
        Command command = parser.parse(VALID_NAME_EVENT);
        assertTrue(command instanceof AddCommand);
        
        // deadline
        command = parser.parse(VALID_NAME_MEETING + PREFIX_DATE + VALID_DATE_5_DEC);
        assertTrue(command instanceof AddCommand);
        
        // event(Date)
        command = parser.parse(VALID_NAME_EVENT + PREFIX_DATE + VALID_DATE_16_NOV + "to " + VALID_DATE_5_DEC );
        assertTrue(command instanceof AddCommand);
        
        //event (date & time)
        command = parser.parse(VALID_NAME_EVENT + PREFIX_DATE + VALID_DATE_16_NOV + "to " + VALID_DATE_5_DEC
                               + PREFIX_TIME + VALID_TIME_5PM);
        assertTrue(command instanceof AddCommand);
    }
    
    @Test
    public void parse_invalidArgsFollowedByValidArgs_returnsAddCommand() throws Exception {
        Command command = parser.parse(NAME_INVALID_NONALPHANUMERIC + PREFIX_DATE + VALID_DATE_5_DEC);
        assertTrue(command instanceof AddCommand);
    }
    
    @Test
    //Add blankspace floating task  
    public void parse_invalidBlankName_returnsAddCommand() throws Exception {
        assertParseFailure(NAME_INVALID_EMPTY, MESSAGE_INVALID_FORMAT);
    }
    
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