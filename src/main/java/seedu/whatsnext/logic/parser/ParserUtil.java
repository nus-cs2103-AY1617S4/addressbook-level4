package seedu.whatsnext.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.commons.util.StringUtil;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes
 */
public class ParserUtil {
    public static final String HIGH = "HIGH";
    public static final String MEDIUM = "MEDIUM";
    public static final String LOW = "LOW";
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
    public static Optional<TaskName> parseName(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new TaskName(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startDateTime} into an {@code Optional<startDateTime>}
     * if {@code startDateTime} is present.
     * @@author A0142675B
     */
    public static Optional<DateTime> parseStartDateTime(Optional<String> startDateTime) throws IllegalValueException {
        requireNonNull(startDateTime);
        return startDateTime.isPresent() ? Optional.of(new DateTime(startDateTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> startDateTime} into an {@code Optional<startDateTime>}
     * if {@code startDateTime} is present.
     * @@author A0142675B
     */
    public static Optional<DateTime> parseEndDateTime(Optional<String> endDateTime) throws IllegalValueException {
        requireNonNull(endDateTime);
        return endDateTime.isPresent() ? Optional.of(new DateTime(endDateTime.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<String> parseType(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new String(name.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<Name>} if {@code name} is present.
     */
    public static Optional<TaskDescription> parseDescription(Optional<String> description)
            throws IllegalValueException {
        requireNonNull(description);
        return description.isPresent() ? Optional.of(new TaskDescription(description.get())) : Optional.empty();
    }


    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            if (isPriorityTagString(tagName)) {
                tagSet.add(new Tag(tagName.trim()));
                break;
            }
        }

        for (String tagName : tags) {
            if (!isPriorityTagString(tagName)) {
                tagSet.add(new Tag(tagName.trim()));
            }
        }
        return tagSet;
    }

    //@@author A0156106M
    /**
     * Parses {@code Optional<String> tags} into a {@code Set<Tag>}.
     * @return {@code Set<tag>} which represent all tags
     */
    public static Set<Tag> parseMultipleTags(Optional<String> tagStringInput) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        if (tagStringInput.isPresent()) {
            String tagString = tagStringInput.get();
            String[] tagList = tagString.split(" ");
            for (String tagName : tagList) {
                tagSet.add(new Tag(tagName.trim()));
            }
        }
        return tagSet;
    }

    private static boolean isPriorityTagString(String tagName) {
        return tagName.toUpperCase().equals(HIGH)
              || tagName.toUpperCase().equals(MEDIUM)
              || tagName.toUpperCase().equals(LOW);

    }

}
