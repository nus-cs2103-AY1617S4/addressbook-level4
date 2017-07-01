package seedu.address.model;


import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of a TickTask client
 */
public interface ReadOnlyTickTask {

    /**
     * Returns an unmodifiable view of the task list.
     * This list will not contain any duplicate task.
     */
    ObservableList<ReadOnlyTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
