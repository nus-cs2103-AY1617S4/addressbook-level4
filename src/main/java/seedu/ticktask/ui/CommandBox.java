package seedu.ticktask.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import seedu.ticktask.commons.core.LogsCenter;
import seedu.ticktask.commons.events.ui.NewResultAvailableEvent;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.Logic;
import seedu.ticktask.logic.commands.*;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.parser.exceptions.ParseException;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import static seedu.ticktask.logic.commands.ListCommand.*;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;

public class CommandBox extends UiPart<Region> {
    
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    //@@author A0139964M
    private Stack<String> prevCommandsHistory = new Stack<String>();
    private Stack<String> nextCommandsHistory = new Stack<String>();
    //@@author
    private String lastPrev = "";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    //@@author A0139964M
    private AutoCompletionBinding<String> autoCompletionBinding;
    Set listOfCommands = new HashSet<>();
    // List of words for autocomplete
    String[] commands = {AddCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD + " " + PREFIX_ALL, CompleteCommand.COMMAND_WORD,
                         ClearCommand.COMMAND_WORD + " " + PREFIX_ACTIVE, ClearCommand.COMMAND_WORD + " " + PREFIX_COMPLETE,
                         DeleteCommand.COMMAND_WORD + " " + PREFIX_ACTIVE, DeleteCommand.COMMAND_WORD + " " + PREFIX_COMPLETE,
                         EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindActiveCommand.COMMAND_WORD,
                         FindCommand.COMMAND_WORD, FindCompleteCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD,
                         HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD + " " + LIST_FLOATING,
                         ListCommand.COMMAND_WORD + " " + LIST_TODAY, ListCommand.COMMAND_WORD + " " + LIST_DEADLINE,
                         RedoCommand.COMMAND_WORD, RestoreCommand.COMMAND_WORD, StorageCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD};
    
    //@@author
    @FXML
    private TextField commandTextField;
    
    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        autoComplete();
    }
    //@@author A0139964M
    /**
     * Handles KeyPresses in the commandField to cycle through commands
     * @param event which accepts UP, DOWN and ENTER keys events.
     */
    @FXML
    private void handleKeyPress(KeyEvent event) {
        KeyCode key = event.getCode();
        switch (key) {
            case UP:
                String prevCommand = getPrevCommand(lastPrev);
                lastPrev = prevCommand;
                commandTextField.setText(prevCommand);
                return;
            case DOWN:
                String nextCommand = getNextCommand();
                commandTextField.setText(nextCommand);
                return;
            case ENTER:
                addSenteceToAutoComplete(commandTextField.getText());
                return;
        }
    }
    
    /**
     *  This method takes in whatever the user has entered and save it into
     *  a array of words. These words will be suggested for auto completion
     *  in the future.
     */
    public void addSenteceToAutoComplete(String text){
        listOfCommands.add(text);
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose();
        }
        autoCompletionBinding = TextFields.bindAutoCompletion(commandTextField, listOfCommands);
        autoCompletionBinding.setVisibleRowCount(5);
        autoCompletionBinding.setPrefWidth(700);
    }
    
    /**
     *This method returns the string of the previous command
     */
    public String getPrevCommand(String lastPrev){
        if(!prevCommandsHistory.isEmpty()) {
            String prevCommand = prevCommandsHistory.pop();
            nextCommandsHistory.push(prevCommand);
            return prevCommand;
        }
        return lastPrev;
    }
    /**
     *This method returns the string of the next command
     */
    public String getNextCommand(){
        if(!nextCommandsHistory.isEmpty()) {
            String nextCommand = nextCommandsHistory.pop();
            prevCommandsHistory.push(nextCommand);
            return nextCommand;
        } else {
            return "";
        }
    }
    
    /**
     * Adds recent input into stack
     */
    private void updatePrevCommand(String commandText){
        prevCommandsHistory.push(commandText);
    }
    
    /**
     * Method that pops up an auto complete when a suitable keyword is inputted
     */
    public void autoComplete(){
        buildCommandsIntoHashSet();
        autoCompletionBinding = TextFields.bindAutoCompletion(commandTextField, listOfCommands);
        autoCompletionBinding.setPrefWidth(700);
        autoCompletionBinding.setVisibleRowCount(5);
        autoCompletionBinding.setHideOnEscape(true);
    }
    
    public void buildCommandsIntoHashSet(){
        for(int i = 0; i < commands.length; i++){
            listOfCommands.add(commands[i]);
        }
    }
    //@@author
    
    @FXML
    private void handleCommandInputChanged() throws IllegalValueException {
        try {
            String commandText = commandTextField.getText();
            CommandResult commandResult = logic.execute(commandText);
            
            //@@author A0139964M
            while(!nextCommandsHistory.isEmpty()){
                prevCommandsHistory.push(nextCommandsHistory.pop());
            }
            updatePrevCommand(commandText);
            //@@author
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
