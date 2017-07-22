package seedu.ticktask.logic.parser;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.logic.commands.CompleteCommand;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.logic.commands.EditCommand;
import seedu.ticktask.logic.commands.FindCommand;
import seedu.ticktask.logic.commands.ListCommand;
import seedu.ticktask.logic.commands.RedoCommand;
import seedu.ticktask.logic.commands.RestoreCommand;
import seedu.ticktask.logic.commands.SelectCommand;
import seedu.ticktask.logic.commands.StorageCommand;
import seedu.ticktask.logic.commands.UndoCommand;
import seedu.ticktask.logic.commands.HelpCommand;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.junit.Test;

//@@author A0131884B
public class parseHelpCommandTest {
    private ParseHelpCommand parser = new ParseHelpCommand();

    @Test
    public void helpMessage_correctCommand_correctlyDisplayed() throws Exception {

        Field field = HelpCommand.class.getDeclaredField("usageMessage");
        field.setAccessible(true);

        HelpCommand helpCommandAdd =  parser.parse(AddCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandAdd), AddCommand.MESSAGE_USAGE);

        HelpCommand helpCommandDelete =  parser.parse(DeleteCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandDelete), DeleteCommand.MESSAGE_USAGE);

        HelpCommand helpCommandClear =  parser.parse(ClearCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandClear), ClearCommand.MESSAGE_USAGE);

        HelpCommand helpCommandComplete =  parser.parse(CompleteCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandComplete), CompleteCommand.MESSAGE_USAGE);

        HelpCommand helpCommandEdit =  parser.parse(EditCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandEdit), EditCommand.MESSAGE_USAGE);

        HelpCommand helpCommandFind =  parser.parse(FindCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandFind), FindCommand.MESSAGE_USAGE);

        HelpCommand helpCommandList =  parser.parse(ListCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandList), ListCommand.MESSAGE_USAGE);

        HelpCommand helpCommandRedo =  parser.parse(RedoCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandRedo), RedoCommand.MESSAGE_USAGE);

        HelpCommand helpCommandRestore =  parser.parse(RestoreCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandRestore), RestoreCommand.MESSAGE_USAGE);

        HelpCommand helpCommandSelect =  parser.parse(SelectCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandSelect), SelectCommand.MESSAGE_USAGE);

        HelpCommand helpCommandUndo =  parser.parse(UndoCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandUndo), UndoCommand.MESSAGE_USAGE);

        HelpCommand helpCommandStorage =  parser.parse(StorageCommand.COMMAND_WORD);
        assertEquals(field.get(helpCommandStorage), StorageCommand.MESSAGE_USAGE);
    }
}
