package seedu.whatsnext.model.task;

import java.util.Set;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.tag.Tag;

public class DeadlineTask extends BasicTask{
    DateTime endTime;

    public DeadlineTask(BasicTaskFeatures source) throws IllegalValueException {
        super(source);
        endTime = new DateTime("12/12/12");
    }

    public DeadlineTask(TaskName taskName, DateTime endTime, boolean isCompleted, Set<Tag> tags) {
        super(taskName, isCompleted, tags);
        this.endTime = endTime;
    }

    public DeadlineTask(TaskName taskName, DateTime endTime, Set<Tag> tags) {
        super(taskName, tags);
        this.endTime = endTime;
    }


}
