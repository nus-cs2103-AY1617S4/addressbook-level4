package guitests;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;

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

}
