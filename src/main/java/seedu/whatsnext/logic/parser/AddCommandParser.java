package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TO;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskName;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     * @@author A0156106M
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ON, PREFIX_TO, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap)) {
            System.out.println("ARGUMENT = " + args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            TaskName taskName = new TaskName(argMultimap.getPreamble());
            Optional<String> startDateTime = DateTime.formatDateTime(argMultimap.getValue(PREFIX_ON));
            Optional<String> endDateTime = DateTime.formatDateTime(argMultimap.getValue(PREFIX_TO));
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
            if (startDateTime.isPresent() && endDateTime.isPresent()) {
                System.out.println("EVENT");
                BasicTask task = new BasicTask(taskName, tagList);
                return new AddCommand(task);
            } else if (startDateTime.isPresent()) {
                System.out.println("DEADLINE");
                BasicTask task = new BasicTask(taskName, tagList);
                return new AddCommand(task);
            } else {
                System.out.println("FLOATING");
                BasicTask task = new BasicTask(taskName, tagList);
                return new AddCommand(task);
            }
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
