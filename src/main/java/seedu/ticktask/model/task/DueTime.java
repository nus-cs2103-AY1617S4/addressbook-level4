package seedu.ticktask.model.task;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import seedu.ticktask.commons.exceptions.IllegalValueException;

/**
 * Represents a task's deadline time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class DueTime {

    private int _hours;
    private int _minutes;
    
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should be in a 24 hours format HHMM";
    public static final String PHONE_VALIDATION_REGEX = "(\\d{2}+)(\\d{2}+)";
    
    private String value;
    
    private  ArrayList<Date> datesAndTimes = new ArrayList<Date>();
    private Date start_time;
	private Date end_time;
	private String start_time_string = "", 
			end_time_string = "";
	private static Parser parser = new Parser();
	private SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mma");
    private boolean isFloating = false;
	private boolean isRange = false;
	private boolean isDeadline = false;

    /**
     * Validates given time.
     *
     * @throws IllegalValueException if given time string is invalid.
     */
    public DueTime(String phone) throws IllegalValueException {
        requireNonNull(phone);
        String trimmedTime = phone.trim();
        /*if (!trimmedTime.equals("")) {
            if (!isValidTime(trimmedTime)) {
                throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
            }
        }*/

        List<DateGroup> dateGroups = parser.parse(trimmedTime);
        if(!dateGroups.isEmpty()){
	        for (Date dates : dateGroups.get(0).getDates()) {
				datesAndTimes.add(dates);
			}
	        if(datesAndTimes.size()>1){
	        	isRange = true;
	        }
	        else isDeadline = true;
        }
        else isFloating = true;
        
        
        if(isFloating){
        	
        	start_time_string = "";
        	end_time_string = "";
        	
            value = new StringBuilder("").toString();

        }
        
        else if (isDeadline){
        	
	        start_time = datesAndTimes.get(0);
	        start_time_string = timeFormatter.format(start_time);
	        end_time_string = "";
	        
	        value = new StringBuilder(start_time_string).toString();

        }
        
        else if (isRange){
        	
        	//Collections.sort(datesAndTimes);
			
			start_time = datesAndTimes.get(0);
			end_time = datesAndTimes.get(1);
			
			start_time_string = timeFormatter.format(start_time);
			end_time_string = timeFormatter.format(end_time);
			
	        value = new StringBuilder(start_time_string + " - " + end_time_string).toString();

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
                || (other instanceof DueTime
                && this.value.equals(((DueTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
