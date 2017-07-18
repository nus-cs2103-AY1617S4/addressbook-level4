package seedu.whatsnext.logic.commands;

import java.io.File;
import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.storage.XmlTaskManagerStorage;



/**
 * Changes location where task manager is stored.
 */
//@@author A0149894H
public class ChangePathCommand extends Command {
    public static final String COMMAND_WORD = "changepath";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes where task manager is stored. Takes in new file location";
    public static final String MESSAGE_CREATED_NEW_CONFIG_FILE = "TEST";
    public static final String MESSAGE_REPEAT_TASK_MANAGER_FILE_PATH = "This file is already saved at this location";
    public static final String MESSAGE_SUCCESS = "Save location changed to: %1$s";

    private static final Logger logger = LogsCenter.getLogger(ChangePathCommand.class);



    private static File toSave;




    public ChangePathCommand(File filePath) {
        toSave = filePath;
    }

    /**
     * Deletes old file at previous location
     */
    //@@author A0149894H
    private void deleteOldFile() {
        File f = new File("test.txt");
        String string = f.getAbsolutePath();
        int texttxtSize = 8;
        int size = string.length() - texttxtSize;
        string = string.substring(0, size);
        String deleteLocation = String.format(string).concat(model.getTaskManagerFilePath());
        deleteLocation = deleteLocation.replace("\\", "/");
        File toDeleteFilePath = new File(deleteLocation);
        toDeleteFilePath.delete();
    }

    //@@author A0149894H
    @Override
    public CommandResult execute() throws CommandException {
        deleteOldFile();

        //overwrite file path
        model.setTaskManagerFilePath(toSave.toString());


        XmlTaskManagerStorage.changeTaskManagerFilePath(toSave.toString());
        model.saveTaskManager();

        logger.fine(MESSAGE_SUCCESS + toSave.toString());
        return new CommandResult(String.format(MESSAGE_SUCCESS, toSave));

    }




}
