package seedu.whatsnext.model;

import java.util.Set;

import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the TaskManager */
    ReadOnlyTaskManager getTaskManager();

    // @@author A0154986L
    /** Undo previous action of task manager. */
    void undoTaskManager();

    // @@author A0154986L
    /** Redo previous action of task manager. */
    void redoTaskManager();

    /** Re-saves data when file path is changed. */
    void saveTaskManager();

    /** Deletes the given task. */
    void deleteTask(BasicTaskFeatures target) throws TaskNotFoundException;

    /** Adds the given task */
    void addTask(BasicTask basicTask) throws DuplicateTaskException;

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    void updateTask(BasicTaskFeatures target, BasicTaskFeatures editedTask)
            throws DuplicateTaskException, TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<BasicTaskFeatures>} */
    UnmodifiableObservableList<BasicTaskFeatures> getFilteredTaskList();

    // @@author A0154986L
    /**
     * Returns the filtered event task list for reminder pop up window.
     */
    UnmodifiableObservableList<BasicTaskFeatures> getFilteredTaskListForEventReminder();

    // @@author A0154986L
    /**
     * Returns the filtered deadline task list for reminder pop up window.
     */
    UnmodifiableObservableList<BasicTaskFeatures> getFilteredTaskListForDeadlineReminder();

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    void updateFilteredTaskListForInitialView();

    // @@author A0154986L
    /**
     * Updates the filter of the filtered task list to filter by task completion
     */
    void updateFilteredTaskListToShowByCompletion(boolean isComplete);

}
