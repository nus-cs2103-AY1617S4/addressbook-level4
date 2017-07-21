package guitests;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.commands.DeleteCommand;
import seedu.whatsnext.logic.commands.FindCommand;
import seedu.whatsnext.model.task.BasicTask;

public class FindCommandTest extends TaskManagerGuiTest {

    //@@author A0154987J
    @Test
    public void find_nonEmptyList() {
        commandBox.pressEnter();
        assertFindResult(FindCommand.COMMAND_WORD + " CS1020E"); // no results
        assertFindResult(FindCommand.COMMAND_WORD + " High",
                td.completeCS2103Assignment, td.cs2010ProblemSet, td.fypSelection, td.tester); // multiple results

        //find after deleting one result
        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFindResult(FindCommand.COMMAND_WORD + " High", td.cs2010ProblemSet, td.fypSelection, td.tester);
    }

    @Test
    public void find_emptyList() {
        commandBox.pressEnter();
        commandBox.runCommand(ClearCommand.COMMAND_WORD + " all");
        assertFindResult(FindCommand.COMMAND_WORD + " High"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.pressEnter();
        commandBox.runCommand(FindCommand.COMMAND_WORD + "george");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, BasicTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
    }
}
