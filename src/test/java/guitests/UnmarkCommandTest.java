package guitests;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.UnmarkCommand;

public class UnmarkCommandTest extends TaskManagerGuiTest {

    //@@author A0154987J
    @Test
    public void markInvalidIndexFailure() {
        commandBox.pressEnter();
        commandBox.runCommand("list completed");
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("unmark " + (beforeSize + 1));
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void markValidIndexSuccess() {
        commandBox.pressEnter();
        int incompleteBeforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("list completed");
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("unmark 1");
        assertListSize(beforeSize - 1);
        commandBox.runCommand("list incomplete");
        assertListSize(incompleteBeforeSize + 1);
    }

    //@@author A0154986L
    @Test
    public void markValidIndexAlreadyUnmarked() {
        commandBox.pressEnter();
        int incompleteBeforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("list completed");
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("unmark 1");
        assertListSize(beforeSize - 1);
        commandBox.runCommand("list incomplete");
        assertListSize(incompleteBeforeSize + 1);
        commandBox.runCommand("unmark 1");
        assertResultMessage(UnmarkCommand.MESSAGE_TASK_UNMARKED);
    }

}
