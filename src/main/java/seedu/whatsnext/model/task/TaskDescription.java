package seedu.whatsnext.model.task;

import static java.util.Objects.requireNonNull;

import seedu.whatsnext.commons.exceptions.IllegalValueException;

//@@author A0156106M
/**
 * Represents a Task's name in the WhatsNext application.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */

public class TaskDescription {
    public static final String INIT_DECRIPTION_VALUE = "Empty";
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Task description should only contain alphanumeric characters and spaces";
    /*
     * The first character of the task must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TASK_DESCRIPTION_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String fullTaskDescription;

    public TaskDescription() throws IllegalValueException {
        this (INIT_DECRIPTION_VALUE);
    }

    /**
     * Validates given Description.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public TaskDescription(String description) throws IllegalValueException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!isValidDescription(trimmedDescription)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullTaskDescription = trimmedDescription;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(TASK_DESCRIPTION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullTaskDescription;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskDescription // instanceof handles nulls
                && this.fullTaskDescription.equals(((TaskDescription) other).fullTaskDescription)); // state check
    }

    @Override
    public int hashCode() {
        return fullTaskDescription.hashCode();
    }
}
