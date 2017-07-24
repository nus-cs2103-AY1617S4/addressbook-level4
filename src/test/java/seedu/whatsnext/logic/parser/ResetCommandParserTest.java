package seedu.whatsnext.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.whatsnext.logic.commands.ResetCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

//@@author A0142675B
public class ResetCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ResetCommandParser parser = new ResetCommandParser();

    @Test
    public void parse_validArgs_returnsResetCommand() throws Exception {
        ResetCommand command = parser.parse("1");
        assertEquals(INDEX_FIRST_TASK, command.targetIndex);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResetCommand.MESSAGE_USAGE));

        parser.parse("a");
    }

}
