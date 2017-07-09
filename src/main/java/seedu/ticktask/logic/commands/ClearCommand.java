package seedu.ticktask.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.ticktask.model.TickTask;

/**
 * Clears the TickTask program.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "The TickTask program has been cleared!";


    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.resetData(new TickTask());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
