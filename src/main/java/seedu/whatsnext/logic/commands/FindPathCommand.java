package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;

import seedu.whatsnext.logic.commands.exceptions.CommandException;
/**
 * Shows the path where storage xml file is stored.
 */
//@@author A0149894H
public class FindPathCommand extends Command {
    public static final String COMMAND_WORD = "findPath";
    public static final String MESSAGE_SUCCESS = "File Path located at:";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Locates the path where the storage xml file exists.";

    private File f = null;
    private String path = "";
    private boolean bool = false;


    public FindPathCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        // create new files
        File f = new File("test.txt");

        // returns true if the file exists
        bool = f.exists();

        // if file exists
        if (bool) {
            this.path = f.getAbsolutePath();
        }

        return new CommandResult(String.format(model.getFilePath()));

    }

}

