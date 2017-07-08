package seedu.ticktask.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.ticktask.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.tag.UniqueTagList;

/**
 * Represents a Task in the TickTask program.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private DueTime time;
    private Email email;
    private DueDate date;
    private boolean completed;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */

    public Task(Name name, DueTime time, Email email, DueDate date, Set<Tag> tags) {
        requireAllNonNull(name, time, email, date, tags);

        this.name = name;
        this.time = time;
        this.email = email;
        this.date = date;
        this.completed = false;
        
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTime(), source.getEmail(), source.getDate(),
                source.getTags());
    }

    public void setName(Name name) {
        this.name = requireNonNull(name);
    }

    @Override
    public Name getName() {
        return name;
    }

    public void setTime(DueTime time) {
        this.time = requireNonNull(time);
    }

    public DueTime getTime() {
        return time;
    }

    public void setEmail(Email email) {
        this.email = requireNonNull(email);
    }

    @Override
    public Email getEmail() {
        return email;
    }

    public void setDate(DueDate date) {
        this.date = requireNonNull(date);
    }

    @Override
    public DueDate getDate() {
        return date;
    }
    
    public boolean getCompleted() {
    	return completed;
    }
    
    public void setCompleted(boolean newStatus) {
    	this.completed = newStatus;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    /**
     * Replaces this task's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.setTags(new UniqueTagList(replacement));
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        requireNonNull(replacement);

        this.setName(replacement.getName());
        this.setTime(replacement.getTime());
        this.setEmail(replacement.getEmail());
        this.setDate(replacement.getDate());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyTask // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, email, date, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
