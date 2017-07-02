package seedu.ticktask.logic;

import javafx.collections.ObservableList;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.person.ReadOnlyTask;

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
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns the filtered list of persons */
    ObservableList<ReadOnlyTask> getFilteredPersonList();

}
