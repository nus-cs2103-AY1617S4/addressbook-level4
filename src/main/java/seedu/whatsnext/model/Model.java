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

    /** Displays the remind alert */
    void showReminderAlert();

    //@@author A0154986L
    /** Undo previous action of task manager. */
    void undoTaskManager();

    //@@author A0154986L
    /** Redo previous action of task manager. */
    void redoTaskManager();

    //@@author A0154986L
    /** Resets previous task manager instance. */
    void resetPrevTaskManager();

    //@@author A0154986L
    /** Returns current reminder setting. */
    String getReminderSetting();

    //@@author A0154986L
    /** Sets new reminder setting. */
    void setReminderSetting(String newReminderSetting);

    //@@author
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

    /** Updates the filter of the filtered task list to show all tasks */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    void updateFilteredTaskListForInitialView();

    //@@author A0154986L
    /**
     * Updates the filter of the filtered task list to filter by task completion
     */
    void updateFilteredTaskListToShowByCompletion(boolean isComplete);

    //@@author A0154986L
    /**
     * Updates the filter of the filtered task list to filter by upcoming tasks.
     */
    void updateFilteredTaskListToShowUpcomingTasks();

    //@@author A0154986L
    /**
     * Updates the filter of the filtered task list to filter by expired tasks.
     */
    void updateFilteredTaskListToShowByExpiry();

    //@@author A0154986L
    /**
     * Updates the filter of the filtered task list to filter for reminder pop up window.
     */
    void updateFilteredTaskListForReminder();

    //@@author A0149894H
    /** Returns current task manager file path. */
    String getTaskManagerFilePath();

    //@@author A0149894H
    /** Sets new task manager file path. */
    void setTaskManagerFilePath(String newFilePath);

}
