package seedu.ticktask.logic.parser;

import org.junit.Test;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.CompleteCommand;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.testutil.TypicalTasks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

//@@author A0147928N
public class CompleteCommandParserTest {
    final int TASK_INDEX = 1;
    
    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    private Model modelCopy = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    
    private CompleteCommandParser completeCommandParser = new CompleteCommandParser();
    
    @Test
    public void testValidParse() throws ParseException, CommandException {
        String validCompleteCommand = "" + TASK_INDEX;
        
        CompleteCommand commandReturned = completeCommandParser.parse(validCompleteCommand);        
        CompleteCommand commandExpected = new CompleteCommand(Index.fromOneBased(TASK_INDEX));
                
        commandReturned.setData(model, new CommandHistory());
        commandReturned.execute();
        
        assertFalse(model.equals(modelCopy));
        
        commandExpected.setData(modelCopy, new CommandHistory());
        commandExpected.execute();
        
        assertEquals(model, modelCopy);
    }
    
    @Test
    public void testInvalidParse() {
        String invalidCompleteCommand = "complete";
        
        try {
            completeCommandParser.parse(invalidCompleteCommand);
        }
        catch (ParseException e) {
            return; 
        }
        
        fail();
    }
}
//@@author