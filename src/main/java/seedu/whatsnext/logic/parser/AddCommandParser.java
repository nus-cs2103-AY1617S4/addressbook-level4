package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG_CLI;

import java.util.Optional;
import java.util.Set;

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

        try {

            if (args.contains(PREFIX_START_DATETIME.toString()) || args.contains(PREFIX_END_DATETIME.toString())
                    || args.contains(PREFIX_MESSAGE.toString()) || args.contains(PREFIX_TAG_CLI.toString())) {
                return parseCommandByPrefix(args);
            } else {
                return parseCommandByComma(args);
            }

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage() + "\n" + AddCommand.MESSAGE_USAGE, ive);
        }
    }

    /**
     * Parses the argument based on Prefix
     * @return AddCommand Object based on tokenized Prefix
     * */
    private AddCommand parseCommandByPrefix(String args) throws IllegalValueException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MESSAGE, PREFIX_START_DATETIME,
                        PREFIX_END_DATETIME, PREFIX_TAG_CLI);
        TaskName taskName = new TaskName(argMultimap.getPreamble());
        Optional<String> startDateTimeValue = argMultimap.getValue(PREFIX_START_DATETIME);
        Optional<String> endDateTimeValue = argMultimap.getValue(PREFIX_END_DATETIME);
        Optional<String> taskDescriptionValue = argMultimap.getValue(PREFIX_MESSAGE);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG_CLI));

        BasicTask task = createBasicTaskBasedOnInputs(taskName, taskDescriptionValue,
                startDateTimeValue, endDateTimeValue, tagList);
        return new AddCommand(task);
    }

    /**
     * Parses the argument based on Comma
     * @return AddCommand Object based on tokenized values
     * */
    private AddCommand parseCommandByComma(String args) throws IllegalValueException {
        SplitCommaParser splitCommandParser = new SplitCommaParser();
        splitCommandParser.tokenize(args);

        TaskName taskName = new TaskName(splitCommandParser.getTaskName());
        Optional<String> startDateTimeValue = splitCommandParser.getStartDateTime();
        Optional<String> endDateTimeValue = splitCommandParser.getEndDateTime();
        Optional<String> taskDescriptionValue = splitCommandParser.getDescription();
        if (startDateTimeValue.isPresent() && !endDateTimeValue.isPresent()) {
            endDateTimeValue = startDateTimeValue;
            startDateTimeValue = Optional.empty();
        }
        Set<Tag> tagList = splitCommandParser.parseTags();

        BasicTask task = createBasicTaskBasedOnInputs(taskName, taskDescriptionValue,
                startDateTimeValue, endDateTimeValue, tagList);
        return new AddCommand(task);
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

    /**
     * Ensures that the startDateTime is not after the endDateTime
     * @throws IllegalValueException
     * */
    private void validateStartEndDateTime(DateTime startDateTime, DateTime endDateTime) throws IllegalValueException {
        if (!startDateTime.isBefore(endDateTime)) {
            throw new IllegalValueException(AddCommand.INVALID_TASK_CREATED);
        }
    }


}
