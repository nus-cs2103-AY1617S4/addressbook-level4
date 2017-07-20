package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.EditCommand;
import seedu.whatsnext.logic.commands.RedoCommand;

public class RedoCommandTest extends TaskManagerGuiTest {

    // @@author A0154987J
    @Test
    public void redoDeleteSuccess() {
        commandBox.pressEnter();
        int targetIndex = 1;
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("delete " + targetIndex);
        assertListSize(beforeSize - 1);
        commandBox.runCommand("undo");
        assertListSize(beforeSize);
        commandBox.runCommand("redo");
        assertListSize(beforeSize - 1);
    }

    @Test
    public void redoAddSuccess() {
        commandBox.pressEnter();
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("add Buy a country m/to rule");
        assertListSize(beforeSize + 1);
        commandBox.runCommand("undo");
        assertListSize(beforeSize);
        commandBox.runCommand("redo");
        assertListSize(beforeSize + 1);
    }

    @Test
    public void redoClearSuccess() {
        commandBox.pressEnter();
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                       + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("clear all");
        assertListSize(0);
        commandBox.runCommand("undo");
        assertListSize(beforeSize);
        commandBox.runCommand("redo");
        assertListSize(0);
    }

    @Test
    public void redoEditSuccess() {
        commandBox.pressEnter();
        String editString = EditCommand.COMMAND_WORD + " 1 n/Test task name";
        String originalName = eventListPanel.getEventTask(0).getName().toString();
        commandBox.runCommand(editString);
        assertEquals(eventListPanel.getEventTask(0).getName().toString(), "Test task name");
        commandBox.runCommand("undo");
        assertEquals(eventListPanel.getEventTask(0).getName().toString(), originalName);
        commandBox.runCommand("redo");
        assertEquals(eventListPanel.getEventTask(0).getName().toString(), "Test task name");
    }

    @Test
    public void redoInvalidCommand() {
        commandBox.pressEnter();
        commandBox.runCommand("REDOwrong");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void redoNothingToUndo() {
        commandBox.pressEnter();
        commandBox.runCommand("redo");
        assertResultMessage(RedoCommand.MESSAGE_FAILURE);
    }

}
