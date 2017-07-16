package seedu.ticktask.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.ticktask.commons.core.Config;
import seedu.ticktask.commons.core.LogsCenter;
import seedu.ticktask.commons.util.ConfigUtil;
import seedu.ticktask.commons.util.StringUtil;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.storage.XmlTickTaskStorage;

//@@author A0138471A
/**
 * Changes the location where the TickTask file is saved.
 */

public class StorageCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_SUCCESS = "Location changed to: %1$s";
    public static final String MESSAGE_DUPLICATE_TICK_TASK_FILE_PATH = "File is already saved at this location";
    public static final String MESSAGE_CREATED_NEW_CONFIG_FILE = "IO";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the location of the Tick Task app doc.\n"
            + "Example: " + COMMAND_WORD + " doc";

    private final File file;

    public StorageCommand(File location) {
        this.file = location;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {

            Config config = new Config();
            config.setTickTaskFilePath(file.toString());
            ConfigUtil.saveConfig(config, Config.DEFAULT_CONFIG_FILE);
            XmlTickTaskStorage.setTickTaskFilePath(file.toString());
            model.saveTickTask();
            return new CommandResult(String.format(MESSAGE_SUCCESS, file));

        } catch (Config.DuplicateTickTaskFilePathException dtmfpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TICK_TASK_FILE_PATH);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_CREATED_NEW_CONFIG_FILE);
        }
    }
}
