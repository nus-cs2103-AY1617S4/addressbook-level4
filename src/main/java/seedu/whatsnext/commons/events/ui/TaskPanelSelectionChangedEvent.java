package seedu.whatsnext.commons.events.ui;

import seedu.whatsnext.commons.events.BaseEvent;
import seedu.whatsnext.model.task.BaseTask;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final BaseTask newSelection;

    public TaskPanelSelectionChangedEvent(BaseTask newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public BaseTask getNewSelection() {
        return newSelection;
    }
}
