package seedu.ticktask.model;

import javafx.collections.ObservableList;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.ReadOnlyTask;

/**
 * Unmodifiable view of a TickTask client
 */
public interface ReadOnlyTickTask {

    /**
     * Returns an unmodifiable view of the task list.
     * This list will not contain any duplicate task.
     */
    ObservableList<ReadOnlyTask> getActiveTaskList();

    ObservableList<ReadOnlyTask> getCompletedTaskList();


    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
