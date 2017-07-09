package seedu.whatsnext.model;

import javafx.collections.ObservableList;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyTaskManager {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<BasicTask> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
