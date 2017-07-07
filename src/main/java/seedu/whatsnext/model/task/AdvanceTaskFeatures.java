package seedu.whatsnext.model.task;

public interface AdvanceTaskFeatures {
    Date getDate();
    Time getEndTime();
    void setEndTime(Time taskTime);
    void setDate(Date taskDate);

}
