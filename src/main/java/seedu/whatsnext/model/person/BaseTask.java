package seedu.whatsnext.model.person;

import java.util.Set;

import seedu.whatsnext.model.tag.Tag;

/**
 * A read-only immutable interface for a task in the WhatsNext application.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface BaseTask {

    Name getName();
    Set<Tag> getTags();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(BaseTask other) {
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
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
