package seedu.ticktask.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.List;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.commons.util.StringUtil;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.TaskType;
import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INSUFFICIENT_PARTS = "Number of parts must be more than 1.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws IllegalValueException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new IllegalValueException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<Name> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new Name(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> time} into an {@code Optional<time>} if {@code time} is present.
     */
    public static Optional<DueTime> parseTime(Optional<String> time) throws IllegalValueException {
        time = setNullToString(time);
        return time.isPresent() ? Optional.of(new DueTime(time.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     */
    public static Optional<DueDate> parseDate(Optional<String> date) throws IllegalValueException {
        date = setNullToString(date);
        return date.isPresent() ? Optional.of(new DueDate(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> taskType} into an {@code Optional<taskType>} if {@code taskType} is present.
     */
    public static Optional<TaskType> parseTaskType(Optional<String> type) throws IllegalValueException {
        type = setNullToString(type);
        return type.isPresent() ? Optional.of(new TaskType(type.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }
    
//@@author A0147928N
    public static Optional<String> setNullToString(Optional<String> data) {
        String string = " ";
        if (!data.isPresent()) {
            return Optional.of(string);
        }
        return data;
    }

    //@@author A0131884B

    /**
    * Return prefix that has arguments mapped to it, and remove prefixes that are not mapped to anything in argMultimap
    */
    public static Prefix getListPrefix(ArgumentMultimap argMultimap, Prefix... prefixes) {
         List<Prefix> temp = Stream.of(prefixes).filter(prefix -> argMultimap.getValue(prefix).isPresent()).collect(Collectors.toList());
            assert (temp.size() <= 1) : "invalid flag combination not catched beforehand or no Prefixes found!";return temp.get(0);
    }

    /**
    * Returns true if any of the prefixes contain non-empty values in argMultimap
    */
    public static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
         return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
 //@@author
}
//@@author
