package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.testutil.TaskUtil;

public class ClearCommandTest extends TickTaskGuiTest {

//    @Test
//    public void clear() {
//
//        //verify a non-empty list can be cleared
//        assertClearCommandSuccess();
//
//        //verify other commands can work after a clear command
//        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
//        assertListSize(0);
//
//        //verify clear command works when the list is empty
//        assertClearCommandSuccess();
//    }

    private void assertClearCommandSuccess() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertListSize(0);
        assertResultMessage("The TickTask program has been cleared!");
    }
}
