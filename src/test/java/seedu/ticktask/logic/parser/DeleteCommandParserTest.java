package seedu.ticktask.logic.parser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.logic.commands.DeleteFindCommand;
import seedu.ticktask.logic.commands.FindCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.testutil.EditCommandTestUtil.VALID_DATE_5_DEC;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;

import java.util.TreeSet;


public class DeleteCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private DeleteCommandParser deleteCommandParser = new DeleteCommandParser();
    
    //@@author A0131884B      
    private static final String TASK_INVALID_NONALPHANUMERIC = "abc!";
 
    @Test
    public void parse_emptyArgs_throwsDeleteParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(DeleteCommand.MESSAGE_USAGE));
        deleteCommandParser.parse("");
    }
    
    @Test
    public void parse_invalidArgsFollowedByValidArgs_returnsDeleteCommand() throws Exception {
        Command command = deleteCommandParser.parse(TASK_INVALID_NONALPHANUMERIC + PREFIX_DATE + VALID_DATE_5_DEC);
        assertTrue(command instanceof DeleteCommand);
    }
    //@@author
}