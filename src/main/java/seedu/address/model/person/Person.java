package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Person implements ReadOnlyPerson {

    private Name name;
    private Time time;
    private Email email;
    private Date address;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */

    public Person(Name name, Time time, Email email, Date address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);

        this.name = name;
        this.time = time;
        this.email = email;
        this.address = address;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given ReadOnlyPerson.
     */
    public Person(ReadOnlyPerson source) {
        this(source.getName(), source.getTime(), source.getEmail(), source.getAddress(),
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
    public Date getAddress() {
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
    public void resetData(ReadOnlyPerson replacement) {
        requireNonNull(replacement);

        this.setName(replacement.getName());
        this.setTime(replacement.getTime());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyPerson // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyPerson) other));
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
