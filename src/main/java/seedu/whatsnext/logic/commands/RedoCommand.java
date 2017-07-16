package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EmptyStackException;

import seedu.whatsnext.logic.commands.exceptions.CommandException;

//@@author A0154986L
/**
 * Redo the previous action in task manager.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Previous action has been redone.";
    public static final String MESSAGE_FAILURE = "Nothing to redo.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Redo previous command.\n"
            + "Example: redo ";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.redoTaskManager();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
