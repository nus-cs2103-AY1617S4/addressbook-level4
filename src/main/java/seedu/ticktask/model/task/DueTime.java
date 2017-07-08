package seedu.ticktask.model.task;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import seedu.ticktask.commons.exceptions.IllegalValueException;

//@@author A0138471A
/**
 * Represents a task's deadline time.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class DueTime {
    
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Time should be in a 24 hours format HHMM";
    public static final String PHONE_VALIDATION_REGEX = "(\\d{2}+)(\\d{2}+)";
    
    private final Parser parser = new Parser();
	private final SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mma");
	private final int FIRST_INDEX_OF_ARRAY = 0;
	private final int INDEX_START_DATE = 0;
	private final int INDEX_END_DATE = 1;
	
    private String value;
    
    private ArrayList<Date> datesAndTimes = new ArrayList<Date>();
    private Date start_time;
	private Date end_time;
	private String start_time_string = "", 
			end_time_string = "";
	
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

        List<DateGroup> dateGroups = parser.parse(trimmedTime);
        if(!dateGroups.isEmpty()){
	        for (Date dates : dateGroups.get(FIRST_INDEX_OF_ARRAY).getDates()) {
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
        	
        	//sorts according to earlier time first
        	Collections.sort(datesAndTimes);
        	
			start_time = datesAndTimes.get(INDEX_START_DATE);
			end_time = datesAndTimes.get(INDEX_END_DATE);
			
			start_time_string = timeFormatter.format(start_time);
			end_time_string = timeFormatter.format(end_time);
			
	        value = new StringBuilder(start_time_string + " - " + end_time_string).toString();

        }
        
    }
    
    /**
     * Returns true if a given date is an empty string or invalid string
     */    
    public boolean isFloating(){
		return isFloating;
    }
    
    /**
     * Returns true if a given date only has a start date
     */    
    public boolean isDeadline(){
		return isDeadline;
    }
    
    /**
     * Returns true if a given date has both start and end date
     */    
    public boolean isRange(){
		return isRange;
    }

    /**
     * Returns true if a given string is a valid time format.
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
