package guitests;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.whatsnext.logic.commands.RemindCommand;

//@@author A0154986L
public class RemindCommandTest extends TaskManagerGuiTest {

    @Test
    public void setRemindSuccess() {
        commandBox.pressEnter();
        String remindSetting = "4 day";
        commandBox.runCommand(RemindCommand.COMMAND_WORD + " " + remindSetting);
        assertResultMessage(RemindCommand.MESSAGE_SUCCESS + remindSetting);
    }

    @Test
    public void setRemindAlreadySet() {
        commandBox.pressEnter();
        String remindSetting = "1 week";
        commandBox.runCommand(RemindCommand.COMMAND_WORD + " " + remindSetting);
        commandBox.runCommand(RemindCommand.COMMAND_WORD + " " + remindSetting);
        assertResultMessage(RemindCommand.MESSAGE_NO_CHANGE_IN_REMINDER_SETTING + remindSetting);
    }

    @Test
    public void setRemindWrongFormat() {
        commandBox.pressEnter();
        String remindSetting = "0 day";
        commandBox.runCommand(RemindCommand.COMMAND_WORD + " " + remindSetting);
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemindCommand.MESSAGE_USAGE));
    }

}
