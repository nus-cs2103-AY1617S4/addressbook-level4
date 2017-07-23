package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.logic.commands.exceptions.CommandException;

//@@author A0149894H
/**
 * Shows the path where storage xml file is stored.
 */
public class FilePathCommand extends Command {
    public static final String COMMAND_WORD = "filepath";
    public static final String MESSAGE_SUCCESS = "File Path located at: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Locates the path where the storage xml file exists.";


    public FilePathCommand() {
    }

    private static final Logger logger = LogsCenter.getLogger(FilePathCommand.class);


    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        String filePath = model.getTaskManagerFilePath();
        logger.info(MESSAGE_SUCCESS + filePath);
        return new CommandResult(MESSAGE_SUCCESS + filePath);
    }



}

