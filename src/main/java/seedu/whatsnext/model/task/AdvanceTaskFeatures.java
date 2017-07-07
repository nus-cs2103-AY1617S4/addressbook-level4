package seedu.whatsnext.model.task;

public interface AdvanceTask {
    Date getDate();
    Time getEndTime();

    default void setEndTime(Time time){
        setEndTime(time);
    }
}
