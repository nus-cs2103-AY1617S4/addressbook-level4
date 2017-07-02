package seedu.ticktask.model.person;

import static java.util.Objects.requireNonNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.ticktask.commons.exceptions.IllegalValueException;

/**
 * Represents a task's deadline time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    private int _hours;
    private int _minutes;
    
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should be in a 24 hours format HHMM";
    public static final String PHONE_VALIDATION_REGEX = "(\\d{2}+)(\\d{2}+)";
    
    public String value;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public Time(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedTime = phone.trim();
        if (!isValidTime(trimmedTime)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        Pattern pattern = Pattern.compile(PHONE_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(trimmedTime);
        if (matcher.matches()){
            _hours = Integer.parseInt(matcher.group(1));
            _minutes = Integer.parseInt(matcher.group(2));
            this.value = "" + _hours + _minutes;
        }

    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidTime(String test) {
        return test.matches(PHONE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
