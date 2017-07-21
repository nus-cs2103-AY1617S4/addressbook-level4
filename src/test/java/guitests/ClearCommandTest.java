package guitests;

import org.junit.Test;

import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.commands.DeleteCommand;

public class ClearCommandTest extends TaskManagerGuiTest {

    //@@author A0154987J
    @Test
    public void clearAll() {

        commandBox.pressEnter();
        commandBox.runCommand("list all");
        assertClearCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand("add Buy a country m/to rule");
        assertListSize(1);
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    @Test
    public void clearIncomplete() {

        commandBox.pressEnter();
        assertClearIncompleteCommandSuccess();

        //verify other commands can work after a clear command
        commandBox.runCommand("list completed");
        assertListSize(1);
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertListSize(0);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    @Test
    public void clearCompleted() {

        commandBox.pressEnter();
        commandBox.runCommand("list completed");
        assertClearCompletedCommandSuccess();

        //verify other commands can work after a clear command
        assertListSize(0);
        commandBox.runCommand("list all");
        assertListSize(6);

        //verify clear command works when the list is empty
        assertClearCommandSuccess();
    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD + " all");
        assertListSize(0);
        assertResultMessage("Task List has been cleared!");
    }

    private void assertClearIncompleteCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD + " incomplete");
        assertListSize(0);
        assertResultMessage("Incomplete tasks have been cleared!");
    }

    private void assertClearCompletedCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD + " completed");
        assertListSize(0);
        assertResultMessage("Completed tasks have been cleared!");
    }
}
