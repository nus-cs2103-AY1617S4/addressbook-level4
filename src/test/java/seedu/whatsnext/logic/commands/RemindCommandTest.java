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

    private Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());


    /***
     * Tests updating a new reminder setting success.
     */
    @Test
    public void execute_setNewReminderSetting_success() {
        RemindCommand remindCommand = new RemindCommand(VALID_REMINDER_SETTING);
        remindCommand.setData(model, new CommandHistory());

        CommandResult result = remindCommand.execute();
        result = remindCommand.execute();

        String expectedMessage = String.format(RemindCommand.MESSAGE_SUCCESS, VALID_REMINDER_SETTING);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        model.setReminderSetting(VALID_REMINDER_SETTING);

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, model);
    }

    /***
     * Tests updating reminder setting results in no change.
     */
    @Test
    public void execute_noChangeInReminderSetting() throws Exception {
        RemindCommand remindCommand = new RemindCommand(DEFAULT_REMINDER_SETTING);
        remindCommand.setData(model, new CommandHistory());

        CommandResult result = remindCommand.execute();
        result = remindCommand.execute();

        String expectedMessage = String.format(RemindCommand.MESSAGE_NO_CHANGE_IN_REMINDER_SETTING,
                DEFAULT_REMINDER_SETTING);

        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        model.setReminderSetting(DEFAULT_REMINDER_SETTING);

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, model);
    }

}
