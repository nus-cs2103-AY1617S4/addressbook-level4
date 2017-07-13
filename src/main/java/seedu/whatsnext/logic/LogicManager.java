package seedu.whatsnext.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.whatsnext.commons.core.ComponentManager;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.CommandResult;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.logic.parser.Parser;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;

/**
 * The main LogicManager of the app.
 */
public class LogicManager extends ComponentManager implements Logic {
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final CommandHistory history;
    private final Parser parser;

    public LogicManager(Model model) {
        this.model = model;
        this.history = new CommandHistory();
        this.parser = new Parser();
    }

    @Override
    public CommandResult execute(String commandText)
            throws CommandException, TagNotFoundException, IllegalValueException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        try {
            Command command = parser.parseCommand(commandText);
            command.setData(model, history);
            return command.execute();
        } finally {
            history.add(commandText);
        }
    }

    @Override
    public ObservableList<BasicTaskFeatures> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<BasicTaskFeatures> getInitialFilteredTaskList() {
        model.updateFilteredTaskListForInitialView();
        return model.getFilteredTaskList();
    }
  
    // @@author A0154986L
    /**
     * Returns the filtered event task list for reminder pop up window.
     */
    @Override
    public ObservableList<BasicTaskFeatures> getEventReminderList() {
        return model.getFilteredTaskListForEventReminder();
    }

    // @@author A0154986L
    /**
     * Returns the filtered deadline task list for reminder pop up window.
     */
    @Override
    public ObservableList<BasicTaskFeatures> getDeadlineReminderList() {
        return model.getFilteredTaskListForDeadlineReminder();
    }
}
