package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.whatsnext.logic.commands.exceptions.CommandException;

/**
 * Shows the path where storage xml file is stored.
 */
//@@author A0149894H
public class FilePathCommand extends Command {
    public static final String COMMAND_WORD = "filepath";
    public static final String MESSAGE_SUCCESS = "File Path located at: ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Locates the path where the storage xml file exists.";


    public FilePathCommand() {
    }
    //@@author A0149894H
    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        String filePath = model.getTaskManagerFilePath();
        return new CommandResult(MESSAGE_SUCCESS + filePath);
    }



}

