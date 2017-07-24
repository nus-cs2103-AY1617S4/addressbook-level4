package seedu.ticktask.testutil;

import java.time.LocalTime;
import java.util.Set;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.TaskType;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.model.util.SampleDataUtil;
/**
 * A utility class to help with building Task objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Wash dog";
    public static final String DEFAULT_TIME = "0000";
    public static final String DEFAULT_TASK_TYPE = "deadline";
    public static final String DEFAULT_DATE = "01/01/2020";
    public static final String DEFAULT_TAGS = "cleaning";
    public static final String PAST_TIME = LocalTime.now().minusSeconds(1).toString();
    public static final String FUTURE_TIME = LocalTime.now().plusSeconds(30).toString();
    public static final String PAST_DATE = "01/01/2001";
    public static final String FUTURE_DATE = "01/01/2019";
    

    private Task task;

    public TaskBuilder() throws IllegalValueException {
        Name defaultName = new Name(DEFAULT_NAME);
        DueTime defaultTime = new DueTime(DEFAULT_TIME);
        TaskType defaultTaskType = new TaskType(DEFAULT_TASK_TYPE);
        DueDate defaultDueDate = new DueDate(DEFAULT_DATE);
        Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        this.task = new Task(defaultName, defaultTime, defaultTaskType, defaultDueDate, defaultTags);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public TaskBuilder(ReadOnlyTask taskToCopy) {
        this.task = new Task(taskToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        this.task.setTags(SampleDataUtil.getTagSet(tags));
        return this;
    }

    public TaskBuilder withDate(String dueDate) throws IllegalValueException {
        this.task.setDate(new DueDate(dueDate));
        return this;
    }

    public TaskBuilder withTime(String time) throws IllegalValueException {
        this.task.setTime(new DueTime(time));
        return this;
    }

    public TaskBuilder withType(String type) throws IllegalValueException {
        this.task.setTaskType(new TaskType(type));

        return this;
    }

    public Task build() {
        return this.task;
    }

}
