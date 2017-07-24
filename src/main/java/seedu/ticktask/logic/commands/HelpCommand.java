package seedu.ticktask.logic.commands;

import seedu.ticktask.commons.core.EventsCenter;
import seedu.ticktask.commons.events.ui.ShowHelpRequestEvent;

//@@author A0131884B
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
                                               + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Please add command keyword after help.\n"
                                                      + "The basic command words are as follows:\n"
                                                      + "add, clear, complete, delete, edit, find, list, "
                                                      + "redo, restore, save, select and undo.\n"
                                                      + "Example: help add";

    private String usageMessage;
    /**
     * Create a HelpCommand object with help message for a command
     * @param helpMessage The message for usage of a specific command
     */
    public HelpCommand(String helpMessage) {
        super();
        usageMessage = helpMessage;
    }

    /**
     * Create a HelpCommand object with no specific help message
     */
    public HelpCommand() {
        super();
    }

    @Override
    public CommandResult execute() {
        if (usageMessage == null) {
            return new CommandResult(SHOWING_HELP_MESSAGE);
        } else {
            return new CommandResult(usageMessage);
        }
    }
}
//@@author
