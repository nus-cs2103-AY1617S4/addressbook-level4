package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;
import seedu.whatsnext.testutil.TaskBuilder;
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddCommand.
 */
public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    //@@author A0156106M
    @Test
    public void execute_floatingTaskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        BasicTask validTask = new TaskBuilder(BasicTask.TASK_TYPE_FLOATING).build();
        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    //@@author A0156106M
    @Test
    public void execute_deadlineTaskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        BasicTask validTask = new TaskBuilder(BasicTask.TASK_TYPE_DEADLINE).build();
        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }


    //@@author A0156106M
    @Test
    public void execute_eventTaskAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTaskAdded modelStub = new ModelStubAcceptingTaskAdded();
        BasicTask validTask = new TaskBuilder(BasicTask.TASK_TYPE_EVENT).build();
        CommandResult commandResult = getAddCommandForTask(validTask, modelStub).execute();
        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validTask), modelStub.tasksAdded);
    }

    //@@author
    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateTaskException();
        BasicTask validTask = new TaskBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_TASK);

        getAddCommandForTask(validTask, modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given task.
     */
    private AddCommand getAddCommandForTask(BasicTask task, Model model) throws IllegalValueException {
        AddCommand command = new AddCommand(task);
        command.setData(model, new CommandHistory());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addTask(BasicTask basicTask) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyTaskManager newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyTaskManager getTaskManager() {
            return new TaskManager();
        }

        @Override
        public void deleteTask(BasicTaskFeatures target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(BasicTaskFeatures target, BasicTaskFeatures editedTask)
                throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public UnmodifiableObservableList<BasicTaskFeatures> getFilteredTaskList() {
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
        public void undoTaskManager() {
            fail("This method should not be called.");
        }

        @Override
        public void redoTaskManager() {
            fail("This method should not be called.");
        }

        @Override
        public void saveTaskManager() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskListForInitialView() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskListToShowByCompletion(boolean isComplete) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskListForReminder() {
            fail("This method should not be called.");
        }

        @Override
        public String getReminderSetting() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setReminderSetting(String newReminderSetting) {
            fail("This method should not be called.");
        }

        @Override
        public String getTaskManagerFilePath() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void setTaskManagerFilePath(String newFilePath) {
            fail("This method should not be called.");
        }

        @Override
        public void resetPrevTaskManager() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskListToShowUpcomingTasks() {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredTaskListToShowByExpiry() {
            fail("This method should not be called.");
        }

        @Override
        public void showReminderAlert() {
            fail("This method should not be called.");

        }

    }

    //@@author A0156106M
    /**
     * A Model stub that always throw a DuplicateTaskException when trying to add a Task.
     */
    private class ModelStubThrowingDuplicateTaskException extends ModelStub {
        @Override
        public void addTask(BasicTask task) throws DuplicateTaskException {
            throw new DuplicateTaskException();
        }

        @Override
        public void resetPrevTaskManager() {
            return;
        }

        @Override
        public UnmodifiableObservableList<BasicTaskFeatures> getFilteredTaskList() {
            final ArrayList<BasicTask> tasksAdded = new ArrayList<>();
            ObservableList<BasicTask> observableList = FXCollections.observableArrayList(tasksAdded);
            return new UnmodifiableObservableList<BasicTaskFeatures>(observableList);

        }

    }

    //@@author A0156106M
    /**
     * A Model stub that always accept the Task being added.
     */
    private class ModelStubAcceptingTaskAdded extends ModelStub {
        final ArrayList<BasicTask> tasksAdded = new ArrayList<>();

        @Override
        public void addTask(BasicTask task) throws DuplicateTaskException {
            tasksAdded.add(new BasicTask(task));
        }

        @Override
        public UnmodifiableObservableList<BasicTaskFeatures> getFilteredTaskList() {
            ObservableList<BasicTask> observableList = FXCollections.observableArrayList(tasksAdded);
            return new UnmodifiableObservableList<BasicTaskFeatures>(observableList);
        }

        @Override
        public ReadOnlyTaskManager getTaskManager() {
            return new TaskManager();
        }


    }

}
