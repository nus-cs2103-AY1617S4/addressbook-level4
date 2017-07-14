package seedu.ticktask.model.task;

import static java.util.Objects.requireNonNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.ticktask.commons.exceptions.IllegalValueException;

//@@author A0138471A
/**
 * Represents a Task's date in the TickTask.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Enter a valid date";

    /*
     * The first character of the date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String START_DATE_VALIDATION_REGEX = "start date.*";
    public static final String END_DATE_VALIDATION_REGEX = "end date.*";
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(START_DATE_VALIDATION_REGEX);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static final int FIRST_INDEX_OF_ARRAY = 0;
    private static final int INDEX_START_DATE = 0;
    private static final int INDEX_END_DATE = 1;

    private final Parser parser = new Parser();
    //private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    private String value;
    private LocalDate local_date;

    private ArrayList<LocalDate> datesArray = new ArrayList<LocalDate>();
    private LocalDate start_date;
    private LocalDate end_date;
    private String start_date_string = "",
            end_date_string = "";

    private boolean isFloating = false;
    private boolean isRange = false;
    private boolean isDeadline = false;


    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        requireNonNull(date);

        String trimmedDate = date.trim();

        if (((date.matches(END_DATE_VALIDATION_REGEX)) || (date.matches(START_DATE_VALIDATION_REGEX)))) {
            List<DateGroup> dateGroups = parser.parse(trimmedDate);
            /*if(dateGroups.isEmpty()){
             * throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
            }*/
            if (!dateGroups.isEmpty()) {
                for (Date dates : dateGroups.get(FIRST_INDEX_OF_ARRAY).getDates()) {
                    local_date = Instant.ofEpochMilli(dates.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                    datesArray.add(local_date);
                }
            }
            if (date.matches(END_DATE_VALIDATION_REGEX)) {
                end_date = datesArray.get(INDEX_START_DATE);
                setEndDate(end_date);
            }
            if (date.matches(START_DATE_VALIDATION_REGEX)) {
                start_date = datesArray.get(INDEX_START_DATE);
                setStartDate(start_date);
            }

        } else {
            extractDate(trimmedDate);
        }

        value = getStartDate() + " " + getEndDate();

    }


    private void extractDate(String trimmedDate) {
        List<DateGroup> dateGroups = parser.parse(trimmedDate);
        /* if(dateGroups.isEmpty()){
         * throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
         * */
        if (!dateGroups.isEmpty()) {
            for (Date dates : dateGroups.get(FIRST_INDEX_OF_ARRAY).getDates()) {
                local_date = Instant.ofEpochMilli(dates.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                datesArray.add(local_date);
            }
        } else {
            setStartDate(null);
            setEndDate(null);
        }
        if (datesArray.size() == 2) {
            start_date = datesArray.get(INDEX_START_DATE);
            end_date = datesArray.get(INDEX_END_DATE);
            setStartDate(start_date);
            setEndDate(end_date);
            isRange = true;
        } else if (datesArray.size() == 1) {
            start_date = datesArray.get(INDEX_START_DATE);
            setStartDate(start_date);
            setEndDate(null);
            isDeadline = true;

        } else {
            setStartDate(null);
            setEndDate(null);
            isFloating = true;
        }

    }

    public String getStartDate() {
        return start_date_string;
    }

    public void setEndDate(LocalDate end_date2) {
        if (end_date2 == null) {
            end_date_string =  "";
        }
        else {
            end_date_string = end_date2.format(DATE_FORMAT).toString();
        }

    }
    public void setStartDate(LocalDate start_date2) {
        if (start_date2 == null) {
            start_date_string =  "";
        }
        else {
            start_date_string = start_date2.format(DATE_FORMAT).toString();
        }

    }

    public String getEndDate() {

        return end_date_string;
    }

    /**
     * Returns true if a given date is an empty string or invalid string
     * */
    public boolean isFloating() {
        return isFloating;
    }

    /**
     * Returns true if a given date only has a start date
     * */
    public boolean isDeadline() {
        return isDeadline;
    }

    /**
     * Returns true if a given date has both start and end date
     */
    public boolean isRange() {
        return isRange;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(START_DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueDate // instanceof handles nulls
                        && this.value.equals(((DueDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
