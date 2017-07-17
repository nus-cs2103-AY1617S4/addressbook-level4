package seedu.ticktask.logic.commands;

import static org.junit.Assert.*;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import junit.framework.Assert;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;
import seedu.ticktask.testutil.TickTaskBuilder;;;

public class RedoCommandTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
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
    
    @Test(expected = CommandException.class)
    public void undoCommandFailNoPreviousTasks() throws EmptyStackException, CommandException{
        
            ModelManager modelStub = new ModelManager();
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.setData(modelStub, new CommandHistory());
            undoCommand.execute();
            
    }
    

    /**
     * A Model stub that always accepts the undo command.
     */
    public class ModelStubAcceptUndo extends ModelManager {
            
        public ModelStubAcceptUndo(){
            super();
            TickTask tickTaskInstanceStub = new TickTask();
            this.previousProgramInstances.push(tickTaskInstanceStub);
        }

    }
    
    
    /**
     * A Model stub that always throw a Stack when trying to add a task.
     
    private class ModelStubThrowingEmptyStackException extends ModelStub {
        @Override
        public void undoPreviousCommand() throws EmptyStackException {
            throw new EmptyStackException();
        }
    }
    */


}