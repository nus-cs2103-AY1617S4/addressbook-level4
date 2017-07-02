package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.person.Date;
import seedu.ticktask.model.person.Email;
import seedu.ticktask.model.person.Name;
import seedu.ticktask.model.person.ReadOnlyTask;
import seedu.ticktask.model.person.Task;
import seedu.ticktask.model.person.Time;
import seedu.ticktask.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TIME, PREFIX_EMAIL, PREFIX_DATE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        try {
            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME)).get();
            Time time = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_TIME)).get();
            Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL)).get();
            Date address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_DATE)).get();
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

            ReadOnlyTask person = new Task(name, time, email, address, tagList);

            return new AddCommand(person);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
