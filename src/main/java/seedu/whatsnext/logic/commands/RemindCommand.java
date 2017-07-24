package seedu.whatsnext.logic.commands;

import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;

//@@author A0154986L
/**
* Adjusts the reminder setting of the task manager.
*/
public class RemindCommand extends Command {

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sets the remind period\n"
            + COMMAND_WORD + " 10 minute/6 hour/3 day/...week/month/year\n"
            + "Example: " + COMMAND_WORD + " 6 hour\n"
            + "Sets reminders to be i.e. 6 hour before events and deadlines\n"
            + "NOTE: Do not put 's' behind the time unit";

    public static final String MESSAGE_POPUP = "Display reminders";
    public static final String MESSAGE_SUCCESS = "Reminder set: ";
    public static final String MESSAGE_NO_CHANGE_IN_REMINDER_SETTING = "No change in reminder setting.\n"
            + "Reminder setting: ";

    private static final Logger logger = LogsCenter.getLogger(RemindCommand.class);

    private String reminderString;

    public RemindCommand(String reminderString) {
        this.reminderString = reminderString;
    }

    @Override
    public CommandResult execute() {
        if (reminderString == null || reminderString.isEmpty()) {
            model.showReminderAlert();
            return new CommandResult(MESSAGE_POPUP);
        }
        String currentReminderSetting = model.getReminderSetting();
        if (reminderString.equals(currentReminderSetting)) {
            logger.info(MESSAGE_NO_CHANGE_IN_REMINDER_SETTING + currentReminderSetting);
            return new CommandResult(MESSAGE_NO_CHANGE_IN_REMINDER_SETTING + currentReminderSetting);
        } else {
            model.setReminderSetting(reminderString);
            logger.fine(MESSAGE_SUCCESS + reminderString);
            return new CommandResult(MESSAGE_SUCCESS + reminderString);
        }
    }

    @Override
    public boolean equals(Object other) {
        RemindCommand e = (RemindCommand) other;
        return e.reminderString.equals(this.reminderString);
    }
}
