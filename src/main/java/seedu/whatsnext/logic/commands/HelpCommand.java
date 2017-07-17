package seedu.whatsnext.logic.commands;

import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.events.ui.ShowHelpRequestEvent;

//@@author A0156106M
/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD + " " + AddCommand.COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public static final boolean OPEN_WINDOW = true;
    public static final boolean CLOSE_WINDOW = false;

    private boolean displayWindow = OPEN_WINDOW;
    private String helpMessage;

    public HelpCommand() {
        displayWindow = OPEN_WINDOW;
        helpMessage = SHOWING_HELP_MESSAGE;
    }

    public HelpCommand(String command) {
        displayWindow = CLOSE_WINDOW;
        helpMessage = showMessageUsage(command);

    }

    @Override
    public CommandResult execute() {
        if (displayWindow == OPEN_WINDOW) {
            EventsCenter.getInstance().post(new ShowHelpRequestEvent());
        }
        return new CommandResult(helpMessage);
    }

    /**
     * Returns respective MESSAGE_USAGE based on input commands
     * */
    public String showMessageUsage(String args) {
        String messageUsage;
        switch (args) {
        case AddCommand.COMMAND_WORD:
            messageUsage = AddCommand.MESSAGE_USAGE;
            break;
        case EditCommand.COMMAND_WORD:
            messageUsage = EditCommand.MESSAGE_USAGE;
            break;
        case SelectCommand.COMMAND_WORD:
            messageUsage = SelectCommand.MESSAGE_USAGE;
            break;
        case DeleteCommand.COMMAND_WORD:
            messageUsage = DeleteCommand.MESSAGE_USAGE;
            break;
        case ChangePathCommand.COMMAND_WORD:
            messageUsage = ChangePathCommand.MESSAGE_USAGE;
            break;
        case FilePathCommand.COMMAND_WORD:
            messageUsage = FilePathCommand.MESSAGE_USAGE;
            break;
        case MarkCommand.COMMAND_WORD:
            messageUsage = MarkCommand.MESSAGE_USAGE;
            break;
        case UnmarkCommand.COMMAND_WORD:
            messageUsage = UnmarkCommand.MESSAGE_USAGE;
            break;
        case ClearCommand.COMMAND_WORD:
            messageUsage = ClearCommand.MESSAGE_USAGE;
            break;
        case FindCommand.COMMAND_WORD:
            messageUsage = FindCommand.MESSAGE_USAGE;
            break;
        case ListCommand.COMMAND_WORD:
            messageUsage = ListCommand.MESSAGE_USAGE;
            break;
        case UndoCommand.COMMAND_WORD:
            messageUsage = UndoCommand.MESSAGE_USAGE;
            break;
        case RedoCommand.COMMAND_WORD:
            messageUsage = RedoCommand.MESSAGE_USAGE;
            break;
        case RemindCommand.COMMAND_WORD:
            messageUsage = RemindCommand.MESSAGE_USAGE;
            break;
        case HistoryCommand.COMMAND_WORD:
            messageUsage = HistoryCommand.MESSAGE_USAGE;
            break;
        default:
            messageUsage = HelpCommand.MESSAGE_USAGE;
        }

        return messageUsage;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HelpCommand)) {
            return false;
        }

        HelpCommand e = (HelpCommand) other;
        return e.displayWindow == this.displayWindow
                && e.helpMessage.equals(this.helpMessage);
    }
}
