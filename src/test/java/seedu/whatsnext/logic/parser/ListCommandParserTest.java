package seedu.whatsnext.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.ListCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

//@@author A0142675B
public class ListCommandParserTest {

    private static final String INVALID_INPUT = ListCommand.COMMAND_WORD + "blah blah blah";

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnListCommand() throws Exception {
        ListCommand expectedCommand = new ListCommand("");
        assertParseSuccess("", expectedCommand);

        expectedCommand = new ListCommand(ListCommand.LIST_COMPLETED);
        assertParseSuccess(ListCommand.LIST_COMPLETED, expectedCommand);

        expectedCommand = new ListCommand(ListCommand.LIST_INCOMPLETE);
        assertParseSuccess(ListCommand.LIST_INCOMPLETE, expectedCommand);

        expectedCommand = new ListCommand(ListCommand.LIST_ALL);
        assertParseSuccess(ListCommand.LIST_ALL, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);
        assertParseFailure(INVALID_INPUT, expectedMessage);
    }

    /**
     * Asserts the parsing of {@code userInput} is successful and the result matches {@code expectedCommand}
     */
    private void assertParseSuccess(String userInput, ListCommand expectedCommand) throws Exception {
        Command command = parser.parse(userInput);
        assert expectedCommand.equals(command);
    }

    /**
     * Asserts the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            fail("An exception should have been thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
