package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.commands.CommandResult;
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
    public void constructor_nullPerson_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Task validTask = new TaskBuilder().build();

        CommandResult commandResult = getAddCommandForPerson(validTask, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        Task validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_TASK);

        getAddCommandForPerson(validTask, modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private AddCommand getAddCommandForPerson(Task task, Model model) throws IllegalValueException {
        AddCommand command = new AddCommand(task);
        command.setData(model, new CommandHistory());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addTask(ReadOnlyTask person) throws DuplicateTaskException {
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
        public void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(ReadOnlyTask target, ReadOnlyTask editedPerson)
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
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addTask(ReadOnlyTask person) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Task> personsAdded = new ArrayList<>();

        @Override
        public void addTask(ReadOnlyTask person) throws DuplicateTaskException {
            personsAdded.add(new Task(person));
        }
    }

}
