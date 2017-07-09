package seedu.whatsnext.logic.commands;

import java.util.Set;

//@@author A0154986L
/**
 * Lists all uncompleted/ completed/ all tasks in the task manager to the user.
 * It can also list tasks by type only.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String LIST_COMPLETED = "completed";
    public static final String LIST_ALL = "all";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all uncompleted tasks\n"
            + COMMAND_WORD + "completed/all: List all completed/all tasks\n";

    public static final String MESSAGE_SUCCESS_UNCOMPLETED = "Listed all uncompleted tasks";
    public static final String MESSAGE_SUCCESS_COMPLETED = "Listed all completed tasks";
    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";
    public static final String MESSAGE_INVALID_COMMAND = "Invalid list command";

    private final Set<String> keywords;

    public ListCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        if (keywords.isEmpty()) {
            boolean isComplete = false;
            model.updateFilteredTaskListToShowByCompletion(isComplete);
            return new CommandResult(MESSAGE_SUCCESS_UNCOMPLETED);
        } else if (keywords.contains(LIST_COMPLETED)) {
            boolean isComplete = true;
            model.updateFilteredTaskListToShowByCompletion(isComplete);
            return new CommandResult(MESSAGE_SUCCESS_COMPLETED);
        } else if (keywords.contains(LIST_ALL)) {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }
        return new CommandResult(MESSAGE_INVALID_COMMAND);
    }
}
