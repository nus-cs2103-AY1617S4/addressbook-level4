package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.ResetCommand;
import seedu.whatsnext.model.task.BasicTaskFeatures;

/**
 * A gui test for ResetCommand
 */
public class ResetCommandTest extends TaskManagerGuiTest {

    @Test
    public void assertEventResetSuccess() {
        commandBox.pressEnter();
        BasicTaskFeatures task = eventListPanel.getEventTask(0);
        commandBox.runCommand("reset 1");
        assertTrue(task.getTaskType().equals("floating"));
    }

    @Test
    public void assertDeadlineResetSuccess() {
        commandBox.pressEnter();
        BasicTaskFeatures task = deadlineListPanel.getDeadlineTask(0);
        int index = eventListPanel.getNumberOfTask() + 1;
        commandBox.runCommand("reset " + index);
        assertTrue(task.getTaskType().equals("floating"));
    }

    @Test
    public void assertFloatingResetFailure() {
        commandBox.pressEnter();
        int index = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask() + 1;
        commandBox.runCommand("reset " + index);
        assertResultMessage(ResetCommand.MESSAGE_RESET_FLOATING_TASK);
    }

    @Test
    public void assertInvalidIndexResetFailure() {
        commandBox.pressEnter();
        int index = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                    + floatingListPanel.getNumberOfTask() + 1;
        commandBox.runCommand("reset " + index);
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

}
