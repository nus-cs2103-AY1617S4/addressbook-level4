package seedu.whatsnext.logic.commands;

import java.util.Set;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.exceptions.CommandException;

//@@author A0154986L
/**
 * Lists all incomplete/ completed/ all tasks in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String LIST_INCOMPLETE = "incomplete";
    public static final String LIST_COMPLETED = "completed";
    public static final String LIST_ALL = "all";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all incomplete/completed/all tasks\n"
            + "Parameters: {}/incomplete/completed/all";

    public static final String MESSAGE_SUCCESS_INCOMPLETE = "Listed all incomplete tasks";
    public static final String MESSAGE_SUCCESS_COMPLETED = "Listed all completed tasks";
    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";

    private final Set<String> keywords;

    public ListCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (keywords.isEmpty() || keywords.contains(LIST_INCOMPLETE)) {
            boolean isComplete = false;
            model.updateFilteredTaskListToShowByCompletion(isComplete);
            return new CommandResult(MESSAGE_SUCCESS_INCOMPLETE);
        } else if (keywords.contains(LIST_COMPLETED)) {
            boolean isComplete = true;
            model.updateFilteredTaskListToShowByCompletion(isComplete);
            return new CommandResult(MESSAGE_SUCCESS_COMPLETED);
        } else if (keywords.contains(LIST_ALL)) {
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_LIST_COMMAND + "\n"
                    + MESSAGE_USAGE);
        }
    }
}
