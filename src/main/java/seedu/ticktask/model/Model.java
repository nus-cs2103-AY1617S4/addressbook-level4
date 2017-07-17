package seedu.ticktask.model;

import java.util.EmptyStackException;
import java.util.Set;

import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.EventClashException;
import seedu.ticktask.model.task.exceptions.PastTaskException;
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
    //@@author

    /** Marks the given task as complete and archives the task. */
    void completeTask(ReadOnlyTask target) throws TaskNotFoundException;

    /** Adds the given task 
     * @throws PastTaskException 
     * @throws EventClashException */
    void addTask(ReadOnlyTask task) throws DuplicateTaskException, PastTaskException, EventClashException;

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
            throws DuplicateTaskException, TaskNotFoundException, PastTaskException, EventClashException;

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
    
    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

    UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList();

    /** Undo a previously completed action on the TickTask program*/
    void undoPreviousCommand() throws EmptyStackException;

    /**Redo a previously undone action on the TickTask program*/
    void redoUndoneCommand() throws EmptyStackException;
}
