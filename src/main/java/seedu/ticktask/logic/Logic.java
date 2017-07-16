package seedu.ticktask.logic;

import javafx.collections.ObservableList;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.commands.exceptions.WarningException;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.task.ReadOnlyTask;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     * @throws IllegalValueException
     * @throws WarningException
     */
    CommandResult execute(String commandText) throws CommandException, IllegalValueException, WarningException;

    /** Returns the filtered list of tasks */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Returns the filtered list of completed tasks */
    ObservableList<ReadOnlyTask> getFilteredCompletedTaskList();


}
