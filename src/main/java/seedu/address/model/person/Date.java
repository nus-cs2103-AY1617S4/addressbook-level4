package seedu.address.model.person;


import static java.util.Objects.requireNonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should be in format DD/MM/YY";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATE_VALIDATION_REGEX = "(\\d{2}+)(/)(\\d{2}+)(/)(\\d{2}+)";
    public static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(DATE_VALIDATION_REGEX);

    public String value;
    private int dateNum;
    private int month;
    private int year;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Date(String date) throws IllegalValueException {
        requireNonNull(date);
        
        Matcher m = BASIC_COMMAND_FORMAT.matcher(date);
        
        if (!isValidDate(date) || !m.matches()) {
        	throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        
        else {
   
            dateNum = Integer.parseInt(m.group(1));
            month = Integer.parseInt(m.group(3));
            year = Integer.parseInt(m.group(5));
            
            value = dateNum + "/" + month + "/" + year;
        }    
    }
    
    public int getDate() {
    	return dateNum;
    }
    
    public int getMonth() {
    	return month;
    }
    
    public int getYear() {
    	return year;
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
                || (other instanceof Date // instanceof handles nulls
                && this.value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
