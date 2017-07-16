package seedu.ticktask.ui;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import seedu.ticktask.commons.core.LogsCenter;
import seedu.ticktask.commons.events.ui.NewResultAvailableEvent;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.Logic;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.parser.exceptions.ParseException;

public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private Stack<String>prevCommandsHistory = new Stack<String>();
    private Stack<String>nextCommandsHistory = new Stack<String>();
    private String currentShownCommand;
    private String lastPrev = "";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
    }
    
    //@@author A0139964M
    @FXML
    private void handleKeyPress(KeyEvent event){
        KeyCode key = event.getCode();
        switch (key) {
            case UP:
                String prevCommand = getPrevCommand(lastPrev);
                lastPrev = prevCommand;
                commandTextField.setText(prevCommand);
                return;
            case DOWN:
                String nextCommand = getNextCommand(currentShownCommand);
                commandTextField.setText(nextCommand);
                return;
        }
    }
    
    public String getPrevCommand(String lastPrev){
        if(!prevCommandsHistory.isEmpty()) {
            String prevCommand = prevCommandsHistory.pop();
            nextCommandsHistory.push(prevCommand);
            return prevCommand;
        }
        return lastPrev;
    }
    public String getNextCommand(String shownCommand){
        if(!nextCommandsHistory.isEmpty()) {
            String nextCommand = nextCommandsHistory.pop();
            prevCommandsHistory.push(nextCommand);
            return nextCommand;
        } else {
            return shownCommand;
        }
    }
    
    private void updatePrevCommand(String commandText){
        prevCommandsHistory.push(commandText);
    }
    //@@author
    
    @FXML
    private void handleCommandInputChanged() throws IllegalValueException {
        try {
            String commandText = commandTextField.getText();
            currentShownCommand = commandText;
            CommandResult commandResult = logic.execute(commandText);

            while(!nextCommandsHistory.isEmpty()){
                prevCommandsHistory.push(nextCommandsHistory.pop());
            }
            
            updatePrevCommand(commandText);
    
            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
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
