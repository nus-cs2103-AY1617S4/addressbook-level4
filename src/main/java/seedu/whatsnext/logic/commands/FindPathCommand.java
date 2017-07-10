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

/*    private File f = null;
    private String path = "";
    private boolean bool = false;*/


    public FindPathCommand() {
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        /*f = new File("test.txt");

        bool = f.exists();

        if (bool) {
            this.path = f.getAbsolutePath();
        }
        String string = f.getAbsolutePath();
        int texttxtSize = 8;
        int size = string.length()- texttxtSize;
        string = string.substring(0,size);
        string = string.replace("\\", "/");
        return new CommandResult(String.format(string).concat(model.getFilePath()));*/
        return new CommandResult(String.format(model.getFilePath()));

    }

}

