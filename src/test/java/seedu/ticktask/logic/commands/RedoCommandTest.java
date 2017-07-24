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
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.testutil.TickTaskBuilder;;;
//@@author A0139819N
public class RedoCommandTest {
    
    @Test
    public void redoCommand_success() throws CommandException, IllegalValueException {
        
        ModelStubAcceptRedo modelStubAcceptRedo = new ModelStubAcceptRedo();
        RedoCommand redoCommandObj = getRedoCommandForModel(modelStubAcceptRedo);
        CommandResult redoResult = redoCommandObj.execute();
        
        assertTrue(modelStubAcceptRedo.getFutureProgramInstances().isEmpty());
        assertEquals(RedoCommand.MESSAGE_SUCCESS, redoResult.feedbackToUser);
        Stack<TickTask> filledStackForTestingPreviousInstances = new Stack<TickTask>();
        filledStackForTestingPreviousInstances.push(new TickTask());
        assertEquals(filledStackForTestingPreviousInstances, modelStubAcceptRedo.getPreviousProgramInstances());
    }
    
    @Test
    public void redoCommand_NoUndoneTasks_throwsCommandException() throws EmptyStackException, CommandException{
        ModelManager modelStub = new ModelManager();
        RedoCommand redoCommand = new RedoCommand();

        redoCommand.setData(modelStub, new CommandHistory());
        try{
            redoCommand.execute();
        } catch( CommandException ce){
            assertEquals(ce.getMessage(), RedoCommand.MESSAGE_FAILURE);
        }

    }
    
    @Test (expected = CommandException.class)
    public void redoCommandFailCmdException() throws EmptyStackException, CommandException{
        ModelManager modelStub = new ModelManager();
        RedoCommand redoCommand = new RedoCommand();

        redoCommand.setData(modelStub, new CommandHistory());

        redoCommand.execute();       

    }

    /**
     * Generates a new RedoCommand object based on the Model Stub
     */
    private RedoCommand getRedoCommandForModel(Model model) {
        RedoCommand command = new RedoCommand();
        command.setData(model, new CommandHistory());
        return command;
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
//@@author