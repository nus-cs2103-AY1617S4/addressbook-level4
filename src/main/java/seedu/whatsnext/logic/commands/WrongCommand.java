package seedu.whatsnext.logic.commands;

import seedu.whatsnext.logic.commands.exceptions.CommandException;

/**
 * Represents an incorrect command. Upon execution, throws a CommandException with feedback to the user.
 */
public class WrongCommand extends Command {

    public final String feedbackToUser;

    public WrongCommand(String feedbackToUser) {
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute() throws CommandException {
        throw new CommandException(feedbackToUser);
    }

}
