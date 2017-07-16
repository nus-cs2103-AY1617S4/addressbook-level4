package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_SECOND_TASK;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_THIRD_TASK;

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
 * Contains integration tests (interaction with the Model) for {@code UnmarkCommand}.
 */
public class UnmarkCommandTest {

    private Model model;

    //@@author A0154987J
    @Before
    public void setUp() {
        EventsCenter.getInstance().registerHandler(this);
        model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredTaskList().size());

        assertExecutionSuccess(INDEX_FIRST_TASK);
        assertUnmarkedSuccess(INDEX_FIRST_TASK);
        assertExecutionSuccess(INDEX_THIRD_TASK);
        assertUnmarkedSuccess(INDEX_SECOND_TASK);
        assertExecutionSuccess(lastPersonIndex);
        assertUnmarkedSuccess(lastPersonIndex);
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
        assertUnmarkedSuccess(INDEX_FIRST_TASK);
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
     * Updates {@code model}'s filtered list to show only the first person from the address book.
     */
    private void showFirstTaskOnly(Model model) {
        BasicTaskFeatures task = model.getFilteredTaskList().get(0);
        final String[] splitName = task.getName().toString().split("\\s+");
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));

        assertTrue(model.getFilteredTaskList().size() == 1);
    }

    private void assertUnmarkedSuccess(Index index) throws Exception {
        BasicTaskFeatures task = model.getFilteredTaskList().get(index.getZeroBased());
        assertEquals(task.getStatusString(), "Incomplete");
    }
    /**
     * Executes a {@code UnmarkCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) throws Exception {
        BasicTaskFeatures task = model.getFilteredTaskList().get(index.getZeroBased());
        UnmarkCommand unmarkCommand = prepareCommand(index);
        CommandResult commandResult = unmarkCommand.execute();
        assertEquals(String.format(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS, task),
                commandResult.feedbackToUser);
    }

    /**
     * Executes a {@code UnmarkCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {

        UnmarkCommand unmarkCommand = prepareCommand(index);

        try {
            unmarkCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException | DuplicateTaskException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code UnmarkCommand} with parameters {@code index}.
     */
    private UnmarkCommand prepareCommand(Index index) {
        UnmarkCommand unmarkCommand = new UnmarkCommand(index);
        unmarkCommand.setData(model, new CommandHistory());
        return unmarkCommand;
    }
}
