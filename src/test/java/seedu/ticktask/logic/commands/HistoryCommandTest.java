package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.HistoryCommand;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;

public class HistoryCommandTest {
    private HistoryCommand historyCommand;
    private CommandHistory history;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        history = new CommandHistory();
        historyCommand = new HistoryCommand();
        historyCommand.setData(model, history);
    }

    @Test
    public void execute() {
        assertCommandResult(historyCommand, HistoryCommand.MESSAGE_NO_HISTORY);

        String command1 = "clear";
        history.add(command1);
        assertCommandResult(historyCommand, String.format(HistoryCommand.MESSAGE_SUCCESS, command1));

        String command2 = "randomCommand";
        String command3 = "delete 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command3, command2, command1));

        assertCommandResult(historyCommand, expectedMessage);
    }

    /**
     * Asserts that the result message from the execution of {@code historyCommand} equals to {@code expectedMessage}
     */
    private void assertCommandResult(HistoryCommand historyCommand, String expectedMessage) {
        assertEquals(expectedMessage, historyCommand.execute().feedbackToUser);
    }
}
