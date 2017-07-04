package seedu.whatsnext.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.tag.UniqueTagList;

/**
 * Represents a Task in the WhatsNext application.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Floating implements BaseTask {

    private TaskName name;
    private boolean isCompleted;

    private UniqueTagList tags;

    /**
     * Every field must be present and not null.
     */
    public Floating(TaskName name, Set<Tag> tags) {
        requireAllNonNull(name, tags);
        this.name = name;
        isCompleted = false;
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }

    /**
     * Creates a copy of the given BasicTask.
     */
    public Floating(BaseTask source) {
        this(source.getName(), source.getTags());
    }

    public void setName(TaskName name) {
        this.name = requireNonNull(name);
    }

    @Override
    public TaskName getName() {
        return name;
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
    public void resetData(BaseTask replacement) {
        requireNonNull(replacement);

        this.setName(replacement.getName());
        this.setTags(replacement.getTags());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BaseTask // instanceof handles nulls
                && this.isSameStateAs((BaseTask) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }

	@Override
	public boolean getIsCompleted() {
		return isCompleted;
	}

	@Override
	public void setCompleted() {
		isCompleted = true;

	}

	@Override
	public void setIncompleted() {
		isCompleted = false;

	}

}
