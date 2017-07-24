package seedu.whatsnext.commons.events.model;

import seedu.whatsnext.commons.events.BaseEvent;
import seedu.whatsnext.model.ReadOnlyTaskManager;

/**
 * Indicates the TaskManager in the model has changed
 */
public class TaskManagerChangedEvent extends BaseEvent {

    public final ReadOnlyTaskManager data;

    public TaskManagerChangedEvent(ReadOnlyTaskManager data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of tasks " + data.getTaskList().size() + ", number of unique tags " + data.getTagList().size();
    }
}
