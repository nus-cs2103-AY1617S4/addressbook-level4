package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;
import seedu.whatsnext.testutil.TypicalTasks;

//@@author A0142675B
//@@author A0154986L
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
    }


    @Test
    public void execute_listIsNotFiltered_showsSameList() throws Exception {
        listCommand = new ListCommand(ListCommand.LIST_ALL);
        listCommand.setData(model, new CommandHistory());
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() throws Exception {
        showFirstTaskOnly(model);
        listCommand = new ListCommand(ListCommand.LIST_ALL);
        listCommand.setData(model, new CommandHistory());
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsIncompleteList() throws Exception {
        listCommand = new ListCommand(ListCommand.LIST_INCOMPLETE);
        listCommand.setData(model, new CommandHistory());
        boolean isCompleted = false;
        expectedModel.updateFilteredTaskListToShowByCompletion(isCompleted);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_INCOMPLETE, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsIncompleteList() throws Exception {
        showFirstTaskOnly(model);
        listCommand = new ListCommand(ListCommand.LIST_INCOMPLETE);
        listCommand.setData(model, new CommandHistory());
        boolean isCompleted = false;
        expectedModel.updateFilteredTaskListToShowByCompletion(isCompleted);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_INCOMPLETE, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsUpcomingList() throws Exception {
        listCommand = new ListCommand("");
        listCommand.setData(model, new CommandHistory());
        expectedModel.updateFilteredTaskListToShowUpcomingTasks();
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_UPCOMING, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsUpcomingList() throws Exception {
        showFirstTaskOnly(model);
        listCommand = new ListCommand("");
        listCommand.setData(model, new CommandHistory());
        expectedModel.updateFilteredTaskListToShowUpcomingTasks();
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_UPCOMING, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsCompletedList() throws Exception {
        listCommand = new ListCommand(ListCommand.LIST_COMPLETED);
        listCommand.setData(model, new CommandHistory());
        boolean isCompleted = true;
        expectedModel.updateFilteredTaskListToShowByCompletion(isCompleted);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_COMPLETED, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsExpiredList() throws Exception {
        showFirstTaskOnly(model);
        listCommand = new ListCommand(ListCommand.LIST_EXPIRED);
        listCommand.setData(model, new CommandHistory());
        expectedModel.updateFilteredTaskListToShowByExpiry();
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_EXPIRED, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_showsExpiredList() throws Exception {
        listCommand = new ListCommand(ListCommand.LIST_EXPIRED);
        listCommand.setData(model, new CommandHistory());
        expectedModel.updateFilteredTaskListToShowByExpiry();
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_EXPIRED, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsCompletedList() throws Exception {
        showFirstTaskOnly(model);
        listCommand = new ListCommand(ListCommand.LIST_COMPLETED);
        listCommand.setData(model, new CommandHistory());
        boolean isCompleted = true;
        expectedModel.updateFilteredTaskListToShowByCompletion(isCompleted);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS_COMPLETED, expectedModel);
    }

    /**
     * Updates the filtered list to show only the first task in the {@code model}'s task manager.
     */
    private void showFirstTaskOnly(Model model) {
        BasicTaskFeatures task = model.getTaskManager().getTaskList().get(0);
        final String[] splitName = task.getName().fullTaskName.split("\\s+");
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));

        assertTrue(model.getFilteredTaskList().size() == 1);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the TaskManager and the filtered task list in the {@code model} matches that of {@code expectedModel}
     * @throws IllegalValueException
     * @throws TagNotFoundException
     */
    public static void assertCommandSuccess(Command command, Model model, String expectedMessage, Model expectedModel)
            throws CommandException, TagNotFoundException, IllegalValueException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, model);
    }
}
