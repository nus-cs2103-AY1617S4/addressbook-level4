package seedu.ticktask.model.task;

import static java.util.Objects.requireNonNull;
import static java.util.concurrent.TimeUnit.DAYS;
import static seedu.ticktask.commons.util.CollectionUtil.requireAllNonNull;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
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
    private TaskType type;
    private DueDate date;
    private boolean completed;
    
    private UniqueTagList tags;
    
    /**
     * Every field must be present and not null.
     */
    
    public Task(Name name, DueTime time, TaskType type, DueDate date, Set<Tag> tags) {
        requireAllNonNull(name, time, type, date, tags);
        
        this.name = name;
        this.time = time;
        this.type = type;
        this.date = date;
        this.completed = false;
        
        this.tags = new UniqueTagList(tags); // protect internal tags from changes in the arg list
    }
    
    /**
     * Creates a copy of the given ReadOnlyTask.
     */
    public Task(ReadOnlyTask source) {
        this(source.getName(), source.getTime(), source.getTaskType(), source.getDate(),
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
    
    public void setTaskType(TaskType type) {
        this.type = requireNonNull(type);
    }
    //@@author A0139819N
    
    /**
     * Resets the task type based on the due date and due time of the task object
     */
    public void resetTaskType() {
        if (time.isRange() || date.isRange()) {
            type.setValue(TaskType.TASK_TYPE_EVENT);
            if (time.getStartTime().equals("")) time.setStartTime(LocalTime.parse("00:00"));
            if (date.getStartDate().equals("")) date.setStartDate(LocalDate.now());
            if (time.getEndTime().equals("")) time.setEndTime(LocalTime.parse("23:59"));
            if (date.getEndDate().equals("")) date.setEndDate(LocalDate.now());
        } else if (time.isFloating() && date.isFloating()) {
            type.setValue(TaskType.TASK_TYPE_FLOATING);
        } else {
            type.setValue(TaskType.TASK_TYPE_DEADLINE);
            if (time.getStartTime().equals("")) time.setStartTime(LocalTime.parse("23:59"));
            if (date.getStartDate().equals("")) date.setStartDate(LocalDate.now());
            
            if (!date.getEndDate().equals("")) date.setEndDate(null);
            if (!time.getEndTime().equals("")) time.setEndTime(null);
            
        }
    }
    //@@author A0139819N
    
    @Override
    public TaskType getTaskType() {
        return type;
    }
    
    public void setDate(DueDate date) {
        this.date = requireNonNull(date);
    }
    
    @Override
    public DueDate getDate() {
        return date;
    }
    
    public boolean getCompleted() {
        return this.completed;
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
        this.setTaskType(replacement.getTaskType());
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
        return Objects.hash(name, time, type, date, tags);
    }
    
    @Override
    public String toString() {
        return getAsText();
    }
    
    //@@author A0139964M
    /**
     * Checks if the task that is to be added is in the past in reference to current time & date.
     * @return boolean
     */
    public boolean isChornological() {
        LocalDate currDate = LocalDate.now();
        
        if(this.getDate().getLocalStartDate() == null){
            if(this.getTime().getLocalStartTime() == null || isTimeChornological()){
                return true;
            }
            else return false;
        }
        LocalDate taskDate = this.getDate().getLocalStartDate();
        //Check if task's date is today.
        if(taskDate.isEqual(currDate)){
            if(this.getTime().getLocalStartTime() == null|| isTimeChornological()){
                return true;
            } else {
                return false;
            }
        }
        if(isDateChornological()){
            return true;
        } else{
            return false;
        }
    }
    
    /**
     * Method to check if task's time is in the future relative to current time
     */
    public boolean isTimeChornological() {
        LocalTime currTime = LocalTime.now();
        LocalTime taskTime = this.getTime().getLocalStartTime();
        if (taskTime.isBefore(currTime)) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Method to check if task's date is in the future relative to current date
     */
    public boolean isDateChornological(){
        LocalDate currDate = LocalDate.now();
        LocalDate taskDate = this.getDate().getLocalStartDate();
        if(taskDate.isBefore(currDate)){
            return false;
        } else {
            return true;
        }
    }
    
    /**
     *Check if the current task's date is due. Applies to only deadline & event
     *true if and only if current date is after start date
     */
    public boolean isDateDue() {
        LocalDate now = LocalDate.now();
        LocalDate taskDate = date.getLocalStartDate();
        if (now.isAfter(taskDate)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     *Check if the current task's time is due. Applies to only deadline & event
     *true if and only if current time is after start time
     */
    public boolean isTimeDue() {
        LocalTime now = LocalTime.now();
        LocalTime taskTime = time.getLocalStartTime();
        if (now.isAfter(taskTime)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     *Get the time in hours if time is less than 1 day (24 hours)
     *Applies to only deadline and events
     */
    public Duration getDueDurationTime() {
        LocalTime now = LocalTime.now();
        LocalTime startTime = time.getLocalStartTime();
        return Duration.between(now, startTime);
    }
    
    /**
     *Get the time in days if days is >= 1
     *Applies to only deadline and events
     */
    public long getDueDateDuration() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = date.getLocalStartDate();
        long daysBetween = ChronoUnit.DAYS.between(now, startDate);
        return daysBetween;
    }
    
    /**
     *Check if the event is happening now.
     *Applies to only events
     */
    public boolean isHappening() {
        LocalDate nowDate = LocalDate.now();
        LocalDate startDate = date.getLocalStartDate();
        LocalTime nowTime = LocalTime.now();
        LocalTime startTime = time.getLocalStartTime();
        LocalTime endTime = time.getLocalEndTime();
        LocalDate endDate = date.getLocalEndDate();
        if(nowTime.isAfter(startTime) && nowTime.isBefore(endTime)){
            if(nowDate.isEqual(startDate) || (nowDate.isAfter(startDate) && nowDate.isBefore(endDate))){
                return true;
            } else {
                return false;
            }
        } else{
            return false;
        }
    }
    
    /**
     *Check if date is equals today date
     *Applies to only events and deadlines
     */
    public boolean isToday(){
        LocalDate nowDate = LocalDate.now();
        LocalDate startDate = date.getLocalStartDate();
        if(nowDate.isEqual(startDate)){
            return true;
        } else{
            return  false;
        }
    }
}
//@@author
