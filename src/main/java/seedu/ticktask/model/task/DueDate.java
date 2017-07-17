package seedu.ticktask.model.task;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.Collections;
import seedu.ticktask.commons.exceptions.IllegalValueException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Objects.requireNonNull;

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
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    private static final int FIRST_INDEX_OF_ARRAY = 0;
    private static final int INDEX_START_DATE = 0;
    private static final int INDEX_END_DATE = 1;
    //@@author A0139819N
    public static final String DATE_REGEX_SINGLE = "\\d{2}?/\\d{2}?/\\d{4}?";
    public static final String DATE_REGEX_RANGE = "\\d{2}?/\\d{2}?/\\d{4}?\\-\\d{2}?/\\d{2}?/\\d{4}?";
    //@@author

    private final Parser parser = new Parser();
    //private final SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");

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

        if (end_date != null){
            value = getStartDate() + " - " + getEndDate();
        }else{
            value = getStartDate();
        }


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
            Collections.sort(datesArray);
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


    //@@author A0139819N
    /*
     * Method used to convert date input by user f
     */
    private String convertDateFormat(String trimmedDate) {
        
        System.out.println("OLD Trimmed date string: " + trimmedDate);
        
        DateTimeFormatter internationalDateParser = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter americanDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //LocalDate inDate = LocalDate.parse(trimmedDate, internationalDateParser);

        if(trimmedDate.matches(DATE_REGEX_SINGLE)){
            trimmedDate = americanDateFormatter.format(internationalDateParser.parse(trimmedDate));
        }else if (trimmedDate.matches(DATE_REGEX_RANGE)){
            String startDate = americanDateFormatter.format(internationalDateParser.parse(trimmedDate.substring(0,10)));
            String endDate = americanDateFormatter.format(internationalDateParser.parse(trimmedDate.substring(11,21)));
            System.out.println("START: " + startDate + "END: " + endDate);
            trimmedDate = startDate + " " + endDate;
        }
        System.out.println("NEW trimmed date string: " + trimmedDate);

        return trimmedDate;
    }
    //@@author
    
    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
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
            end_date = end_date2;
            if (end_date != null){
                value = getStartDate() + " - " + getEndDate();
            }else{
                value = getStartDate();
            }
        }

    }

    public void setStartDate(LocalDate start_date2) {
        if (start_date2 == null) {
            start_date_string =  "";

        }
        else {
            start_date_string = start_date2.format(DATE_FORMAT).toString();
            start_date = start_date2;
            if (end_date != null){
                value = getStartDate() + " - " + getEndDate();
            }else{
                value = getStartDate();
            }
        }

    }

    public String getEndDate() {

        return end_date_string;
    }
    
    public LocalDate getLocalStartDate() {
        return start_date;
    }
    
    public LocalDate getLocalEndDate() {
        return end_date;
    }

    /**
     * Returns true if a given date is an empty string or invalid string
     * */
    public boolean isFloating() {
        return isFloating;
    }
    
    /**
     * Sets the date to floating
     */
    public void setFloating(){
        isFloating = true;
        isDeadline = false;
        isRange = false;
    }
    
    /**
     * Returns true if a given date only has a start date
     * */
    public boolean isDeadline() {
        return isDeadline;
    }

    /**
     * Sets the date to floating
     */
    public void setDeadline(){
        isFloating = false;
        isDeadline = true;
        isRange = false;
    }
    
    
    /**
     * Returns true if a given date has both start and end date
     */
    public boolean isRange() {
        return isRange;
    }
    
    /**
     * Sets the date to range
     */
    public void setRange(){
        isFloating = false;
        isDeadline = false;
        isRange = true;
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
