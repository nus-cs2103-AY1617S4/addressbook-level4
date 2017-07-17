package seedu.whatsnext.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.RemindCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

//@@author A0154986L
public class RemindCommandParserTest {

    private static final String VALID_INPUT_MINUTE = "30 minute";
    private static final String VALID_INPUT_DAY = "10 day";
    private static final String VALID_INPUT_HOUR = "6 hour";
    private static final String VALID_INPUT_WEEK = "2 week";
    private static final String VALID_INPUT_MONTH = "3 month";
    private static final String VALID_INPUT_YEAR = "1 year";

    private static final String INVALID_INPUT_NIL = "";
    private static final String INVALID_INPUT_ZERO = "0 year";
    private static final String INVALID_INPUT_LESS_THAN_0 = "-3 minute";
    private static final String INVALID_INPUT_DECIMAL = "2.1 hour";
    private static final String INVALID_INPUT_MISSPELL = "1 weeks";
    private static final String INVALID_INPUT_CAPS = "1 MONTH";

    private RemindCommandParser commandParser = new RemindCommandParser();

    @Test
    public void parse_validArgs_returnRemindCommand() throws Exception {
        RemindCommand expectedCommand = new RemindCommand(VALID_INPUT_MINUTE);
        assertParseSuccess(VALID_INPUT_MINUTE, expectedCommand);

        expectedCommand = new RemindCommand(VALID_INPUT_DAY);
        assertParseSuccess(VALID_INPUT_DAY, expectedCommand);

        expectedCommand = new RemindCommand(VALID_INPUT_HOUR);
        assertParseSuccess(VALID_INPUT_HOUR, expectedCommand);

        expectedCommand = new RemindCommand(VALID_INPUT_WEEK);
        assertParseSuccess(VALID_INPUT_WEEK, expectedCommand);

        expectedCommand = new RemindCommand(VALID_INPUT_MONTH);
        assertParseSuccess(VALID_INPUT_MONTH, expectedCommand);

        expectedCommand = new RemindCommand(VALID_INPUT_YEAR);
        assertParseSuccess(VALID_INPUT_YEAR, expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE);
        assertParseFailure(INVALID_INPUT_NIL, expectedMessage);
        assertParseFailure(INVALID_INPUT_ZERO, expectedMessage);
        assertParseFailure(INVALID_INPUT_LESS_THAN_0, expectedMessage);
        assertParseFailure(INVALID_INPUT_DECIMAL, expectedMessage);
        assertParseFailure(INVALID_INPUT_MISSPELL, expectedMessage);
        assertParseFailure(INVALID_INPUT_CAPS, expectedMessage);
    }

    /**
     * Asserts the parsing of {@code userInput} is successful and the result matches {@code expectedCommand}
     */
    private void assertParseSuccess(String userInput, RemindCommand expectedCommand) throws Exception {
        Command command = commandParser.parse(userInput);
        assert expectedCommand.equals(command);
    }

    /**
     * Asserts the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        try {
            commandParser.parse(userInput);
            fail("An exception should have been thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
