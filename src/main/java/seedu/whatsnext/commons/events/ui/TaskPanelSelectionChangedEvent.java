package seedu.whatsnext.commons.events.ui;

import seedu.whatsnext.commons.events.BaseEvent;
import seedu.whatsnext.model.task.BasicTaskFeatures;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final BasicTaskFeatures newSelection;

    public TaskPanelSelectionChangedEvent(BasicTaskFeatures newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public BasicTaskFeatures getNewSelection() {
        return newSelection;
    }
}
