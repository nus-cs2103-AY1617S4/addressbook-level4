package seedu.whatsnext.model.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.whatsnext.commons.exceptions.IllegalValueException;

//@@author A0156106M
/**
 * Represents the Date of an AdvancedTask in WhatsNext
 * Time is represented in a 24 hour format
 * */
public class DateTime {
    public static final String INIT_DATEVALUE = "0001/01/01 00:00";
    public static final String MESSAGE_DATE_CONSTRAINT = "Task date should be either "
            + "a day (e.g. friday) or a date with the format: DD/MM/YY (e.g. 06/07/17)\n";

    private final DateFormat dateTimeFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private final DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private Date dateValue;

    public DateTime() throws IllegalValueException {
        this(INIT_DATEVALUE);
    }

    public DateTime(String dateInput) throws IllegalValueException {
        assert(dateInput != null);
        String dateInputTrim = dateInput.trim();
        if (dateInputTrim.equals(INIT_DATEVALUE)) {
            try {
                dateValue = dateTimeFormat.parse(INIT_DATEVALUE);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        } else {
            List<Date> dateInputList = new PrettyTimeParser().parse(dateInputTrim);

            if (!isValidDate(dateInputList)) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINT);
            }
            dateValue = dateInputList.get(0);
        }


    }

    private boolean isValidDate(List<Date> dateInputList) {
        return !dateInputList.isEmpty();
    }

    @Override
    public String toString() {
        return dateTimeFormat.format(dateValue);
    }

    public boolean isEmpty() {
        return toString().equals(INIT_DATEVALUE);
    }

}
