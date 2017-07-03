package seedu.ticktask.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.commons.util.StringUtil;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.Date;
import seedu.ticktask.model.task.Email;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.Time;

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
    public static Optional<Time> parseTime(Optional<String> time) throws IllegalValueException {
        time = setNullToString(time);
        return time.isPresent() ? Optional.of(new Time(time.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     */
    public static Optional<Date> parseDate(Optional<String> date) throws IllegalValueException {
        date = setNullToString(date);
        return date.isPresent() ? Optional.of(new Date(date.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        email = setNullToString(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
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
    public static Optional<String> setNullToString(Optional<String> data){
        String string = " ";
        if(!data.isPresent()){
            return Optional.of(string);
        }
        return data;
    }
}
