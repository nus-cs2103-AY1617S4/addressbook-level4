package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Task implements ReadOnlyTask {

    private Name name;
    private Time time;
    private Email email;
    private Date address;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */

    public Task(Name name, Time time, Email email, Date address, Set<Tag> tags) {
        requireAllNonNull(name, time, email, address, tags);

        this.name = name;
        this.time = time;
        this.email = email;
        this.address = address;
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

    public void setTime(Time time) {
        this.time = requireNonNull(time);
    }

    public Time getTime() {
        return time;
    }

    public void setEmail(Email email) {
        this.email = requireNonNull(email);
    }

    @Override
    public Email getEmail() {
        return email;
    }

    public void setAddress(Date address) {
        this.address = requireNonNull(address);
    }

    @Override
    public Date getDate() {
        return address;
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
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.setTags(new UniqueTagList(replacement));
    }

    /**
     * Updates this person with the details of {@code replacement}.
     */
    public void resetData(ReadOnlyTask replacement) {
        requireNonNull(replacement);

        this.setName(replacement.getName());
        this.setTime(replacement.getTime());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getDate());
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
        return Objects.hash(name, time, email, address, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

}
