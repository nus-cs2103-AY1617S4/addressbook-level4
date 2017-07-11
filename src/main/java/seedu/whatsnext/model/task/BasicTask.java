package seedu.whatsnext.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.tag.UniqueTagList;
//@@author A0156106M
/**
 * Represents a Basic Task in the WhatsNext application.
 * Basic Tasks are only able to store task name, task description and tags
 * Guarantees: details are present and not null, field values are validated.
 */
public class BasicTask implements BasicTaskFeatures {
    public static final String TASK_TYPE_FLOATING = "floating";
    public static final String TASK_TYPE_DEADLINE = "deadline";
    public static final String TASK_TYPE_EVENT = "event";
    private DateTime startDateTime;
    private DateTime endDateTime;
    private String taskType;
    private TaskName taskName;
    private boolean isCompleted;
    private UniqueTagList tags;

    /**
     * Constructor for Floating
     * Deadline consists of Name, End Date, End Time and tags
     * @throws IllegalValueException
     * */
    public BasicTask(TaskName taskName, Set<Tag> tags) throws IllegalValueException {
        this(taskName, false, new DateTime(), new DateTime(), tags);

    }

    /**
     * Constructor for Floating
     * @throws IllegalValueException
     * */
    public BasicTask(TaskName taskName, boolean isCompleted, Set<Tag> tags) throws IllegalValueException {
        this (taskName, isCompleted, new DateTime(), new DateTime(), tags);
    }

    /**
     * Constructor for Deadline
     * @throws IllegalValueException
     * */
    public BasicTask(TaskName taskName, boolean isCompleted, DateTime endDateTime, Set<Tag> tags)
            throws IllegalValueException {
        this (taskName, isCompleted, new DateTime(), endDateTime, tags);
    }

    public BasicTask(TaskName taskName, DateTime endDateTime, Set<Tag> tags)
            throws IllegalValueException {
        this (taskName, false, new DateTime(), endDateTime, tags);
    }

    /**
     * Constructor for Event
     * */
    public BasicTask(TaskName taskName, boolean isCompleted,
            DateTime startDateTime, DateTime endDateTime, Set<Tag> tags) {
        assert (startDateTime.isEmpty() && !endDateTime.isEmpty());
        this.taskName = taskName;
        this.tags = new UniqueTagList(tags);
        this.isCompleted = isCompleted;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        setTaskType();

    }

    /**
     * Creates a copy of the given BasicTask.
     */
    public BasicTask(BasicTaskFeatures source) {
        this (source.getName(), source.getIsCompleted(),
                source.getStartDateTime(), source.getEndDateTime(), source.getTags());
    }

    public void setTaskType() {
        if (this.startDateTime.isEmpty() && this.endDateTime.isEmpty()) {
            taskType = TASK_TYPE_FLOATING;
        } else if (this.startDateTime.isEmpty() && !this.endDateTime.isEmpty()) {
            taskType = TASK_TYPE_DEADLINE;
        } else {
            taskType = TASK_TYPE_EVENT;
        }
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
        this.isCompleted = (replacement.getIsCompleted());
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
    public void setIncomplete() {
        isCompleted = false;
    }

    @Override
    public DateTime getStartDateTime() {
        return startDateTime;
    }

    @Override
    public DateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public String getTaskType() {
        return taskType;
    }

    @Override
    public String getStatusString() {
        if (getIsCompleted()) {
            return "Completed";
        } else {
            return "Incomplete";
        }
    }

    @Override
    public void setStartDateTime(DateTime dateTime) {
        startDateTime = dateTime;
        setTaskType();
    }

    @Override
    public void setEndDateTime(DateTime dateTime) {
        endDateTime = dateTime;
        setTaskType();
    }
}
