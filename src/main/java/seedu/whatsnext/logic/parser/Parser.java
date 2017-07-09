package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.DeleteCommand;
import seedu.whatsnext.logic.commands.EditCommand;
import seedu.whatsnext.logic.commands.ExitCommand;
import seedu.whatsnext.logic.commands.FindCommand;
import seedu.whatsnext.logic.commands.HelpCommand;
import seedu.whatsnext.logic.commands.HistoryCommand;
import seedu.whatsnext.logic.commands.ListCommand;
import seedu.whatsnext.logic.commands.MarkCommand;
import seedu.whatsnext.logic.commands.SelectCommand;
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

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        //@@author A0156106M
        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parseMarkCommand(arguments);

        //@@author A0156106M
        case UnmarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parseUnmarkCommand(arguments);

        case ClearCommand.COMMAND_WORD:
//            return new ClearCommand();
            return new ClearCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        //@@author A0154986L
        case ListCommand.COMMAND_WORD:
            return new ListCommandParser().parse(arguments);

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
