package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.logic.commands.ChangePathCommand;
import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.DeleteCommand;
import seedu.whatsnext.logic.commands.EditCommand;
import seedu.whatsnext.logic.commands.ExitCommand;
import seedu.whatsnext.logic.commands.FilePathCommand;
import seedu.whatsnext.logic.commands.FindCommand;
import seedu.whatsnext.logic.commands.HelpCommand;
import seedu.whatsnext.logic.commands.HistoryCommand;
import seedu.whatsnext.logic.commands.ListCommand;
import seedu.whatsnext.logic.commands.MarkCommand;
import seedu.whatsnext.logic.commands.RedoCommand;
import seedu.whatsnext.logic.commands.RemindCommand;
import seedu.whatsnext.logic.commands.ResetCommand;
import seedu.whatsnext.logic.commands.SelectCommand;
import seedu.whatsnext.logic.commands.UndoCommand;
import seedu.whatsnext.logic.commands.UnmarkCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class Parser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        //@@author A0156106M
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        //@@author A0142675B
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        //@@author A0149894H
        case ChangePathCommand.COMMAND_WORD:
            return new ChangePathCommandParser().parse(arguments);

        //@@author A0149894H
        case FilePathCommand.COMMAND_WORD:
            return new FilePathCommand();

        //@@author A0156106M
        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parseMarkCommand(arguments);

        //@@author A0156106M
        case UnmarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parseUnmarkCommand(arguments);

        //@@author A0156106M
        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);

        //@@author A0142675B
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        //@@author A0154986L
        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        //@@author A0154986L
        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        //@@author A0154986L
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

        //@@author A0154986L
        case RemindCommand.COMMAND_WORD:
            return new RemindCommandParser().parse(arguments);

        //@@author A0142675B
        case ResetCommand.COMMAND_WORD:
            return new ResetCommandParser().parse(arguments);

        //@@author
        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        //@@author A0156106M
        case HelpCommand.COMMAND_WORD:
            return new HelpCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
