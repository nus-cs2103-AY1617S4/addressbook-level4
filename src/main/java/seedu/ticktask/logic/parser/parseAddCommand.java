package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.TaskType;

//@@A0139964M
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class parseAddCommand {

    public static final Pattern ADD_COMMAND_FORMAT =
            Pattern.compile("(?<name>(.(?!\\bby\\b|\\bfrom\\b|#|\\bat\\b))+)" //.(?!by)+ will keep matching until by.
            + "(?=.*(by|from)\\s(?<dates>(.(?!.*'|#|\\bat\\b))+)?)?"
            + "(?=.*(at)\\s(?<time>(.(?!.*'|#))+)?)?"
            + "((?=.*#(?<tags>.+)))?"
            + ".*");

    private Set<Tag> tagList;

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        Matcher matcher = ADD_COMMAND_FORMAT.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        try {
            Optional<String> parsename = Optional.ofNullable(matcher.group("name"));
            Optional<String> parsedate = Optional.ofNullable(matcher.group("dates"));
            Optional<String> parsetime = Optional.ofNullable((matcher.group("time")));
            Optional<String> parsetag = Optional.ofNullable((matcher.group("tags")));
            tagList = createTagList(parsetag);
            Name name = ParserUtil.parseName(parsename).get();
            DueTime time = ParserUtil.parseTime(parsetime).get();
            TaskType type = ParserUtil.parseTaskType(Optional.of(" ")).get();
            DueDate date = ParserUtil.parseDate(parsedate).get();


            ReadOnlyTask task = new Task(name, time, type, date, tagList);

            return new AddCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    public Set<Tag> createTagList(Optional<String> parsetag) throws IllegalValueException {
        if (parsetag.isPresent()) {
            String[] stringArray = parsetag.get().split(" ");
            Collection<String> tagCollection = Arrays.asList(stringArray);
            HashSet<String> tagsList = new HashSet<>(tagCollection);
            tagList = ParserUtil.parseTags(tagsList);
        } else {
            HashSet<String> emptyTagList = new HashSet<String>();
            tagList = ParserUtil.parseTags(emptyTagList);
        }
        return tagList;
    }
}
