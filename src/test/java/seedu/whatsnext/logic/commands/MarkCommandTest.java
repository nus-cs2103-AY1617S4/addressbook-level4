package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_SECOND_TASK;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) for {@code MarkCommand}.
 */
public class MarkCommandTest {

    private Model model;
    //@@author A0154987J
    @Before
    public void setUp() {
        EventsCenter.getInstance().registerHandler(this);
        model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {

        assertExecutionSuccess(INDEX_FIRST_TASK);
        assertMarkedSuccess(INDEX_FIRST_TASK);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstTaskOnly(model);

        assertExecutionSuccess(INDEX_FIRST_TASK);
        assertMarkedSuccess(INDEX_FIRST_TASK);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstTaskOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of task manager list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getTaskManager().getTaskList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Updates {@code model}'s filtered list to show only the first tasks from the TaskManager.
     */
    private void showFirstTaskOnly(Model model) {
        BasicTaskFeatures task = model.getFilteredTaskList().get(0);
        final String[] splitName = task.getName().toString().split("\\s+");
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));

        assertTrue(model.getFilteredTaskList().size() == 1);
    }

    private void assertMarkedSuccess(Index index) throws Exception {
        BasicTaskFeatures task = model.getFilteredTaskList().get(index.getZeroBased());
        assertEquals(task.getStatusString(), "Completed");
    }
    /**
     * Executes a {@code MarkCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) throws Exception {
        BasicTaskFeatures task = model.getFilteredTaskList().get(index.getZeroBased());
        MarkCommand markCommand = prepareCommand(index);
        CommandResult commandResult = markCommand.execute();
        assertEquals(String.format(MarkCommand.MESSAGE_MARK_TASK_SUCCESS, task),
                commandResult.feedbackToUser);
    }

    /**
     * Executes a {@code MarkCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {

        MarkCommand markCommand = prepareCommand(index);

        try {
            markCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException | DuplicateTaskException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code MarkCommand} with parameters {@code index}.
     */
    private MarkCommand prepareCommand(Index index) {
        MarkCommand markCommand = new MarkCommand(index);
        markCommand.setData(model, new CommandHistory());
        return markCommand;
    }
}
