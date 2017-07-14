package seedu.ticktask.model;

import java.util.EmptyStackException;
import java.util.Set;

import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTickTask newData);

    /** Returns the TickTask */
    ReadOnlyTickTask getTickTask();

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Marks the given task as complete and archives the task. */
    void completeTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Adds the given task */
    void addTask(ReadOnlyTask task) throws DuplicateTaskException;

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks*/
    void updateFilteredListToShowAll();
    //@@author A0138471A
    /** Updates the filter of the filtered task list to show today*/
	void updateFilteredListToShowToday();
	
	/** Updates the filter of the filtered task list to show event*/
	void updateFilteredListToShowEvent();
	
	/** Updates the filter of the filtered task list to show deadline*/
	void updateFilteredListToShowDeadline();
	
	/** Updates the filter of the filtered task list to show floating*/
	void updateFilteredListToShowFloating();
	//@@author
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList();

    void deleteCompletedTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Undo a previously completed action on the TickTask program*/
    void undoPreviousCommand() throws EmptyStackException;

    /**Redo a previously undone action on the TickTask program*/
    void redoUndoneCommand() throws EmptyStackException;


}
