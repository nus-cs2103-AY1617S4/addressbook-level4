package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.task.ReadOnlyTask;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     * @throws WarningException 
     * @throws IllegalValueException 
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) throws CommandException, IllegalValueException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, actualModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the tick task and the filtered task list in the {@code actualModel} remain unchanged
     * @throws WarningException 
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage)  {

        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TickTask expectedTickTask = new TickTask(actualModel.getTickTask());
        List<ReadOnlyTask> expectedFilteredList = new ArrayList<>(actualModel.getFilteredActiveTaskList());
        List<ReadOnlyTask> expectedFilteredCompletedList = new ArrayList<>(actualModel.getFilteredCompletedTaskList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTickTask, actualModel.getTickTask());
            assertEquals(expectedFilteredList, actualModel.getFilteredActiveTaskList());
            assertEquals(expectedFilteredCompletedList, actualModel.getFilteredCompletedTaskList());
        } catch (IllegalValueException e) {
            e.printStackTrace();
        } 
        
    }
}
