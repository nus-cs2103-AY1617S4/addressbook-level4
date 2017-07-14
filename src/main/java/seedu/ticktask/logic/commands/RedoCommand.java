//@@author A0139819N
package seedu.ticktask.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EmptyStackException;

import seedu.ticktask.logic.commands.exceptions.CommandException;


public class RedoCommand extends Command {
    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Redo an existing command that was previously undone by the user.";

    public static final String MESSAGE_SUCCESS = "Redo successful!";
    public static final String MESSAGE_FAILURE = "Redo failed! TickTask is already at latest state.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.redoUndoneCommand();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

    }



}
//@@author A0139819N
