package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.parser.Prefix;
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
    public void executeOnModelCreatesEmptyModel_successful() {
                
        ClearCommand clearCommand = prepareClearCommand();
        clearCommand.execute();
        
        assertEquals(model, emptyModel);
    }
    
    public ClearCommand prepareClearCommand() {
    	Prefix PREFIX_ACTIVE = new Prefix("active");
        ClearCommand command = new ClearCommand(PREFIX_ACTIVE);
        command.setData(model, new CommandHistory());  
        return command;
    }
    
}
//@@author
