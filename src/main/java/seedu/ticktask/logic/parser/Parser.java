package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.*;
import seedu.ticktask.logic.parser.exceptions.ParseException;

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
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws IllegalValueException
     */
    public Command parseCommand(String userInput) throws IllegalValueException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new ParseAddCommand().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommandParser().parse(arguments);
        
        //@@author A0147928N
        case CompleteCommand.COMMAND_WORD:
            return new CompleteCommandParser().parse(arguments);
        //@@author

        //@@author A0131884B
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        //@@author
            
        case EditCommand.COMMAND_WORD:
            return new ParseEditCommand().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        //@@author A0131884B
        case HelpCommand.COMMAND_WORD:
            return new ParseHelpCommand().parse(arguments);
        //@@author

        case HistoryCommand.COMMAND_WORD:
            return new HistoryCommand();
            
        //@@author A0138471A
        case ListCommand.COMMAND_WORD:
        	return new ListCommandParser().parse(arguments);
        	
        case StorageCommand.COMMAND_WORD:
            return new StorageCommandParser().parse(arguments);
        //@@author
            
        case RedoCommand.COMMAND_WORD:
            return new RedoCommand();

            case RestoreCommand.COMMAND_WORD:
                return new RestoreCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();
        	
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
