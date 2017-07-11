package seedu.whatsnext.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import seedu.whatsnext.commons.core.Config;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.util.ConfigUtil;
import seedu.whatsnext.commons.util.StringUtil;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.storage.XmlTaskManagerStorage;



/**
 * Changes location where task manager is stored.
 */
//@@author A0149894H
public class ChangePathCommand extends Command {
    private static final Logger logger = LogsCenter.getLogger(ChangePathCommand.class);
    public static final String COMMAND_WORD = "changePath";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes where task manager is stored. Takes in new file location";
    public static final String MESSAGE_CREATED_NEW_CONFIG_FILE = "TEST";
    public static final String MESSAGE_REPEAT_TASK_MANAGER_FILE_PATH = "This file is already saved at this location";
    public static final String MESSAGE_SUCCESS = "Save location changed to: %1$s";

    private final File toSave;
    private static String toDelete = Config.getTaskManagerFilePath();

    public ChangePathCommand(File filePath){
        this.toSave = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            Config config = new Config();
            config.setTaskManagerFilePath(toSave.toString());
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);           
            
            File f = new File("test.txt");
            String string = f.getAbsolutePath();
            int texttxtSize = 8;
            int size = string.length()- texttxtSize;
            string = string.substring(0,size);
            string = string.replace("\\", "/");
            String deleteLocation = String.format(string).concat(toDelete);
            File filePath = new File(deleteLocation);
            filePath.delete(); 


            XmlTaskManagerStorage.changeTaskManagerFilePath(toSave.toString());
            model.saveTaskManager();

            return new CommandResult(String.format(MESSAGE_SUCCESS, toSave));
        } catch (Config.RepeatTaskManagerFilePathException dtmfpe) {
            throw new CommandException(MESSAGE_REPEAT_TASK_MANAGER_FILE_PATH);
        } catch (IOException ioe) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(ioe));
            throw new CommandException(MESSAGE_CREATED_NEW_CONFIG_FILE);
        }        
        
    }


}