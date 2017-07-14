package seedu.whatsnext.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.events.ui.NewResultAvailableEvent;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.Logic;
import seedu.whatsnext.logic.commands.CommandResult;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.logic.parser.exceptions.ParseException;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;

public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private int i = 0;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        commandTextField.setOnKeyPressed(
            event -> {
                switch (event.getCode()) {
                case UP:
                    if ((logic.getHistory().size() - (i + 1)) >= 0) {
                        i++;
                        commandTextField.setText(logic.getHistory().get(logic.getHistory().size() - i));
                    }
                    break;

                case DOWN:
                    if ((i - 1) > 0) {
                        i--;
                        commandTextField.setText(logic.getHistory().get(logic.getHistory().size() - i));
                    }
                    break;

                case DELETE:
                    commandTextField.setText("");
                    i = 0;
                    break;

                default:
                }
            }
        );
    }

    @FXML
    private void handleCommandInputChanged() throws IllegalValueException {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            i = 0;
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException | TagNotFoundException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }


    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
