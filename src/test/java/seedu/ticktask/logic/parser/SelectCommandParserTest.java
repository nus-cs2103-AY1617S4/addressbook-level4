package seedu.ticktask.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ticktask.logic.commands.SelectCommand;
import seedu.ticktask.logic.parser.SelectCommandParser;
import seedu.ticktask.logic.parser.exceptions.ParseException;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() throws Exception {
        SelectCommand command = parser.parse("1");
        assertEquals(INDEX_FIRST_PERSON, command.targetIndex);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        parser.parse("a");
    }
}
