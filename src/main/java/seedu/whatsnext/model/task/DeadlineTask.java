package seedu.whatsnext.model.task;

import java.util.Set;

import seedu.whatsnext.model.tag.Tag;

/**
 * Represents a Deadline Task in the WhatsNext application.
 * Deadline Tasks are able to store task name, task description, tags,
 * task date and task time which represents the ideal date of completion of the task
 * Guarantees: details are present and not null, field values are validated.
 *
 * @@author: A0156106M
 */

public class DeadlineTask extends BasicTask implements AdvanceTaskFeatures{
    public static final String TASK_TYPE = "deadline";
    private Date taskDate;
    private Time taskTime;

    public DeadlineTask(TaskName taskName, Date taskDate, Time taskTime, Set<Tag> tags) {
        super(taskName, tags);
        super.taskType = TASK_TYPE;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
    }

    /**
     * Creates a copy of the given Deadline Task
     * */
    public DeadlineTask(DeadlineTask source) {
        this(source.getName(), source.getDate(), source.getEndTime(), source.getTags());
    }

    @Override
    public Date getDate() {
        return taskDate;
    }

    @Override
    public Time getEndTime() {
        return taskTime;
    }

    @Override
    public void setEndTime(Time taskTime) {
        this.taskTime = taskTime;
    }

    @Override
    public void setDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    @Override
    public void setTaskType(String taskType){
        super.taskType = taskType;
    }


}
