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
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicateTaskException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask editedPerson)
            throws DuplicateTaskException, TaskNotFoundException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

	UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList();

	void deleteCompletedTask(ReadOnlyTask target) throws TaskNotFoundException;
	
	/** Undo a previously completed action on TickTask*/
	void undoPreviousCommand() throws EmptyStackException;
	
	/**Redo a previously undone action on TickTask*/
	void redoUndoneCommand() throws EmptyStackException;
}
