package seedu.ticktask.logic.parser;

import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.logic.commands.CompleteCommand;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.logic.commands.EditCommand;
import seedu.ticktask.logic.commands.FindCommand;
import seedu.ticktask.logic.commands.ListCommand;
import seedu.ticktask.logic.commands.RedoCommand;
import seedu.ticktask.logic.commands.RestoreCommand;
import seedu.ticktask.logic.commands.SelectCommand;
import seedu.ticktask.logic.commands.StorageCommand;
import seedu.ticktask.logic.commands.UndoCommand;
import seedu.ticktask.logic.commands.HelpCommand;
import seedu.ticktask.logic.commands.ExitCommand;
import seedu.ticktask.logic.commands.HistoryCommand;
//@@author A0131884B
/**
 * Parses input arguments and creates a new HelpCommand object.
 */
public class ParseHelpCommand {
    /**
     * Parses the given {String} of arguments in the context of the HelpCommand.
     * and returns a HelpCommand object for execution.
     * @param args The string after the command word help.
     */
    public HelpCommand parse(String args) {
        if (args == null) {
            return new HelpCommand();
        }
        
        final String commandWord = args.trim();
        final String helpMessage = parseCommand(commandWord);
        if (helpMessage == null) {
            return new HelpCommand();
        } else {
            return new HelpCommand(helpMessage);
        }
    }
    /**
     * Check for the command word in the given {String} of command
     * and returns the help message of the corresponding command.
     * @param commandWord A string which may be the command word.
     */
    private static String parseCommand(String commandWord) {
        switch (commandWord) {
            case AddCommand.COMMAND_WORD:
                return AddCommand.MESSAGE_USAGE;

            case ClearCommand.COMMAND_WORD:
                return ClearCommand.MESSAGE_USAGE;

            case CompleteCommand.COMMAND_WORD:
                return CompleteCommand.MESSAGE_USAGE;

            case DeleteCommand.COMMAND_WORD:
                return DeleteCommand.MESSAGE_USAGE;

            case EditCommand.COMMAND_WORD:
                return EditCommand.MESSAGE_USAGE;

            case ExitCommand.COMMAND_WORD:
                return ExitCommand.MESSAGE_USAGE;

            case FindCommand.COMMAND_WORD:
                return FindCommand.MESSAGE_USAGE;

            case HelpCommand.COMMAND_WORD:
                return HelpCommand.MESSAGE_USAGE;

            case HistoryCommand.COMMAND_WORD:
                return HistoryCommand.MESSAGE_USAGE;

            case ListCommand.COMMAND_WORD:
                return ListCommand.MESSAGE_USAGE;

            case RedoCommand.COMMAND_WORD:
                return RedoCommand.MESSAGE_USAGE;

            case RestoreCommand.COMMAND_WORD:
                return RestoreCommand.MESSAGE_USAGE;

            case SelectCommand.COMMAND_WORD:
                return SelectCommand.MESSAGE_USAGE;

            case StorageCommand.COMMAND_WORD:
                return StorageCommand.MESSAGE_USAGE;

            case UndoCommand.COMMAND_WORD:
                return UndoCommand.MESSAGE_USAGE;

            default:
                return null;
        }
    }
}
