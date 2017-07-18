package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.testutil.TickTaskBuilder;
import seedu.ticktask.testutil.TypicalTasks;

//@@author A0147928N

public class ClearCommandTest {
    
    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    private Model emptyModel = new ModelManager(new TickTaskBuilder().build(), new UserPrefs());
    
    @Test
    public void testExecute() {
        
        assertFalse(model.equals(emptyModel));
        
        ClearCommand clearCommand = prepareClearCommand();
        clearCommand.execute();
        
        assertEquals(model, emptyModel);
    }
    
    public ClearCommand prepareClearCommand() {
        ClearCommand command = new ClearCommand();
        command.setData(model, new CommandHistory());  
        return command;
    }
    
}
//@@author
