package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.whatsnext.commons.core.Config;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.storage.XmlTaskManagerStorage;
import seedu.whatsnext.commons.util.ConfigUtil;
import seedu.whatsnext.commons.util.StringUtil;
import seedu.whatsnext.storage.XmlTaskManagerStorage;

/**
 * Changes location where task manager is stored.
 */
public class ChangePathCommand extends Command{
    private static final Logger logger = LogsCenter.getLogger(ChangePathCommand.class);
    
    public static final String COMMAND_WORD = "changePath";
    public static final String MESSAGE_SUCCESS = "Save location changed to: %1$s";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes where task manager is stored.";
    public static final String MESSAGE_REPEAT_TASK_MANAGER_FILE_PATH = "This file is already saved at this location";
    public static final String MESSAGE_CREATED_NEW_CONFIG_FILE = "TEST";
    
    private final File toSave;
    
    //@@author A0149894H
    public ChangePathCommand(File filePath){
        this.toSave = filePath;
    }
    
    @Override
    public CommandResult execute() throws CommandException {
        try {
            Config config = new Config();
            config.setTaskManagerFilePath(toSave.toString());
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);

            XmlTaskManagerStorage.changeTaskManagerFilePath(toSave.toString());
            model.saveTaskManager();

            return new CommandResult(String.format(MESSAGE_SUCCESS, toSave));
        } catch (Config.RepeatTaskManagerFilePathException dtmfpe) {
            throw new CommandException(MESSAGE_REPEAT_TASK_MANAGER_FILE_PATH);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_CREATED_NEW_CONFIG_FILE);
        }
    }


}
