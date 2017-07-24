package seedu.ticktask.commons.events.model;

import seedu.ticktask.commons.events.BaseEvent;
import seedu.ticktask.model.ReadOnlyTickTask;

/** Indicates the TickTask in the model has changed*/
public class TickTaskChangedEvent extends BaseEvent {

    public final ReadOnlyTickTask data;

    public TickTaskChangedEvent(ReadOnlyTickTask data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of Tasks " + data.getActiveTaskList().size() + ", number of tags " + data.getTagList().size();
    }
}
