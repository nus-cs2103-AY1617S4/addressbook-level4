package seedu.ticktask.commons.events.model;

import seedu.ticktask.commons.events.BaseEvent;
//@@author A0138471A
public class ViewListChangedEvent extends BaseEvent{
    public final String typeOfListView;

    public ViewListChangedEvent(String typeOfListView) {
        this.typeOfListView = typeOfListView;
    }

    public String getTypeOfListView() {
        return typeOfListView;
    }

    @Override
    public String toString() {
        return typeOfListView + " tasks listed event";
    }
}
