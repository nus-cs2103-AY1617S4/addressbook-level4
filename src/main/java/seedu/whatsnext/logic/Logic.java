package seedu.whatsnext.logic;

import javafx.collections.ObservableList;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.CommandResult;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.logic.parser.exceptions.ParseException;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;

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
     * @throws TagNotFoundException If an error occurs during tag removal.
     * @throws IllegalValueException
     */
    CommandResult execute(String commandText)
            throws CommandException, ParseException, TagNotFoundException, IllegalValueException;

    /** Returns the filtered list of persons */

    ObservableList<BasicTaskFeatures> getFilteredTaskList();

    ObservableList<BasicTaskFeatures> getInitialFilteredTaskList();

    // @@author A0154986L
    /**
     * Returns the filtered event task list for reminder pop up window.
     */
    ObservableList<BasicTaskFeatures> getEventReminderList();

    // @@author A0154986L
    /**
     * Returns the filtered deadline task list for reminder pop up window.
     */
    ObservableList<BasicTaskFeatures> getDeadlineReminderList();

}
