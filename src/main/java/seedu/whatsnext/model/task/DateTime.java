package seedu.whatsnext.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.whatsnext.commons.exceptions.IllegalValueException;

//@@author A0156106M
/**
 * Represents the Date of a BasicTask
 *
 * */
public class DateTime {
    public static final String INIT_DATETIME_VALUE = "0001/01/01 00:00";
    public static final String DEFAULT_TIME_VALUE = " 23:59";
    public static final String MESSAGE_DATE_CONSTRAINT = "Task date should be either "
            + "a day (e.g. friday) or a date with the format: DD/MM/YY (e.g. 06/07/17)\n";
    public static final String MESSAGE_DATE_INVALID = "A Task cannot be created before today.";

    private final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy/dd/MM HH:mm");
    private final DateFormat dateTimeFormatDisplay = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa");
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/dd/MM");
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private Date dateValue;

    public DateTime() throws IllegalValueException {
        this(INIT_DATETIME_VALUE);
    }

    public DateTime(String dateInput) throws IllegalValueException {
        assert(dateInput != null);
        String dateInputTrim = dateInput.trim();
        initDateValue(dateInputTrim);
    }

    /**
     * Initializes the dateValue object variable base on its input parameters
     *
     * */
    private void initDateValue(String dateInputTrim) throws IllegalValueException {
        if (dateInputTrim.equals(INIT_DATETIME_VALUE)) {
            try {
                dateValue = dateTimeFormat.parse(INIT_DATETIME_VALUE);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } else {
            List<Date> dateInputList = new PrettyTimeParser().parse(dateInputTrim);
            if (!isValidDate(dateInputList)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINT);
            }
            dateValue = dateInputList.get(0);
            validateDateTime();

        }
    }

    //@@author A0156106M
    /**
     * Prevents User from setting Tasks before today
     * @throws IllegalValueException when dateValue is before today's date
     * */
    private void validateDateTime() throws IllegalValueException {
        Date today = new Date();
        /*
        if (isBefore(today)) {
            throw new IllegalValueException(MESSAGE_DATE_INVALID);
        }*/
        if (timeFormat.format(today).equals(getTime())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateValue);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            dateValue = calendar.getTime();
        }
    }

    /**
     * Checks if the list is empty
     * */
    private boolean isValidDate(List<Date> dateInputList) {
        return !dateInputList.isEmpty();
    }

    public String getDate() {
        return dateFormat.format(dateValue);
    }

    public String getTime() {
        return timeFormat.format(dateValue);
    }

    @Override
    public String toString() {
        return dateTimeFormat.format(dateValue);
    }

    public String displayDateTime() {
        return dateTimeFormatDisplay.format(dateValue);
    }

    public boolean equals(DateTime other) {
        return this.toString().equals(other.toString());
    }

    /**
     * @return true if dateValue is before parameter
     * */
    public boolean isBefore(DateTime endDateTime) {
        // dateValue is before source
        return dateValue.compareTo(endDateTime.dateValue) < 0;
    }

    public boolean isBefore(Date endDate) {
        // dateValue is before source
        return dateValue.compareTo(endDate) < 0;
    }

    /**
     * @return true when dateValue is equal or after source's dateValue
     * */
    public boolean isAfterOrEqual(DateTime source) {
        // dateValue is after or equal source
        return dateValue.compareTo(source.dateValue) >= 0;
    }

    /**
     * @return true when dateValue is equal or before source's dateValue
     * */
    public boolean isBeforeOrEqual(DateTime source) {
        // dateValue is before or equal source
        return dateValue.compareTo(source.dateValue) <= 0;
    }

    /**
     * @return true when DateValue contains INIT_DATETIME_VALUE
     * */
    public boolean isEmpty() {
        return toString().equals(INIT_DATETIME_VALUE);
    }

}
