package seedu.whatsnext.model.task;

import java.util.Set;

import seedu.whatsnext.model.tag.Tag;

/**
 * A read-only immutable interface for a task in the WhatsNext application.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface BasicTaskFeatures {

    TaskName getName();
    Set<Tag> getTags();
    boolean getIsCompleted();
    void setCompleted();
    void setIncompleted();
    DateTime getStartDateTime();
    DateTime getEndDateTime();


    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(BasicTaskFeatures other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName())); // state checks here onwards
    }

    /**
     * Formats the task as text, showing all contact details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
               .append(" Tags: ");
        builder.append(getAllTags());
        return builder.toString();
    }

    default String getAllTags(){
    	final StringBuilder builder = new StringBuilder();
    	getTags().forEach(builder::append);
    	return builder.toString();

    }

}
