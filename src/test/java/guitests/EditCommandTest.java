package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.EditCommand;

public class EditCommandTest extends TaskManagerGuiTest {

    //@@author A0154987J
    @Test
    public void editEventTaskMultipleFieldsSuccess() throws Exception {
        commandBox.pressEnter();
        String newName = "TestTask";
        String newDescription = "Task edited successfully";
        String newTag = "MEDIUM";
        String detailsToEdit = "edit 1 n/" + newName + " m/" + newDescription
                                + " +t/" + newTag;

        commandBox.runCommand(detailsToEdit);

        assertEquals(eventListPanel.getEventTask(0).getName().toString(), newName);
        assertEquals(eventListPanel.getEventTask(0).getDescription().toString(), newDescription);
        assertTrue(eventListPanel.getEventTask(0).getAllTags().contains(newTag));
    }

    @Test
    public void editDeadlineTaskMultipleFieldsSuccess() throws Exception {
        commandBox.pressEnter();
        String newName = "TestTask";
        String newDescription = "Task edited successfully";
        String newEndDateTime = "2017/07/25 05:00";
        int eventListSize = eventListPanel.getNumberOfTask();
        String detailsToEdit = "edit " + (eventListSize + 1) + " n/" + newName + " m/" + newDescription
                                + " e/" + newEndDateTime;

        commandBox.runCommand(detailsToEdit);

        assertEquals(deadlineListPanel.getDeadlineTask(0).getName().toString(), newName);
        assertEquals(deadlineListPanel.getDeadlineTask(0).getDescription().toString(), newDescription);
        assertEquals(deadlineListPanel.getDeadlineTask(0).getEndDateTime().toString(), "2017/25/07 05:00");
    }

    @Test
    public void editFloatingTaskMultipleFieldsSuccess() throws Exception {
        commandBox.pressEnter();
        String newName = "TestTask";
        String newDescription = "Task edited successfully";
        String newTag = "LOW";
        int index = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask();
        String detailsToEdit = "edit " + (index + 1) + " n/" + newName + " m/" + newDescription
                                + " +t/" + newTag;

        commandBox.runCommand(detailsToEdit);

        assertEquals(floatingListPanel.getFloatingTask(0).getName().toString(), newName);
        assertEquals(floatingListPanel.getFloatingTask(0).getDescription().toString(), newDescription);
        assertTrue(floatingListPanel.getFloatingTask(0).getAllTags().contains(newTag));
    }

    @Test
    public void editInvalidTaskFailure() throws Exception {
        commandBox.pressEnter();
        String newName = "TestTask";
        String newDescription = "Task edited successfully";
        String newTag = "EDITEDTASK";
        int index = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                    + floatingListPanel.getNumberOfTask();
        String detailsToEdit = "edit " + (index + 1) + " n/" + newName + " m/" + newDescription
                                + " +t/" + newTag;

        commandBox.runCommand(detailsToEdit);

        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void editInvalidDuplicateTaskFailure() throws Exception {
        commandBox.pressEnter();
        String newName = eventListPanel.getEventTask(0).getName().toString();
        String detailsToEdit = "edit 2 n/" + newName;

        commandBox.runCommand(detailsToEdit);

        assertResultMessage(EditCommand.MESSAGE_DUPLICATE_TASK);
    }
}
