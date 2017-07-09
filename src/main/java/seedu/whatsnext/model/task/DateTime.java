package seedu.whatsnext.model.task;

import java.util.Optional;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

//@@author A0156106M
/**
 * Represents the Date of an AdvancedTask in WhatsNext
 * Time is represented in a 24 hour format
 * */
public class DateTime {
    public static final String DATE_VALIDATION_REGEX = "\\d{2}/\\d{2}/\\d{2}";
    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String MESSAGE_DATE_CONSTRAINT = "Task date should be either "
            + "a day (e.g. friday) or a date with the format: DD/MM/YY (e.g. 06/07/17)\n";
<<<<<<< HEAD

    private String dateValue;

=======
    private String dateValue;
>>>>>>> ae6c25bf05038f445a3deefa266954a6ece71d18
    public DateTime(String dateInput) throws IllegalValueException {
        assert(dateInput != null);
        String dateInputTrim = dateInput.trim();
        if (!isValidDate(dateInputTrim)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINT);
        }
        dateValue = dateInputTrim;
    }

    private boolean isValidDate(String dateInputTrim) {
        return dateInputTrim.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return dateValue;
    }

    public static Optional<String> formatDateTime(Optional<String> dateTime) throws ParseException {
        if (dateTime.equals(Optional.empty()) || dateTime.get().equals("")) {
            return dateTime;
        }
        return null;
    }

}
