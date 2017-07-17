package seedu.ticktask.logic.commands;

import static org.junit.Assert.*;

import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;
import seedu.ticktask.testutil.TickTaskBuilder;;;

public class UndoCommandTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Test
    public void undoCommandSuccess() throws CommandException, IllegalValueException {
        ModelStubAcceptUndo modelStub = new ModelStubAcceptUndo();
        modelStub.undoPreviousCommand();
        try{
            CommandResult commandResult = getUndoCommand(modelStub).execute();
            assertEquals(String.format(UndoCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
        } catch (CommandException ce){

        } catch (IllegalValueException ive){

        }
        
        
        Stack<TickTask> emptyStackForTestingPrev = new Stack<TickTask>();
        Stack<TickTask> filledStackForTestingFuture = new Stack<TickTask>();
        filledStackForTestingFuture.push(new TickTaskBuilder().build());
        filledStackForTestingFuture.push(new TickTaskBuilder().build());
        
        assertEquals(emptyStackForTestingPrev, modelStub.previousProgramInstances);
       
        
    }
    

    /**
     * Generates a new UndoCommand object 
     */
    private UndoCommand getUndoCommand(Model model) throws IllegalValueException {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory());
        return undoCommand;
    }
    
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyTickTask newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyTickTask getTickTask() {
            fail("This method should not be called.");
            return null;
        }

        public void completeTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteFindTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
                throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskList(Set<String> keywords) {
            fail("This method should not be called.");
        }

        @Override
        public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteIndexActiveTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }
        
        @Override
        public void deleteIndexCompleteTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void undoPreviousCommand() throws EmptyStackException {

        }

        @Override
        public void redoUndoneCommand() throws EmptyStackException {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredListToShowEvent() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredListToShowDeadline() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredListToShowFloating() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredListToShowToday() {
            fail("This method should not be called.");
        }

        @Override
        public void saveTickTask() {
            fail("This method should not be called.");
        }
    }
    
    /**
     * A TickTask program instance stub that is used for insertion into the stack
     */
    public class TickTaskInstanceStub extends TickTask{ 
        
    }

    /**
     * A Model stub that always accepts the undo command.
     */
    public class ModelStubAcceptUndo extends ModelStub {
        
        TickTask tickTaskInstanceStub = new TickTask();
        TickTask currentProgramInstance = new TickTask();
        Stack<TickTask> previousProgramInstances = new Stack<TickTask>();
        Stack<TickTask> futureProgramInstances = new Stack<TickTask>();
        
        public ModelStubAcceptUndo(){
            previousProgramInstances.push(tickTaskInstanceStub);
        }
        @Override
        public void undoPreviousCommand() throws EmptyStackException {
            TickTask currentProgramInstance = new TickTask(tickTaskInstanceStub);
            currentProgramInstance.resetData(previousProgramInstances.pop());
            futureProgramInstances.push(currentProgramInstance);
        }
    }
    
    /**
     * A Model stub that always throw a Stack when trying to add a task.
     */
    private class ModelStubThrowingEmptyStackException extends ModelStub {
        @Override
        public void undoPreviousCommand() throws EmptyStackException {
            throw new EmptyStackException();
        }
    }
    
    




}