package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.logic.commands.ChangePathCommand;
import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.commands.DeleteCommand;
import seedu.whatsnext.logic.commands.EditCommand;
import seedu.whatsnext.logic.commands.FilePathCommand;
import seedu.whatsnext.logic.commands.FindCommand;
import seedu.whatsnext.logic.commands.HelpCommand;
import seedu.whatsnext.logic.commands.HistoryCommand;
import seedu.whatsnext.logic.commands.ListCommand;
import seedu.whatsnext.logic.commands.MarkCommand;
import seedu.whatsnext.logic.commands.RedoCommand;
import seedu.whatsnext.logic.commands.RemindCommand;
import seedu.whatsnext.logic.commands.SelectCommand;
import seedu.whatsnext.logic.commands.UndoCommand;
import seedu.whatsnext.logic.commands.UnmarkCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

//@@author A0156106M
/**
 * Parses input arguments and creates a new HelpCommand object
 *
 * */
public class HelpCommandParser {

    public HelpCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            return new HelpCommand();
        }
        String helpCommandArgument = args.trim();
        if (isValid(helpCommandArgument)) {
            return new HelpCommand(helpCommandArgument);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

    }

    /**
     * Checks if help command is valid
     * @return true if it is valid
     * */
    public boolean isValid(String args) {
        if (args.equals(AddCommand.COMMAND_WORD)
                || args.equals(EditCommand.COMMAND_WORD)
                || args.equals(SelectCommand.COMMAND_WORD)
                || args.equals(DeleteCommand.COMMAND_WORD)
                || args.equals(ChangePathCommand.COMMAND_WORD)
                || args.equals(FilePathCommand.COMMAND_WORD)
                || args.equals(MarkCommand.COMMAND_WORD)
                || args.equals(UnmarkCommand.COMMAND_WORD)
                || args.equals(ClearCommand.COMMAND_WORD)
                || args.equals(FindCommand.COMMAND_WORD)
                || args.equals(ListCommand.COMMAND_WORD)
                || args.equals(UndoCommand.COMMAND_WORD)
                || args.equals(RedoCommand.COMMAND_WORD)
                || args.equals(RemindCommand.COMMAND_WORD)
                || args.equals(HistoryCommand.COMMAND_WORD)
                || args.equals(HelpCommand.COMMAND_WORD)) {
            return true;
        }
        return false;
    }

}
