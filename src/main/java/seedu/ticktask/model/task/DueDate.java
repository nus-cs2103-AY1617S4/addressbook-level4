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

/**
 * Represents a Task's date in the TickTask.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DueDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in format DD/MM/YY";

    /*
     * The first character of the date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "(\\d{2}+)(/)(\\d{2}+)(/)(\\d{2}+)";
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(DATE_VALIDATION_REGEX);

    private String value;
    
    private  ArrayList<Date> datesAndTimes = new ArrayList<Date>();
    private Date start_date;
	private Date end_date;
	private String start_date_string = "", 
			end_date_string = "";
	private static Parser parser = new Parser();
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private boolean isFloating = false;
	private boolean isRange = false;
	private boolean isDeadline = false;
	
   /* private int dateNum;
    private int month;
    private int year;*/

    /**
     * Validates given date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public DueDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
       // Matcher m = BASIC_COMMAND_FORMAT.matcher(date);
        
        if (!date.equals(" ")) {
            /*if (!isValidDate(date) || !m.matches()) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }*/
        }
        
        List<DateGroup> dateGroups = parser.parse(trimmedDate);
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
        	
        	start_date_string = "";
        	end_date_string = "";
        	
            value = new StringBuilder("").toString();

        }
        
        else if (isDeadline){
        	
	        start_date = datesAndTimes.get(0);
	        start_date_string = dateFormatter.format(start_date);
	        end_date_string = "";
	        
	        value = new StringBuilder(start_date_string).toString();

        }
        
        else if (isRange){
        	
        	Collections.sort(datesAndTimes);
			
			start_date = datesAndTimes.get(0);
			end_date = datesAndTimes.get(1);
			
			start_date_string = dateFormatter.format(start_date);
			end_date_string = dateFormatter.format(end_date);
			
	        value = new StringBuilder(start_date_string + " - " + end_date_string).toString();

        }
        
    }
    
    /**
     * Returns true if a given string is a valid person email.
     */
    
    
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
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
