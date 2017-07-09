package seedu.whatsnext.commons.events.ui;

import seedu.whatsnext.commons.events.BaseEvent;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.ui.ResultDisplay;

/**
 * Represents a selection change in the Task List Panel
 */
public class TaskPanelSelectionChangedEvent extends BaseEvent {


    private final BasicTaskFeatures newSelection;

    public TaskPanelSelectionChangedEvent(BasicTaskFeatures newSelection) {
        this.newSelection = newSelection;
        ResultDisplay.showSelectedTask(newSelection);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public BasicTaskFeatures getNewSelection() {
        return newSelection;
    }
}
