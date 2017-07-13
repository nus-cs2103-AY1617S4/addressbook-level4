package teamthree.twodo.model.task;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import teamthree.twodo.commons.core.Config;
import teamthree.twodo.commons.exceptions.IllegalValueException;

/**
 * Represents a Task's deadline in the address book. Consists of start, end and
 * notification times. If task only has start time, end time will be = start
 * time. Notification time = start time + notification period (either default or
 * user provided)
 */
public class Deadline {

    public static final String MESSAGE_DEADLINE_CONSTRAINTS_STRICT = "Deadlines can be informal, e.g. fri 10am, "
            + "but if providing exact dates, " + "they should be of the format MM/DD/YY"
            + " and Time can be either AM/PM or 24HR\n";

    // This value is only to be used by edit command to indicate a change of date
    public static final String NULL_VALUE = "0000";
    public static final Date DEFAULT_DATE = new Date(0);

    private static final long DAY_TO_MILLIS = 1000 * 60 * 60 * 24;
    private static final long WEEK_TO_MILLIS = DAY_TO_MILLIS * 7;
    // Java Date contains both time and date so don't need separate Time object.
    private Long notificationPeriod = Config.getDefaultNotificationPeriod();
    private Date startDate;
    private Date endDate;

    public Deadline() {

    }

    public Deadline(String startDate, String endDate, String notificationPeriod) throws IllegalValueException {
        requireNonNull(startDate.toString());
        requireNonNull(endDate.toString());
        requireNonNull(notificationPeriod.toString());
        PrettyTimeParser dateParser = new PrettyTimeParser();
        if (!isValidDeadline(startDate, endDate, dateParser)) {
            throw new IllegalValueException(MESSAGE_DEADLINE_CONSTRAINTS_STRICT);
        }

        this.startDate = !startDate.equals(NULL_VALUE) ? dateParser.parseSyntax(startDate).get(0).getDates().get(0)
                : DEFAULT_DATE;
        this.endDate = !endDate.equals(NULL_VALUE) ? dateParser.parseSyntax(endDate).get(0).getDates().get(0)
                : DEFAULT_DATE;
        this.notificationPeriod = !notificationPeriod.equals(NULL_VALUE) ? parseNotificationPeriod(notificationPeriod)
                : Config.getDefaultNotificationPeriod();
    }

    public Deadline(Date startDate, Date endDate, Long notificationPeriod) {
        requireNonNull(startDate.toString());
        requireNonNull(endDate.toString());
        requireNonNull(notificationPeriod.toString());

        this.startDate = startDate;
        this.endDate = endDate;
        this.notificationPeriod = notificationPeriod;
    }

    public Deadline(Deadline deadline) {
        startDate = deadline.getStartDate();
        endDate = deadline.getEndDate();
        notificationPeriod = deadline.getNotificationPeriod();
    }

    private boolean isValidDeadline(String startDate, String endDate, PrettyTimeParser dateParser) {
        if (dateParser.parseSyntax(startDate).isEmpty() || dateParser.parseSyntax(endDate).isEmpty()) {
            return false;
        }
        return true;
    }

    public Long getNotificationPeriod() {
        return notificationPeriod;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getNotificationDate() {
        return new Date(startDate.getTime() - notificationPeriod);
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setNotificationPeriod(Long notificationPeriod) {
        this.notificationPeriod = notificationPeriod;
    }

    public void setNotificationPeriod(String notificationPeriod) {

    }

    /**
     * Gets notification period from user-given arguments Will only accept days
     * and weeks else will return default notification period
     *
     * @param notificationPeriod
     * @return Long time in milliseconds
     */
    private Long parseNotificationPeriod(String notificationPeriod) {
        Matcher integerParser = Pattern.compile("\\d*").matcher(notificationPeriod);
        assert (integerParser.find());
        integerParser.find();
        int period = Integer.parseInt(integerParser.group());
        if (notificationPeriod.toLowerCase().contains("day")) {
            return DAY_TO_MILLIS * period;
        } else if (notificationPeriod.toLowerCase().contains("week")) {
            return WEEK_TO_MILLIS * period;
        }
        return Config.getDefaultNotificationPeriod();
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    /*
     * public static boolean isValidDeadline(String test) { return
     * test.matches(PHONE_VALIDATION_REGEX); }
     */

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        if (startDate.equals(endDate)) {
            return "Starts: " + dateFormat.format(startDate) + "\nReminder on: "
                    + dateFormat.format(getNotificationDate()) + "\n";
        }
        return "Starts: " + dateFormat.format(startDate) + "\nEnds: " + dateFormat.format(endDate) + "\nReminder on: "
                + dateFormat.format(getNotificationDate()) + "\n";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                        && this.toString().equals(((Deadline) other).toString())); // state check
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

}
