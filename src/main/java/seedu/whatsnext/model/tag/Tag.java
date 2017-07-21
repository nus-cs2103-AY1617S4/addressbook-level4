package seedu.whatsnext.model.tag;

import static java.util.Objects.requireNonNull;

import seedu.whatsnext.commons.exceptions.IllegalValueException;

/**
 * Represents a Tag in the task manager.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";

    public static final String RESERVED_TAG_HIGH = "HIGH";
    public static final String RESERVED_TAG_MEDIUM = "MEDIUM";
    public static final String RESEVERD_TAG_LOW = "LOW";
    public static final String RESERVED_TAG_OVERLAP = "OVERLAP";

    public static final String[] RESERVEREDTAGS = {RESERVED_TAG_HIGH, RESERVED_TAG_MEDIUM, RESEVERD_TAG_LOW};


    public final String tagName;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = trimmedName.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && this.tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + tagName + ']';
    }

    //@@author A0142675B
    /**
     * Returns true if the given tagName is a reserved Tag for priority.
     */
    public boolean isPriorityTag() {
        String trimmedName = tagName.trim();
        assert isValidTagName(trimmedName);

        for (int i = 0; i < 3; i++) {
            if (trimmedName.toUpperCase().equals(RESERVEREDTAGS[i])) {
                return true;
            }
        }
        return false;
    }

}
