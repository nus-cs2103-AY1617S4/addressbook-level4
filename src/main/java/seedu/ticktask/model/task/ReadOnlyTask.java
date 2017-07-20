package seedu.ticktask.model.task;

import java.time.Duration;
import java.util.Set;

import seedu.ticktask.model.tag.Tag;

/**
 * A read-only immutable interface for a task in TickTask.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    //These are methods
    Name getName();
    DueTime getTime();
    TaskType getTaskType();
    void resetTaskType();
    DueDate getDate();
    boolean isDateDue();
    boolean getCompleted();
    Duration getDueDurationTime();
    long getDueDateDuration();
    boolean isHappening();
    boolean isTimeDue();
    boolean isToday();
    void setCompleted(boolean s);
    Set<Tag> getTags();
    boolean isChornological();
    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getTime().equals(this.getTime())
                && other.getTaskType().equals(this.getTaskType())
                && other.getDate().equals(this.getDate()));
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getTime())
                .append(" Task Type: ")
                .append(getTaskType())
                .append(" Date: ")
                .append(getDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
