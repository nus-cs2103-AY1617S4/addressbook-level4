package seedu.ticktask.model.task;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Objects.requireNonNull;

//@@author A0138471A
/**
 * Represents a task's deadline time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class DueTime {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time is not a valid time";
    public static final String START_TIME_VALIDATION_REGEX = "start time.*";
    public static final String END_TIME_VALIDATION_REGEX = "end time.*";
    private static final String MESSAGE_END_TIME_CONSTRAINTS = "End time does not exist";
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final int FIRST_INDEX_OF_ARRAY = 0;
    private static final int INDEX_START_TIME = 0;
    private static final int INDEX_END_TIME = 1;
    private final Parser parser = new Parser();
    private String value;
    private LocalTime local_time;
    private ArrayList<LocalTime> timesArray = new ArrayList<LocalTime>();
    private LocalTime start_time;
    private LocalTime end_time;
    private String start_time_string = "";
    private String end_time_string = "";
    private boolean isFloating = false;
    private boolean isRange = false;
    private boolean isDeadline = false;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public DueTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();

        if (((time.matches(END_TIME_VALIDATION_REGEX)) || (time.matches(START_TIME_VALIDATION_REGEX)))) {
            List<DateGroup> dateGroups = parser.parse(time);

            if (!dateGroups.isEmpty()) {
                for (Date dates : dateGroups.get(FIRST_INDEX_OF_ARRAY).getDates()) {
                    local_time = Instant.ofEpochMilli(dates.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
                    timesArray.add(local_time);
                }
            }
            if (time.matches(END_TIME_VALIDATION_REGEX)) {
                end_time = timesArray.get(INDEX_START_TIME);
                setEndTime(end_time);
            }
            if (time.matches(START_TIME_VALIDATION_REGEX)) {
                start_time = timesArray.get(INDEX_START_TIME);
                setStartTime(start_time);
            }

        }
        else {
            extractTime(trimmedTime);
        }
        
        if (end_time != null){
            value = getStartTime() + " - " + getEndTime();
        }else{
            value = getStartTime();
        }
        
    }


    public void setEndTime(LocalTime end_time2) {
        if (end_time2 == null) {

            LocalTime localtime = LocalTime.MAX;
            end_time_string =  "";
            end_time = end_time2;
            value = getStartTime();

        }
        else {
            end_time_string = end_time2.format(TIME_FORMAT).toString();
            end_time = end_time2;
            if (end_time != null){
                value = getStartTime() + " - " + getEndTime();
            }else{
                value = getStartTime();
            }            

        }
    }

    public void setStartTime(LocalTime start_time2) {
        if (start_time2 == null) {

            start_time_string =  "";
            start_time = start_time2;
            value = getStartTime();
            
        }
        else {
            start_time_string = start_time2.format(TIME_FORMAT).toString();
            start_time = start_time2;
            if (end_time != null){
                value = getStartTime() + " - " + getEndTime();
            }else{
                value = getStartTime();
            }
        }
    }

    public void extractTime(String time)throws IllegalValueException {
        List<DateGroup> dateGroups = parser.parse(time);
 
        if (!dateGroups.isEmpty()) {
            for (Date dates : dateGroups.get(FIRST_INDEX_OF_ARRAY).getDates()) {
                local_time = Instant.ofEpochMilli(dates.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
                timesArray.add(local_time);
            }
        }
        else {
            setStartTime(null);
            setEndTime(null);
        }
        if (timesArray.size() == 2) {
            start_time = timesArray.get(INDEX_START_TIME);
            end_time = timesArray.get(INDEX_END_TIME);
            setStartTime(start_time);
            setEndTime(end_time);
            isRange = true;
        }
        else if (timesArray.size() == 1) {
            start_time = timesArray.get(INDEX_START_TIME);
            setStartTime(start_time);
            setEndTime(null);
            isDeadline = true;

        }
        else {
            setStartTime(null);
            setEndTime(null);
            isFloating = true;
        }


    }
    
    public String getValue(){
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public String getEndTime() {

        return end_time_string;

    }
    public String getStartTime() {

        return start_time_string;

    }
    
    public LocalTime getLocalEndTime() {
        return end_time;
    }
    
    public LocalTime getLocalStartTime() {
        return start_time;
    }

    /**
     * Returns true if a given date is an empty string or invalid string
     */
    public boolean isFloating() {
        return isFloating;
    }
    
    /**
     * Sets the time to floating
     */
    public void setFloating(){
        isFloating = true;
        isDeadline = false;
        isRange = false;
    }

    /**
     * Returns true if a given date only has a start time
     */
    public boolean isDeadline() {
        return isDeadline;
    }
    
    /**
     * Sets the time to a deadline
     */
    public void setDeadline(){
        isFloating = false;
        isDeadline = true;
        isRange = false;
    }

    /**
     * Returns true if a given date has both start and end time
     */
    public boolean isRange() {
        return isRange;
    }

    /**
     * Toggles the time to a range type
     */
    public void setRange(){
        isFloating = false;
        isDeadline = false;
        isRange = true;
    }
    /**
     * Returns true if a given string is a valid time format.
     */
    public static boolean isValidTime(String test) {
        return test.matches(START_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {

        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DueTime
                        && this.value.equals(((DueTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
