package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EmptyStackException;
import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(UndoCommand.class);

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.undoTaskManager();
            logger.fine(MESSAGE_SUCCESS);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException ese) {
            logger.info(MESSAGE_FAILURE);
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
