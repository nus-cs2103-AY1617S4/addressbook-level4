package seedu.whatsnext.logic;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.whatsnext.commons.core.ComponentManager;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.CommandResult;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.logic.parser.Parser;
import seedu.whatsnext.logic.parser.exceptions.ParseException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.task.BasicTaskFeatures;

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
    public CommandResult execute(String commandText) throws CommandException, ParseException {
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
<<<<<<< HEAD
    public ObservableList<BasicTaskFeatures> getFilteredPersonList() {
=======
    public ObservableList<BaseTask> getFilteredTaskList() {
>>>>>>> f47b041c1ac2fa4f54400e3f1a83b8b755f4987f
        return model.getFilteredTaskList();
    }
}
