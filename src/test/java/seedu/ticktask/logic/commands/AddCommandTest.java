package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;
import seedu.ticktask.testutil.TaskBuilder;

public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }


    @Test
    public void execute_taskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }
    //@@author A0139964M
    @Test
    public void execute_PastTaskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().withDate("01/01/2001").build();

        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_PAST_TASK, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_PastTask_CurrTimeNullDate_AcceptedByModel_addSuccessful() throws Exception {
        LocalTime localTime = LocalTime.now();
        LocalDate currDate = LocalDate.now();
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().withDate("").withTime(localTime.toString()).build();

        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_PAST_TASK, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    @Test
    public void execute_PastTask_CurrDateCurrTimeAcceptedByModel_addSuccessful() throws Exception {
        LocalTime localTime = LocalTime.now();
        LocalDate currDate = LocalDate.now();
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        Task validTask = new TaskBuilder().withDate(currDate.toString()).withTime(localTime.toString()).build();

        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_PAST_TASK, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
        //@@author
    }
    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_TASK);

        getAddCommandForTask(validTask, modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given task.
     */
    private AddCommand getAddCommandForTask(Task task, Model model) throws IllegalValueException {
        AddCommand command = new AddCommand(task);
        command.setData(model, new CommandHistory());
        return command;
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
        public void resetActiveData(ReadOnlyTickTask newData) {
            fail("This method should not be called.");
        }
        
        @Override
        public void resetCompleteData(ReadOnlyTickTask newData) {
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
        public void deleteIndexActiveTask(ReadOnlyTask target) throws TaskNotFoundException {
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
        public void updateMatchedTaskList(Set<String> keywords) {

        }

        @Override
		public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList() {
            fail("This method should not be called.");
            return null;
		}

		@Override
		public void deleteIndexCompleteTask(ReadOnlyTask target) throws TaskNotFoundException {
			fail("This method should not be called.");
			
		}

		@Override
		public void undoPreviousCommand() throws EmptyStackException {
			fail("This method should not be called.");
			
		}

		@Override
		public void redoUndoneCommand() throws EmptyStackException {
			fail("This method should not be called.");
			
		}

        @Override
        public String eventClash(ReadOnlyTask t) {
            fail("This method should not be called.");
            return null;
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

        public boolean isChornological(ReadOnlyTask t) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void restoreTask(ReadOnlyTask target) throws TaskNotFoundException, DuplicateTaskException {
            fail("This method should not be called.");   
        }
    }

    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }
    }

    /**
     * A Model stub that always accept the task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<Task> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(ReadOnlyTask task) throws DuplicateTaskException {
            tasksAdded.add(new Task(task));
        }
    }

}
