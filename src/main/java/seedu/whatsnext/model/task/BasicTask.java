package seedu.whatsnext.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.tag.UniqueTagList;

/**
 * Represents a Basic Task in the WhatsNext application.
 * Basic Tasks are only able to store task name, task description and tags
 * Guarantees: details are present and not null, field values are validated.
 * @@author A0156106M
 */
public class BasicTask implements BasicTaskFeatures {
    public static final String TASK_TYPE = "basic";
    private TaskName taskName;
    private boolean isCompleted;
    @SuppressWarnings("unused")
    private DateTime startDateTime;
    @SuppressWarnings("unused")
    private DateTime endDateTime;

    private UniqueTagList tags;

    /**
     * Constructor for Floating
     * Floating consists of Name and tags
     */
    public BasicTask(TaskName taskName, Set<Tag> tags) {
        this(taskName, null, null, tags);
    }

    /**
     * Constructor for Deadline
     * Deadline consists of Name, End Date, End Time and tags
     * */
    public BasicTask(TaskName taskName, DateTime startDateTime, Set<Tag> tags) {
        this(taskName, startDateTime, null, tags);
    }

    /**
     * Constructor for Event
     * */
    public BasicTask(TaskName taskName, DateTime startDateTime, DateTime endDateTime, Set<Tag> tags) {
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.tags = new UniqueTagList(tags);
    }

    /**
     * Constructor for Event
     * */
    public BasicTask(TaskName taskName, DateTime startDateTime,
            DateTime endDateTime, boolean isCompleted, Set<Tag> tags) {
        this.taskName = taskName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.tags = new UniqueTagList(tags);
        this.isCompleted = isCompleted;
        System.out.println("VALUE IS " + isCompleted);
    }

    /**
     * Creates a copy of the given BasicTask.
     */
//    public BasicTask(BasicTaskFeatures source) {
//        this(source.getName(), source.getTags());
//    }

    /**
     * Creates a copy of the given BasicTask.
     */
    public BasicTask(BasicTaskFeatures source) {
        this(source.getName(), source.getStartDateTime(), source.getEndDateTime(), source.getIsCompleted(), source.getTags());

    }


    public void setName(TaskName name) {
        this.taskName = requireNonNull(name);
    }

    @Override
    public TaskName getName() {
        return taskName;
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
    public void resetData(BasicTaskFeatures replacement) {
        requireNonNull(replacement);

        this.setName(replacement.getName());
        this.setTags(replacement.getTags());
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BasicTaskFeatures // instanceof() handles nulls
                && this.isSameStateAs((BasicTaskFeatures) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(taskName, tags);
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

    @Override
    public DateTime getStartDateTime() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DateTime getEndDateTime() {
        // TODO Auto-generated method stub
        return null;
    }


}
