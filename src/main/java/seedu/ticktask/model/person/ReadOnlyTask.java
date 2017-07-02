package seedu.ticktask.model.person;

import java.util.Set;

import seedu.ticktask.model.tag.Tag;

/**
 * A read-only immutable interface for a Task in  TickTask.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyTask {

    //These are methods
    Name getName();
    Time getTime();
    Email getEmail();
    Date getDate();
    Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyTask other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getTime().equals(this.getTime())
                && other.getEmail().equals(this.getEmail())
                && other.getDate().equals(this.getDate()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Time: ")
                .append(getTime())
                .append(" Email: ")
                .append(getEmail())
                .append(" Date: ")
                .append(getDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
