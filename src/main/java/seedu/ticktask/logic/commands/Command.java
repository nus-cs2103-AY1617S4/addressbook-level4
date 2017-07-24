package seedu.ticktask.logic.commands;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.task.ReadOnlyTask;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
    protected CommandHistory history;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of tasks.
     *
     * @param displaySize used to generate summary
     * @return summary message for tasks displayed
     */
    public static String getMessageForTaskListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    //@@author A0131884B
    public abstract CommandResult execute() throws CommandException, IllegalValueException;
    //@@author
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history) {
        this.model = model;
    }
}
