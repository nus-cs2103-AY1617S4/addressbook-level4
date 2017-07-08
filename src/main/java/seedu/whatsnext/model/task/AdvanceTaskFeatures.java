package seedu.whatsnext.model.task;

public interface AdvanceTaskFeatures {
    DateTime getDate();
    Time getEndTime();
    void setEndTime(Time taskTime);
    void setDate(DateTime taskDate);

}
