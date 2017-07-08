package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_ALL;

import seedu.whatsnext.model.TaskManager;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task List has been cleared!";
    public static final Object MESSAGE_USAGE = null;

    public final String clearArgument;

    public ClearCommand(String clearArgument) {
        this.clearArgument = clearArgument;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        if (clearArgument.equals(PREFIX_ALL)){
            model.resetData(new TaskManager());
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
