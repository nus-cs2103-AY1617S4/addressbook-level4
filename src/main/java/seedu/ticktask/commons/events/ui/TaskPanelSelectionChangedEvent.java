package seedu.ticktask.commons.events.ui;

import seedu.ticktask.commons.events.BaseEvent;
import seedu.ticktask.model.task.ReadOnlyTask;

/**
 * Represents a selection change in the Person List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final ReadOnlyTask newSelection;

    public TaskPanelSelectionChangedEvent(ReadOnlyTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ReadOnlyTask getNewSelection() { 
        return newSelection;
    }
}
