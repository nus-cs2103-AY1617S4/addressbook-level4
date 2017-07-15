package seedu.whatsnext.testutil;

import java.util.Set;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_TASKNAME = "CS2103 Exam";
    public static final String DEFAULT_START_DATETIME = DateTime.INIT_DATETIME_VALUE;
    public static final String DEFAULT_END_DATETIME = DateTime.INIT_DATETIME_VALUE;
    public static final String DEFAULT_TAGS = "HIGH";

    private BasicTask task;

    public TaskBuilder() throws IllegalValueException {
        TaskName defaultName = new TaskName(DEFAULT_TASKNAME);
        TaskDescription defaultDescription = new TaskDescription(TaskDescription.INIT_DECRIPTION_VALUE);
        DateTime defaultStartDateTime = new DateTime();
        DateTime defaultEndDateTime = new DateTime();
        Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        this.task = new BasicTask(defaultName, defaultDescription,
                false, defaultStartDateTime, defaultEndDateTime, defaultTags);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(BasicTaskFeatures taskToCopy) {
        this.task = new BasicTask(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new TaskName(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        this.task.setTags(SampleDataUtil.getTagSet(tags));
        return this;
    }

    public TaskBuilder withStartDateTime(String startDateTime) throws IllegalValueException {
        this.task.setStartDateTime(new DateTime(startDateTime));
        return this;
    }

    public TaskBuilder withEndDateTime(String endDateTime) throws IllegalValueException {
        this.task.setEndDateTime(new DateTime(endDateTime));
        return this;
    }

    public TaskBuilder withStatus(boolean status) throws IllegalValueException {
        this.task.setCompleted();
        return this;
    }

    public TaskBuilder withDescription(String description) throws IllegalValueException {
        this.task.setDescription(new TaskDescription(description));
        return this;
    }

    public BasicTask build() {
        return this.task;
    }

}
