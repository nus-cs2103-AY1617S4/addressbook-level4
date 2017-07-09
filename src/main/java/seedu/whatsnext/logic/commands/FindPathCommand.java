package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;


<<<<<<< HEAD
import seedu.whatsnext.logic.commands.exceptions.CommandException;
/**
 * Shows the path where storage xml file is stored.
 */
public class FindPathCommand extends Command{
=======
public class FindPathCommand extends Command {
>>>>>>> ae6c25bf05038f445a3deefa266954a6ece71d18
    public static final String COMMAND_WORD = "filePath";
    public static final String MESSAGE_SUCCESS = "File Path located at:";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Locates the path where the storage xml file exists.";

    public FindPathCommand(){
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(model.getFilePath()));
    }

}
