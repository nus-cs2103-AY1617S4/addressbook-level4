package seedu.whatsnext.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import seedu.whatsnext.commons.core.UnmodifiableObservableList;
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
    private TaskDescription taskDescription;
    private boolean isCompleted;
    private UniqueTagList tags;


    /**
     * Constructor for Floating
     * Deadline consists of Name, End Date, End Time and tags
     * @throws IllegalValueException
     * */

    /**
     * Constructor for Floating
     * @throws IllegalValueException
     * */
    public BasicTask(TaskName taskName, TaskDescription taskDescription,
            boolean isCompleted, Set<Tag> tags) throws IllegalValueException {
        this (taskName, taskDescription, isCompleted, new DateTime(), new DateTime(), tags);
    }

    public BasicTask(TaskName taskName, TaskDescription taskDescription, Set<Tag> tags) throws IllegalValueException {
        this (taskName, taskDescription, false, new DateTime(), new DateTime(), tags);
    }

    public BasicTask(TaskName taskName, Set<Tag> tags) throws IllegalValueException {
        this (taskName, new TaskDescription(), false, new DateTime(), new DateTime(), tags);
    }

    /**
     * Constructor for Deadline
     * @throws IllegalValueException
     * */
    public BasicTask(TaskName taskName, TaskDescription taskDescription, DateTime endDateTime, Set<Tag> tags)
            throws IllegalValueException {
        this (taskName, taskDescription, false, new DateTime(), endDateTime, tags);
    }

    public BasicTask(TaskName taskName, TaskDescription taskDescription,
            boolean isCompleted, DateTime endDateTime, Set<Tag> tags)
            throws IllegalValueException {
        this (taskName, taskDescription, isCompleted, new DateTime(), endDateTime, tags);
    }

    public BasicTask(TaskName taskName, TaskDescription taskDescription,
            DateTime startDateTime, DateTime endDateTime, Set<Tag> tags)
            throws IllegalValueException {
        this (taskName, taskDescription, false, startDateTime, endDateTime, tags);
    }

    /**
     * Constructor for Event
     * @throws IllegalValueException
     * */
    public BasicTask(TaskName taskName, TaskDescription taskDescription, boolean isCompleted,
            DateTime startDateTime, DateTime endDateTime, Set<Tag> tags) {
        //assert (startDateTime.isEmpty() && !endDateTime.isEmpty());
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.tags = new UniqueTagList(tags);
        this.isCompleted = isCompleted;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        setTaskType();
    }

    public BasicTask(BasicTaskFeatures source) {
        this (source.getName(), source.getDescription(), source.getIsCompleted(),
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

    @Override
    public boolean eventTaskOverlap(BasicTaskFeatures task) {
        return (this.getTaskType().equals(TASK_TYPE_EVENT) && task.getTaskType().equals(TASK_TYPE_EVENT)
                && this.getStartDateTime().isBeforeOrEqual(task.getEndDateTime())
                && task.getStartDateTime().isBeforeOrEqual(this.getEndDateTime()));
    }

    public void setName(TaskName name) {
        this.taskName = requireNonNull(name);
    }

    public void setDescription(TaskDescription description) {
        this.taskDescription = requireNonNull(description);
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
     * Replaces this task's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.setTags(new UniqueTagList(replacement));
    }

    /**
     * Updates this task with the details of {@code replacement}.
     */
    public void resetData(BasicTaskFeatures replacement) {
        requireNonNull(replacement);
        this.setName(replacement.getName());
        this.setTags(replacement.getTags());
        this.setDescription(replacement.getDescription());
        this.isCompleted = (replacement.getIsCompleted());
        this.startDateTime = (replacement.getStartDateTime());
        this.endDateTime = (replacement.getEndDateTime());
        setTaskType();
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

    @Override
    public TaskDescription getDescription() {
        return taskDescription;
    }

    public static int getOverlapTaskIndex(BasicTaskFeatures taskToEdit,
            UnmodifiableObservableList<BasicTaskFeatures> taskList) {
        int index = 0;
        for (BasicTaskFeatures task : taskList) {
            if (taskToEdit.eventTaskOverlap(task) && (!taskToEdit.equals(task))) {
                return index;
            }
        }
        return -1;
    }

    public boolean isOverlapTask(UnmodifiableObservableList<BasicTaskFeatures> taskList) throws IllegalValueException  {
        for (BasicTaskFeatures task : taskList) {
            if (this.eventTaskOverlap(task) && (!this.equals(task))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTaskDetails() {
        StringBuilder details = new StringBuilder();
        if (this.getTaskType().equals("event")) {
            details.append("Task name: " + this.getName() + "\n"
                + "Tags: " + this.getAllTags() + "\n"
                + "Status: " + this.getStatusString() + "\n"
                + "From: " + this.getStartDateTime().toString() + " "
                + "To: " + this.getEndDateTime().toString() + "\n"
                + "Description: " + this.getDescription().toString());
        } else if (this.getTaskType().equals("deadline")) {
            details.append("Task name: " + this.getName() + "\n"
                    + "Tags: " + this.getAllTags() + "\n"
                    + "Status: " + this.getStatusString() + "\n"
                    + "Due by: " + this.getEndDateTime().toString() + "\n"
                    + "Description: " + this.getDescription().toString());
        } else if (this.getTaskType().equals("floating")) {
            details.append("Task name: " + this.getName() + "\n"
                    + "Tags: " + this.getAllTags() + "\n"
                    + "Status: " + this.getStatusString() + "\n"
                    + "Description: " + this.getDescription().toString());
        }
        return details.toString();
    }

}
