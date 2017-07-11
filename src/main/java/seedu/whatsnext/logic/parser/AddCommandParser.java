package seedu.whatsnext.logic.parser;

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
    //@@author A0156106M
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ON, PREFIX_TO, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap)) {
            System.out.println("ARGUMENT = " + args);
            throw new ParseException(String.format("testing", AddCommand.MESSAGE_USAGE));
        }

        try {
            TaskName taskName = new TaskName(argMultimap.getPreamble());
            Optional<String> startDateTimeValue = argMultimap.getValue(PREFIX_ON);
            Optional<String> endDateTimeValue = argMultimap.getValue(PREFIX_TO);
            Optional<String> tagString = argMultimap.getValue(PREFIX_TAG);
            Set<Tag> tagList = ParserUtil.parseMultipleTags(tagString);
            BasicTask task = createBasicTaskBasedOnInputs(taskName, startDateTimeValue, endDateTimeValue, tagList);
            return new AddCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Creates the Basic Task object base on the value of startDateTimeValue and endDateTimeValue
     * @return BasicTask object
     * @throws ParseException if the user input does not conform the expected format
     */
    private BasicTask createBasicTaskBasedOnInputs(TaskName taskName, Optional<String> startDateTimeValue,
            Optional<String> endDateTimeValue, Set<Tag> tagList) throws IllegalValueException {
        BasicTask task;
        // Create Event Task
        if (startDateTimeValue.isPresent() && endDateTimeValue.isPresent()) {
            DateTime startDateTime = new DateTime(startDateTimeValue.get());
            DateTime endDateTime = new DateTime(endDateTimeValue.get());
            task = new BasicTask(taskName, false, startDateTime, endDateTime, tagList);
        // Create Deadline Task
        } else if (startDateTimeValue.isPresent()) {
            DateTime startDateTime = new DateTime(startDateTimeValue.get());
            task = new BasicTask(taskName, false, startDateTime, tagList);
        // Create Floating Task
        } else {
            task = new BasicTask(taskName, tagList);
        }
        return task;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
