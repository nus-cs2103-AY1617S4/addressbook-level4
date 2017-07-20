package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.EditCommand;
import seedu.whatsnext.logic.commands.UndoCommand;

public class UndoCommandTest extends TaskManagerGuiTest {

    // @@author A0154987J
    @Test
    public void undoDeleteSuccess() {
        commandBox.pressEnter();
        int targetIndex = 1;
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("delete " + targetIndex);
        assertListSize(beforeSize - 1);
        commandBox.runCommand("undo");
        assertListSize(beforeSize);
    }

    @Test
    public void undoAddSuccess() {
        commandBox.pressEnter();
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                        + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("add Buy a country m/to rule");
        assertListSize(beforeSize + 1);
        commandBox.runCommand("undo");
        assertListSize(beforeSize);
    }

    @Test
    public void undoClearSuccess() {
        commandBox.pressEnter();
        int beforeSize = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
                       + floatingListPanel.getNumberOfTask();
        commandBox.runCommand("clear all");
        assertListSize(0);
        commandBox.runCommand("undo");
        assertListSize(beforeSize);
    }

    @Test
    public void undoEditSuccess() {
        commandBox.pressEnter();
        String editString = EditCommand.COMMAND_WORD + " 1 n/Test task name";
        String originalName = eventListPanel.getEventTask(0).getName().toString();
        commandBox.runCommand(editString);
        assertEquals(eventListPanel.getEventTask(0).getName().toString(), "Test task name");
        commandBox.runCommand("undo");
        assertEquals(eventListPanel.getEventTask(0).getName().toString(), originalName);
    }

    @Test
    public void undoInvalidCommand() {
        commandBox.pressEnter();
        commandBox.runCommand("list all");
        commandBox.runCommand("UNDOwrong");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void undoNothingToUndo() {
        commandBox.pressEnter();
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_FAILURE);
    }

}
