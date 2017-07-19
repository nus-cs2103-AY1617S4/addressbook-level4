package seedu.whatsnext.logic.commands;

//@@author A0154986L
/**
 * Lists all uncompleted/ completed/ all tasks in the task manager to the user.
 * It can also list tasks by type only.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String LIST_INCOMPLETE = "incomplete";
    public static final String LIST_COMPLETED = "completed";
    public static final String LIST_EXPIRED = "expired";
    public static final String LIST_ALL = "all";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "list the tasks in the task manager.\n"
            + "To list upcoming tasks : list\n"
            + "To list incomplete tasks : list incomplete\n"
            + "To list completed tasks : list completed\n"
            + "To list expired tasks : list expired\n"
            + "To list all tasks : list all";

    public static final String MESSAGE_SUCCESS_UPCOMING = "Listed all upcoming incomplete tasks";
    public static final String MESSAGE_SUCCESS_INCOMPLETE = "Listed all incomplete tasks";
    public static final String MESSAGE_SUCCESS_COMPLETED = "Listed all completed tasks";
    public static final String MESSAGE_SUCCESS_EXPIRED = "Listed all expired tasks";
    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";

    private final String argument;

    public ListCommand(String argument) {
        this.argument = argument;
    }

    @Override
    public CommandResult execute() {
        if (argument.isEmpty()) {
            model.updateFilteredTaskListToShowUpcomingTasks();
            return new CommandResult(MESSAGE_SUCCESS_UPCOMING);
        } else if (argument.equals(LIST_INCOMPLETE)) {
            boolean isComplete = false;
            model.updateFilteredTaskListToShowByCompletion(isComplete);
            return new CommandResult(MESSAGE_SUCCESS_INCOMPLETE);
        } else if (argument.equals(LIST_COMPLETED)) {
            boolean isComplete = true;
            model.updateFilteredTaskListToShowByCompletion(isComplete);
            return new CommandResult(MESSAGE_SUCCESS_COMPLETED);
        } else if (argument.equals(LIST_EXPIRED)) {
            model.updateFilteredTaskListToShowByExpiry();
            return new CommandResult(MESSAGE_SUCCESS_EXPIRED);
        } else {
            model.updateFilteredListToShowAll();
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
