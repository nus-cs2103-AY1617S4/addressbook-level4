package seedu.whatsnext.logic.commands;

import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;

//@@author A0154986L
/**
 * Lists all uncompleted/ completed/ expired/ all tasks in the task manager to the user.
 * It can also list tasks by type only.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String LIST_INCOMPLETE = "incomplete";
    public static final String LIST_COMPLETED = "completed";
    public static final String LIST_EXPIRED = "expired";
    public static final String LIST_ALL = "all";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List the tasks in the task manager.\n"
            + "To list upcoming tasks : list\n"
            + "To list incomplete tasks : list incomplete\n"
            + "To list completed tasks : list completed\n"
            + "To list expired tasks : list expired\n"
            + "To list all tasks : list all";

    public static final String MESSAGE_SUCCESS_UPCOMING = "List all upcoming incomplete tasks";
    public static final String MESSAGE_SUCCESS_INCOMPLETE = "List all incomplete tasks";
    public static final String MESSAGE_SUCCESS_COMPLETED = "List all completed tasks";
    public static final String MESSAGE_SUCCESS_EXPIRED = "List all expired tasks";
    public static final String MESSAGE_SUCCESS_ALL = "List all tasks";

    private static final Logger logger = LogsCenter.getLogger(ListCommand.class);

    private final String argument;

    public ListCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public CommandResult execute() {
        if (argument.isEmpty()) {
            model.updateFilteredTaskListToShowUpcomingTasks();
            logger.info(MESSAGE_SUCCESS_UPCOMING);
            return new CommandResult(MESSAGE_SUCCESS_UPCOMING);
        } else if (argument.equals(LIST_INCOMPLETE)) {
            boolean isComplete = false;
            model.updateFilteredTaskListToShowByCompletion(isComplete);
            logger.info(MESSAGE_SUCCESS_INCOMPLETE);
            return new CommandResult(MESSAGE_SUCCESS_INCOMPLETE);
        } else if (argument.equals(LIST_COMPLETED)) {
            boolean isComplete = true;
            model.updateFilteredTaskListToShowByCompletion(isComplete);
            logger.info(MESSAGE_SUCCESS_COMPLETED);
            return new CommandResult(MESSAGE_SUCCESS_COMPLETED);
        } else if (argument.equals(LIST_EXPIRED)) {
            model.updateFilteredTaskListToShowByExpiry();
            logger.info(MESSAGE_SUCCESS_EXPIRED);
            return new CommandResult(MESSAGE_SUCCESS_EXPIRED);
        } else {
            model.updateFilteredListToShowAll();
            logger.info(MESSAGE_SUCCESS_ALL);
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }
    }

    //@@author A0142675B
    @Override
    public boolean equals(Object other) {
        ListCommand e = (ListCommand) other;
        return e.argument.equals(this.argument);
    }
}
