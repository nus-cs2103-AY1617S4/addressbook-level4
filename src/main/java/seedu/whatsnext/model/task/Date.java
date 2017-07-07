package seedu.whatsnext.model.task;

import seedu.whatsnext.commons.exceptions.IllegalValueException;

/**
 * Represents the Date of an AdvancedTask in WhatsNext
 * Time is represented in a 24 hour format
 * @@author A0156106M
 * */
public class Date {
    private String dateValue;
    public static final String DATE_VALIDATION_REGEX = "\\d{2}/\\d{2}/\\d{2}";
    public static final String EMPTY_FIELD = "EMPTY_FIELD";
    public static final String MESSAGE_DATE_CONSTRAINT = "Task date should be either "
            + "a day (e.g. friday) or a date with the format: DD/MM/YY (e.g. 06/07/17)\n";

    public Date(String dateInput) throws IllegalValueException{
        assert(dateInput != null);
        String dateInputTrim = dateInput.trim();
        if(!isValidDate(dateInputTrim)){
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINT);
        }
        dateValue = dateInputTrim;

    }

    private boolean isValidDate(String dateInputTrim){
        return dateInputTrim.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString(){
        return dateValue;
    }

}
