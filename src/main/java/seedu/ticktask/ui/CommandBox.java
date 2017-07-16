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
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.commands.exceptions.WarningException;
import seedu.ticktask.logic.parser.exceptions.ParseException;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

public class CommandBox extends UiPart<Region> {

	public static final String ERROR_STYLE_CLASS = "error";
	private static final String FXML = "CommandBox.fxml";
	private Stack<String>prevCommandsHistory = new Stack<String>();
	private Stack<String>nextCommandsHistory = new Stack<String>();
	private String currentShownCommand;
	private String lastPrev = "";
	private final Logger logger = LogsCenter.getLogger(CommandBox.class);
	private final Logic logic;
	private AutoCompletionBinding<String> autoCompletionBinding;

	Set listOfCommands = new HashSet<>();
	String[] commands = { "add", "delete", "edit", "clear", "help", "undo", "redo", "find", "exit", "delete" };

	@FXML
	private TextField commandTextField;

	public CommandBox(Logic logic) {
		super(FXML);
		this.logic = logic;
		autoComplete();
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
			String nextCommand = getNextCommand();
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
	public String getNextCommand(){
		if(!nextCommandsHistory.isEmpty()) {
			String nextCommand = nextCommandsHistory.pop();
			prevCommandsHistory.push(nextCommand);
			return nextCommand;
		} else {
			return "";
		}
	}

	private void updatePrevCommand(String commandText){
		prevCommandsHistory.push(commandText);
	}

	public void autoComplete(){
		buildCommandsIntoHashSet();
		autoCompletionBinding = TextFields.bindAutoCompletion(commandTextField, listOfCommands);
		autoCompletionBinding.setPrefWidth(300);
		autoCompletionBinding.setVisibleRowCount(1);
		autoCompletionBinding.setHideOnEscape(true);
	}

	public void buildCommandsIntoHashSet(){
		for(int i = 0; i < commands.length; i++){
			listOfCommands.add(commands[i]);
		}
	}
	//@@author

	@FXML
	private void handleCommandInputChanged() throws IllegalValueException, WarningException {
		try {
			String commandText = commandTextField.getText();
			currentShownCommand = commandText;
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
