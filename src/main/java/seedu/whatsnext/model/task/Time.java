package seedu.whatsnext.model.task;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
/**
 * Represents the Time of an AdvancedTask in WhatsNext
 * Time is represented in a 24 hour format
 * @@author A0156106M
 * */
public class Time {
    private static final String VALIDATE_TIME_REGEX =
            "([0-1][0-9][0-5][0-9])|(2[0-3][0-5][0-9])";
    private static final String MESSAGE_TIME_CONSTRAINT = null;
    private String timeValue;

    public Time() {
        timeValue = "2359";
    }

    public Time(String timeInput) throws IllegalValueException {
        assert(timeInput != null);
        String timeInputTrim = timeInput.trim();
        if (!isValidTime(timeInputTrim)) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINT);
        }
        timeValue = timeInputTrim;
    }

    private boolean isValidTime(String timeInputTrim) {
        return timeInputTrim.matches(VALIDATE_TIME_REGEX);
    }

    @Override
    public String toString() {
        return timeValue;
    }

    public boolean laterOrEqual(Time time) {
        return (Integer.parseInt(timeValue) >= Integer.parseInt(time.timeValue));
    }

    @Override
    public int hashCode() {
        return timeValue.hashCode();
    }


}
