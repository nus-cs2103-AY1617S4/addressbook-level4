package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.task.exceptions.EventClashException;
import seedu.ticktask.model.task.exceptions.PastTaskException;
import seedu.ticktask.testutil.TypicalTasks;

public class ClearCommandTest {

    @Test
    public void execute_emptyTickTask_success() {
        Model model = new ModelManager();
        assertCommandSuccess(model);
    }

    @Test
    public void execute_nonEmptyTickTask_success() throws PastTaskException, EventClashException {
        Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
        assertCommandSuccess(model);
    }

    /**
     * Executes {@code ClearCommand} on the given {@code model}, confirms that <br>
     * - the result message matches {@code ClearCommand.MESSAGE_SUCCESS} <br>
     * - the TickTask and filtered task list in {@code model} is empty <br>
     */
    private void assertCommandSuccess(Model model) {
        ClearCommand command = new ClearCommand();
        command.setData(model, new CommandHistory());
        CommandResult result = command.execute();

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertEquals(new ModelManager(), model);
    }
}
