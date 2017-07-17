package seedu.ticktask.logic.commands;

import static org.junit.Assert.*;

import java.util.EmptyStackException;
import java.util.Stack;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.testutil.TickTaskBuilder;

//@@author A0139819N

public class UndoCommandTest {
    
    @Test
    public void undoCommandSuccess() throws CommandException, IllegalValueException {
        
        ModelStubAcceptUndo modelStubAcceptUndo = new ModelStubAcceptUndo();
        
        modelStubAcceptUndo.undoPreviousCommand();
        
        Stack<TickTask> emptyStackForTestingPrev = new Stack<TickTask>();
        assertEquals(emptyStackForTestingPrev, modelStubAcceptUndo.getPreviousProgramInstances());
        Stack<TickTask> filledStackForTestingFuture = new Stack<TickTask>();
        filledStackForTestingFuture.push(new TickTaskBuilder().build());
        assertEquals(filledStackForTestingFuture, modelStubAcceptUndo.getFutureProgramInstances());
        assertEquals(new TickTaskBuilder().build(), modelStubAcceptUndo.getCurrentProgramInstance());
        
    }
    
    @Test (expected = CommandException.class)
    public void undoCommandFailNoPreviousTasks() throws EmptyStackException, CommandException{
        
            ModelManager modelStub = new ModelManager();
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.setData(modelStub, new CommandHistory());
            undoCommand.execute();
            
    }
    

    /**
     * A Model stub that always accepts a undo command.
     */
    public class ModelStubAcceptUndo extends ModelManager {

        public ModelStubAcceptUndo(){
            super();
            TickTask tickTaskInstanceStub = new TickTask();
            this.previousProgramInstances.push(tickTaskInstanceStub);
        }

    }

}
//author