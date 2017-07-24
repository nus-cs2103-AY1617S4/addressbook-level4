package seedu.ticktask.logic.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.CompleteCommand;
import seedu.ticktask.logic.commands.ListCommand;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.testutil.TypicalTasks;
//@@author A0138471A
public class ListCommandParserTest {
    private ListCommandParser listCommandParser = new ListCommandParser();
    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    private Model modelCopy = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    @Test
    public void testForInvalidCommandFormat_fail() {
        String invalidStorageCommand = "list vvsdfv";
        try {
            listCommandParser.parse(invalidStorageCommand);
        }
        catch (ParseException e) {
            return; 
        }
        
        fail();
    }
    
   @Test
   public void testForValidCommandFormat_equals() throws DuplicateTaskException, CommandException, IllegalValueException {
        String validTypeOfList = "floating";
            Command commandReturned = listCommandParser.parse(validTypeOfList);        
            Command commandExpected = new ListCommand(validTypeOfList);
                    
            commandReturned.setData(model, new CommandHistory());
            commandReturned.execute();
            
            assertFalse(model.equals(modelCopy));
            
            commandExpected.setData(modelCopy, new CommandHistory());
            commandExpected.execute();
            
            assertEquals(model, modelCopy);
    }

}
