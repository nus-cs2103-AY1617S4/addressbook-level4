package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.testutil.TypicalTasks;

//@@author A0154986L
public class RemindCommandTest {

    private static final String DEFAULT_REMINDER_SETTING = "3 day";
    private static final String VALID_REMINDER_SETTING = "1 week";

    /***
     * Tests updating reminder setting results in no change.
     */
    @Test
    public void execute_noChangeInReminderSetting() throws Exception {
        Model actualModel = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        RemindCommand remindCommand = new RemindCommand(DEFAULT_REMINDER_SETTING);
        remindCommand.setData(actualModel, new CommandHistory());

        CommandResult result = remindCommand.execute();

        String expectedMessage = String.format(RemindCommand.MESSAGE_NO_CHANGE_IN_REMINDER_SETTING)
                + DEFAULT_REMINDER_SETTING;

        Model expectedModel = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        expectedModel.setReminderSetting(DEFAULT_REMINDER_SETTING);

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, actualModel);
    }

    /***
     * Tests updating a new reminder setting success.
     */
    @Test
    public void execute_setNewReminderSetting_success() {
        Model actualModel = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        RemindCommand remindCommand = new RemindCommand(VALID_REMINDER_SETTING);
        remindCommand.setData(actualModel, new CommandHistory());

        CommandResult result = remindCommand.execute();

        String expectedMessage = String.format(RemindCommand.MESSAGE_SUCCESS) + VALID_REMINDER_SETTING;

        Model expectedModel = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        expectedModel.setReminderSetting(VALID_REMINDER_SETTING);

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, actualModel);
    }

}
