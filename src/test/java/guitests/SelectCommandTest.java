package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.whatsnext.model.task.BasicTaskFeatures;

public class SelectCommandTest extends TaskManagerGuiTest {

    //@@author A0154987J
    @Test
    public void selectTask_nonEmptyList() {
        commandBox.pressEnter();
        commandBox.runCommand("list all");

        assertSelectionInvalid(51); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); // first task in the list
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.pressEnter();
        commandBox.runCommand("clear all");
        assertListSize(0);
        assertSelectionInvalid(1); // invalid index
        commandBox.runCommand("undo");
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        if (eventListPanel.getNumberOfTask() > index) {
            assertEquals(eventListPanel.getSelectedTasks().size(), 1);
            assertNoDeadlineTaskSelected();
            assertNoFloatingTaskSelected();
            BasicTaskFeatures selectedTask = eventListPanel.getSelectedTasks().get(0).getKey();
            assertEquals(eventListPanel.getEventTask(index - 1), selectedTask);
        } else {
            if ((eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()) > index) {
                assertEquals(deadlineListPanel.getSelectedTasks().size(), 1);
                assertNoEventTaskSelected();
                assertNoFloatingTaskSelected();
                BasicTaskFeatures selectedTask = deadlineListPanel.getSelectedTasks().get(0).getKey();
                assertEquals(deadlineListPanel.getDeadlineTask(index - eventListPanel.getNumberOfTask()
                        - 1), selectedTask);

            } else {
                assertEquals(floatingListPanel.getSelectedTasks().size(), 1);
                assertNoDeadlineTaskSelected();
                assertNoEventTaskSelected();
                BasicTaskFeatures selectedTask = floatingListPanel.getSelectedTasks().get(0).getKey();
                assertEquals(floatingListPanel.getFloatingTask(
                        index - eventListPanel.getNumberOfTask() - deadlineListPanel.getNumberOfTask()
                        - 1), selectedTask);
            }
        }
    }

    private void assertNoTaskSelected() {
        assertNoEventTaskSelected();
        assertNoDeadlineTaskSelected();
        assertNoFloatingTaskSelected();
    }

    private void assertNoEventTaskSelected() {
        assertEquals(eventListPanel.getSelectedTasks().size(), 0);
    }

    private void assertNoDeadlineTaskSelected() {
        assertEquals(deadlineListPanel.getSelectedTasks().size(), 0);
    }

    private void assertNoFloatingTaskSelected() {
        assertEquals(floatingListPanel.getSelectedTasks().size(), 0);
    }

}
