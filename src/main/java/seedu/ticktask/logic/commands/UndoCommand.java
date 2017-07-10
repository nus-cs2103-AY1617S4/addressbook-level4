//@@author A0139819N
package seedu.ticktask.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.EmptyStackException;

import seedu.ticktask.logic.commands.exceptions.CommandException;


public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Undo a previously completed action on TickTask.";
    
    public static final String MESSAGE_SUCCESS = "Undo successful!";
    public static final String MESSAGE_FAILURE = "Undo failed! TickTask could not find any existing previous commands.";
    
    
    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.undoPreviousCommand();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyStackException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

    }
    

}
//@@author A0139819N
