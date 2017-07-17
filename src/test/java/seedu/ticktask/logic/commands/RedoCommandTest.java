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

public class RedoCommandTest {
    
    
    @Test (expected = EmptyStackException.class)
    public void redoCommandFailNoUndoneTasks() throws EmptyStackException, CommandException{
        ModelManager modelStub = new ModelManager();
        RedoCommand redoCommand = new RedoCommand();
        try{
                redoCommand.setData(modelStub, new CommandHistory());
                redoCommand.execute();
            } catch (EmptyStackException ese){
                assertEquals(ese.getMessage(), RedoCommand.MESSAGE_FAILURE);
            }
    }
    
    @Test
    public void redoCommandSuccess() throws CommandException, IllegalValueException {
        
        TickTask tickTaskStub1 = new TickTask();
        TickTask tickTaskStub2 = new TickTask();
        ModelStubAcceptRedo modelStubAcceptRedo = new ModelStubAcceptRedo();
        
        modelStubAcceptRedo.redoUndoneCommand();
        //assertEquals(RedoCommand.MESSAGE_SUCCESS, )
        
        assertTrue(modelStubAcceptRedo.getFutureProgramInstances().isEmpty());
        Stack<TickTask> filledStackForTestingPreviousInstances = new Stack<TickTask>();
        filledStackForTestingPreviousInstances.push(tickTaskStub2);
        filledStackForTestingPreviousInstances.push(tickTaskStub1);
        assertEquals(filledStackForTestingPreviousInstances, modelStubAcceptRedo.getPreviousProgramInstances());
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