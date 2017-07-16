package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG_CLI;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MESSAGE, PREFIX_START_DATETIME,
                        PREFIX_END_DATETIME, PREFIX_TAG_CLI);

        // First argument would always be for name
        /*
        if (!arePrefixesPresent(argMultimap)) {
            System.out.println("ARGUMENT = " + args);
            throw new ParseException(String.format("testing", AddCommand.MESSAGE_USAGE));
        }*/

        try {
            TaskName taskName = new TaskName(argMultimap.getPreamble());
            Optional<String> startDateTimeValue = argMultimap.getValue(PREFIX_START_DATETIME);
            Optional<String> endDateTimeValue = argMultimap.getValue(PREFIX_END_DATETIME);
            Optional<String> taskDescriptionValue = argMultimap.getValue(PREFIX_MESSAGE);
            Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG_CLI));

            BasicTask task = createBasicTaskBasedOnInputs(taskName, taskDescriptionValue,
                    startDateTimeValue, endDateTimeValue, tagList);
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
    private BasicTask createBasicTaskBasedOnInputs(
            TaskName taskName, Optional<String> taskDescriptionValue, Optional<String> startDateTimeValue,
            Optional<String> endDateTimeValue, Set<Tag> tagList) throws IllegalValueException {
        BasicTask task;
        TaskDescription taskDescription = new TaskDescription();
        if (taskDescriptionValue.isPresent()) {
            taskDescription = new TaskDescription(taskDescriptionValue.get());
        }
        // Create Event Task
        if (startDateTimeValue.isPresent() && endDateTimeValue.isPresent()) {
            DateTime startDateTime = new DateTime(startDateTimeValue.get());
            DateTime endDateTime = new DateTime(endDateTimeValue.get());
            validateStartEndDateTime(startDateTime, endDateTime);
            task = new BasicTask(taskName, taskDescription, false, startDateTime, endDateTime, tagList);
        // Create Deadline Task
        } else if (endDateTimeValue.isPresent()) {
            DateTime endDateTime = new DateTime(endDateTimeValue.get());
            task = new BasicTask(taskName, taskDescription, false, endDateTime, tagList);
        // Invalid Task
        } else if (startDateTimeValue.isPresent() && !endDateTimeValue.isPresent()) {
            throw new IllegalValueException(AddCommand.INVALID_TASK_CREATED);
        // Create Floating Task
        } else {
            task = new BasicTask(taskName, taskDescription, false, tagList);
        }
        return task;
    }

    private void validateStartEndDateTime(DateTime startDateTime, DateTime endDateTime) throws IllegalValueException {
        if (!startDateTime.isBefore(endDateTime)) {
            throw new IllegalValueException(AddCommand.INVALID_TASK_CREATED);
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
