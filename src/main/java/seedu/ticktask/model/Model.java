package seedu.ticktask.model;

import java.util.EmptyStackException;
import java.util.Set;

import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.parser.exceptions.ParseException;
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

    //@@author A0131884B
    /** Deletes the given task using find task name method. */
    void deleteFindTask(ReadOnlyTask target) throws TaskNotFoundException, DuplicateTaskException;

    /** Deletes the given complete task using find task index method. */
    void deleteIndexCompleteTask(ReadOnlyTask target) throws TaskNotFoundException, DuplicateTaskException;

    /** Deletes the given active task using find task index method. */
    void deleteIndexActiveTask(ReadOnlyTask target) throws TaskNotFoundException, DuplicateTaskException;

    /** Clear active list. */
    void resetActiveData(ReadOnlyTickTask newData);

    /** clear complete list. */
    void resetCompleteData(ReadOnlyTickTask newData);
    //@@author
    
    //@@author A0147928N
    /** Marks the given task as complete and archives the task. */
    void completeTask(ReadOnlyTask target) throws TaskNotFoundException;
    
    /** Restores the given task from archive. 
     * @throws DuplicateTaskException */
    void restoreTask(ReadOnlyTask target) throws TaskNotFoundException, DuplicateTaskException;
    //@@author

    /** Adds the given task 
     * @throws PastTaskException 
     * @throws EventClashException */
    void addTask(ReadOnlyTask task) throws DuplicateTaskException;

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     * @throws PastTaskException 
     * @throws EventClashException 
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyTask>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all tasks*/
    void updateFilteredListToShowAll();
    
    //@@author A0138471A
    /** Updates the filter of the filtered task list to show all event tasks*/
    void updateFilteredListToShowEvent();
    
    /** Updates the filter of the filtered task list to show all deadline tasks*/
    void updateFilteredListToShowDeadline();
    
    /** Updates the filter of the filtered task list to show all floating tasks*/
    void updateFilteredListToShowFloating();
    
    /** Updates the filter of the filtered task list to show all today's tasks*/
    void updateFilteredListToShowToday();
    
    public void saveTickTask();
    //@@author
    
    /** Updates the filter of the filtered task list if it contains the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    //@@author A0131884B
    /** Updates the filter of the filtered task list if it matches the given keywords*/
    void updateMatchedTaskList(Set<String> keywords);
    //@@author

    UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList();
    //@@author A0139819N
    /** Undo a previously completed action on the TickTask program*/
    void undoPreviousCommand() throws EmptyStackException;

    /**Redo a previously undone action on the TickTask program*/
    void redoUndoneCommand() throws EmptyStackException;
    //@@author

    String eventClash(ReadOnlyTask t);
}
