package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EmptyStackException;

import seedu.whatsnext.logic.commands.exceptions.CommandException;

//@@author A0154986L
/**
 * Undo the previous action in task manager.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Previous action has been undone.";
    public static final String MESSAGE_FAILURE = "Nothing to undo.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo previous command.\n"
            + "Example: undo ";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.undoTaskManager();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
