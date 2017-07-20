package guitests;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.ListCommand;

public class ListCommandTest extends TaskManagerGuiTest {

    @Test
    public void listInvalidCommandFailure() {
        commandBox.pressEnter();
        commandBox.runCommand("LIst");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("list nonsense");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void listAllCommand() {
        commandBox.pressEnter();
        commandBox.runCommand("list all");
        assertListSize(td.getTypicalTasks().length);
    }

    @Test
    public void listCompletedCommand() {
        commandBox.pressEnter();
        commandBox.runCommand("list completed");
        assertListSize(1);
    }

    @Test
    public void listIncompleteCommand() {
        commandBox.pressEnter();
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                       + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("list complete");
        assertListSize(beforeSize);
    }
}
