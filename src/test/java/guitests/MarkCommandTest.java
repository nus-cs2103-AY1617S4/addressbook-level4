package guitests;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.MarkCommand;

/**
 * Gui tests for the mark command
 */
public class MarkCommandTest extends TaskManagerGuiTest {

    //@@author A0154987J
    @Test
    public void markInvalidIndexFailure() {
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.pressEnter();
        commandBox.runCommand("mark " + (beforeSize + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void markValidIndexSuccess() {
        commandBox.pressEnter();
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("mark 1");
        assertListSize(beforeSize - 1);
        commandBox.runCommand("list completed");
        assertListSize(2);
    }

    //@@author A0154986L
    @Test
    public void markValidIndexAlreadyMarked() {
        commandBox.pressEnter();
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("mark 1");
        assertListSize(beforeSize - 1);
        commandBox.runCommand("list completed");
        assertListSize(2);
        commandBox.runCommand("mark 1");
        assertResultMessage(MarkCommand.MESSAGE_TASK_MARKED);
    }

}
