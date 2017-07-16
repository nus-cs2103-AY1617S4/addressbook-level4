package seedu.ticktask.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

import static org.junit.Assert.assertEquals;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;


public class DeleteCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private DeleteCommandParser parser = new DeleteCommandParser();
    
    @Test
    public void parse_validArgs_returnsDeleteCommand() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parse("1");
        assertEquals(INDEX_FIRST_TASK, command.targetIndex);
    }
    
    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        
        parser.parse("a");
    }
}