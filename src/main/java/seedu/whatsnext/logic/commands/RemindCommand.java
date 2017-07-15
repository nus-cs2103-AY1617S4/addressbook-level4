package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.logic.commands.CommandResult;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.ui.UiManager;

//@@author A0154986L
/**
* Adjusts the reminder setting of the task manager.
*/
public class RemindCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(RemindCommand.class);

    public static final String COMMAND_WORD = "remind";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " 10 minute/6 hour/3 day/...week/month/year\n"
            + "Sets reminders to be i.e. 6 hour before events and deadlines\n"
            + "NOTE: Do not put 's' behind the time unit";

    public static final String MESSAGE_SUCCESS = "Reminder set: ";
    public static final String MESSAGE_NO_CHANGE_IN_REMINDER_SETTING = "No change in reminder setting.\n"
            + "Reminder setting: ";

    public String reminderString;

    public RemindCommand(String reminderString) {
        this.reminderString = reminderString;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(reminderString);
        String currentReminderSetting = UiManager.getReminderSetting();
        
        if (reminderString.equals(currentReminderSetting)) {
            logger.info(MESSAGE_NO_CHANGE_IN_REMINDER_SETTING);
            throw new CommandException(MESSAGE_NO_CHANGE_IN_REMINDER_SETTING + currentReminderSetting);
        } else {
            UiManager.setReminderString(reminderString);
            logger.fine(MESSAGE_SUCCESS + reminderString);
            return new CommandResult(MESSAGE_SUCCESS + reminderString);
        }
    }

}
