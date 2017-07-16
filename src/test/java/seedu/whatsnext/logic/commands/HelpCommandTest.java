/*package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.whatsnext.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.events.ui.ShowHelpRequestEvent;

//@@author A0156106M
public class HelpCommandTest {
    private boolean isEventCaught = false;

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent shre) {
        isEventCaught = true;
    }

    @Before
    public void setUp() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute_help_success() {
        CommandResult result = new HelpCommand().execute();
        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
        assertTrue(isEventCaught);
    }

    @Test
    public void execute_helpAdd_success() {
        CommandResult result = new HelpCommand(AddCommand.COMMAND_WORD).execute();
        assertEquals(AddCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpEdit_success() {
        CommandResult result = new HelpCommand(EditCommand.COMMAND_WORD).execute();
        assertEquals(EditCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpSelect_success() {
        CommandResult result = new HelpCommand(SelectCommand.COMMAND_WORD).execute();
        assertEquals(SelectCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpDelete_success() {
        CommandResult result = new HelpCommand(DeleteCommand.COMMAND_WORD).execute();
        assertEquals(DeleteCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpChangePath_success() {
        CommandResult result = new HelpCommand(ChangePathCommand.COMMAND_WORD).execute();
        assertEquals(ChangePathCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpFilePath_success() {
        CommandResult result = new HelpCommand(FilePathCommand.COMMAND_WORD).execute();
        assertEquals(FilePathCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpMark_success() {
        CommandResult result = new HelpCommand(MarkCommand.COMMAND_WORD).execute();
        assertEquals(MarkCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpUnmark_success() {
        CommandResult result = new HelpCommand(UnmarkCommand.COMMAND_WORD).execute();
        assertEquals(UnmarkCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpClear_success() {
        CommandResult result = new HelpCommand(ClearCommand.COMMAND_WORD).execute();
        assertEquals(ClearCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpFind_success() {
        CommandResult result = new HelpCommand(FindCommand.COMMAND_WORD).execute();
        assertEquals(FindCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpList_success() {
        CommandResult result = new HelpCommand(ListCommand.COMMAND_WORD).execute();
        assertEquals(ListCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpUndo_success() {
        CommandResult result = new HelpCommand(UndoCommand.COMMAND_WORD).execute();
        assertEquals(UndoCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpRedo_success() {
        CommandResult result = new HelpCommand(RedoCommand.COMMAND_WORD).execute();
        assertEquals(RedoCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpRemind_success() {
        CommandResult result = new HelpCommand(RemindCommand.COMMAND_WORD).execute();
        assertEquals(RemindCommand.MESSAGE_USAGE, result.feedbackToUser);
    }

    @Test
    public void execute_helpHistory_success() {
        CommandResult result = new HelpCommand(HistoryCommand.COMMAND_WORD).execute();
        assertEquals(HistoryCommand.MESSAGE_USAGE, result.feedbackToUser);
    }
}
*/
