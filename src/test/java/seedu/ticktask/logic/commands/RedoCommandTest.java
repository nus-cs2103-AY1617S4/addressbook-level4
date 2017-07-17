package seedu.ticktask.logic.commands;

import static org.junit.Assert.*;

import java.util.EmptyStackException;
import java.util.Stack;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.testutil.TickTaskBuilder;;;
//@@author A0139819N
public class RedoCommandTest {
    
    @Test
    public void redoCommandSuccess() throws CommandException, IllegalValueException {
        
        ModelStubAcceptRedo modelStubAcceptRedo = new ModelStubAcceptRedo();
        modelStubAcceptRedo.redoUndoneCommand();
        
        assertTrue(modelStubAcceptRedo.getFutureProgramInstances().isEmpty());
        
        Stack<TickTask> filledStackForTestingPreviousInstances = new Stack<TickTask>();
        filledStackForTestingPreviousInstances.push(new TickTask());
        assertEquals(filledStackForTestingPreviousInstances, modelStubAcceptRedo.getPreviousProgramInstances());
    }
    
    @Test (expected = CommandException.class)
    public void redoCommandFailNoUndoneTasks() throws EmptyStackException, CommandException{
        ModelManager modelStub = new ModelManager();
        RedoCommand redoCommand = new RedoCommand();

        redoCommand.setData(modelStub, new CommandHistory());
        redoCommand.execute();

    }
    
   

    /**
     * A Model stub that always accepts the redo command.
     */
    public class ModelStubAcceptRedo extends ModelManager {
            
        public ModelStubAcceptRedo(){
            super(new TickTask(), new UserPrefs());
            TickTask tickTaskStub = new TickTask(); 
            this.futureProgramInstances.push(tickTaskStub);
        }
        
    }


}
//author