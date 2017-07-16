/*package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import seedu.whatsnext.commons.core.Config;
import seedu.whatsnext.commons.core.Config.RepeatTaskManagerFilePathException;
import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.logic.commands.ChangePathCommand;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.UserPrefs;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

public class ChangePathCommandTest {

    public static final String INVALID_SAVE_LOCATION = "Invalid input for save location";
    public static final String TEST_ORIGINAL_LOCATION = "data";
    public static final String TEST_SAVE_LOCATION = "data/test";

    @Test
    public void execute_InvalidCommand_success() {
        Config config = new Config();
        config.setTaskManagerFilePath(TEST_ORIGINAL_LOCATION);
        commandBox.runCommand("changepathdata");
        assertInvalidCommandSuccess(Messages.MESSAGE_UNKNOWN_COMMAND, OUTPUT);
    }

    @Test
    public void execute_InvalidSaveLocation_success() {
        commandBox.runCommand("changepath hello");
        assertInvalidSaveLocationSuccess(INVALID_SAVE_LOCATION + "\n" + ChangePathCommand.MESSAGE_USAGE, OUTPUT);
    }

    //@@author A0149894H
    @Test
    public void execute_noSaveLocation_success() {
        commandBox.runCommand("changepath");
        assertNoSaveLocationSuccess(
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePathCommand.MESSAGE_USAGE), OUTPUT);
    }
    //@@author A0149894H
    @Test
    public void execute_properSaveLocation_success() throws RepeatTaskManagerFilePathException, CommandException {
        Config config = new Config();
        Model model = new ModelManager(new TaskManager(), new UserPrefs());
        config.setTaskManagerFilePath(TEST_ORIGINAL_LOCATION);

        File file = new File(TEST_SAVE_LOCATION);
        assertProperSaveLocationSuccess(config, model, file);

    }

    *//**
     * Executes {@code ChangePathCommand} on the given {@code model}, confirms that <br>
     * - the result message matches {@code ChangePathCommand.MESSAGE_SUCCESS} <br>
     * - file path remains the same {@code model} is empty <br>
     * @throws CommandException
     *//*
    private void assertProperSaveLocationSuccess(Config config, Model model, File file) throws CommandException{
        System.out.println(model);
        ChangePathCommand command = new ChangePathCommand(file);
        CommandResult result = command.execute();

        assertEquals(ChangePathCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertEquals(TEST_SAVE_LOCATION, config.getTaskManagerFilePath());
    }
}*/
