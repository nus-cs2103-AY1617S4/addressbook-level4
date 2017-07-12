package seedu.ticktask.model.task;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
            "Time is not a valid time";
    public static final String START_TIME_VALIDATION_REGEX = "start time.*";
    public static final String END_TIME_VALIDATION_REGEX = "end time.*";
	private static final String MESSAGE_END_TIME_CONSTRAINTS = "End time does not exist";
    
    private final Parser parser = new Parser();
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mma");
	//private final SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mma");
	private final int FIRST_INDEX_OF_ARRAY = 0;
	private final int INDEX_START_TIME = 0;
	private final int INDEX_END_TIME = 1;
	
    public String value;
    LocalTime local_time;
    
    private ArrayList<LocalTime> timesArray = new ArrayList<LocalTime>();
    private LocalTime start_time;
	private LocalTime end_time;
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
    public DueTime(String time) throws IllegalValueException {
        requireNonNull(time);
        String trimmedTime = time.trim();

        if(((time.matches(END_TIME_VALIDATION_REGEX)) || (time.matches(START_TIME_VALIDATION_REGEX)))){
        	List<DateGroup> dateGroups = parser.parse(time);
            /*if(dateGroups.isEmpty()){
            	throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
            }*/
            if(!dateGroups.isEmpty()){
    	        for (Date dates : dateGroups.get(FIRST_INDEX_OF_ARRAY).getDates()) {
       	        	local_time = Instant.ofEpochMilli(dates.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
    				timesArray.add(local_time);
    			}
            }
	        if (time.matches(END_TIME_VALIDATION_REGEX)){
	            System.out.println("This SHOILD run" + time);
	        	end_time = timesArray.get(INDEX_START_TIME);
	        	setEndTime(end_time);
	        }
	        if (time.matches(START_TIME_VALIDATION_REGEX)){
	            System.out.println("This should not run" + time);
	        	start_time = timesArray.get(INDEX_START_TIME);
	        	setStartTime(start_time);
	        }
        	
        }
        else{
        	extractTime(trimmedTime);
        }
        
        value = getStartTime() + " - " + getEndTime();
    }
    
    
    public void setEndTime(LocalTime end_time2){
    	if(end_time2 ==null){
    		//LocalTime localtime = LocalTime.MAX;
    		//end_time_string =  localtime.format(TIME_FORMAT).toString();
    		LocalTime localtime = LocalTime.MAX;
    		end_time_string =  "";
    	}
    	else{
    		end_time_string = end_time2.format(TIME_FORMAT).toString();
    	}
    }
    
    public void setStartTime(LocalTime start_time2){
    	if(start_time2 ==null){
    		//LocalTime localtime = LocalTime.MAX;
    		//start_time_string =  localtime.format(TIME_FORMAT).toString();
    		start_time_string =  "";
    	}
    	else{
    		start_time_string = start_time2.format(TIME_FORMAT).toString();
    	}
    }
    
    public void extractTime(String time)throws IllegalValueException{
        List<DateGroup> dateGroups = parser.parse(time);
       /* if(dateGroups.isEmpty()){
        	throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }*/
        if(!dateGroups.isEmpty()){
	        for (Date dates : dateGroups.get(FIRST_INDEX_OF_ARRAY).getDates()) {
   	        	local_time = Instant.ofEpochMilli(dates.getTime()).atZone(ZoneId.systemDefault()).toLocalTime();
				timesArray.add(local_time);
			}
        }
        else{
        	setStartTime(null);
        	setEndTime(null);
        }
        if(timesArray.size()==2){
        	start_time = timesArray.get(INDEX_START_TIME);
        	end_time = timesArray.get(INDEX_END_TIME);
        	setStartTime(start_time);
        	setEndTime(end_time);
        	isRange = true;
        }
        else if(timesArray.size()==1){
        	start_time = timesArray.get(INDEX_START_TIME);
        	setStartTime(start_time);
        	setEndTime(null);
        	isDeadline = true;
        	
        }
        else{
        	setStartTime(null);
        	setEndTime(null);
        	isFloating = true;
        }
        
        
    }
    
    public String getEndTime(){

    	return end_time_string;
    	
    }
   public String getStartTime(){

    	return start_time_string;
    	
    }
    
    /**
     * Returns true if a given date is an empty string or invalid string
     */    
    public boolean isFloating(){
		return isFloating;
    }
    
    /**
     * Returns true if a given date only has a start time
     */    
    public boolean isDeadline(){
		return isDeadline;
    }
    
    /**
     * Returns true if a given date has both start and end time
     */    
    public boolean isRange(){
		return isRange;
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
