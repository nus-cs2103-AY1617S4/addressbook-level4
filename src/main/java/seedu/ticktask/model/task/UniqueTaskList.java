package seedu.ticktask.model.task;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.util.CollectionUtil;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Task#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Task> {

    private final ObservableList<Task> internalList = FXCollections.observableArrayList();


    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(ReadOnlyTask toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     * @throws EventClashException 
     */
    public void add(ReadOnlyTask toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
       
        toAdd.resetTaskType();
        internalList.add(new Task(toAdd));
    }


    //@@author A0147928N
    /**
     * This predicate will be used to filter the list of tasks in eventClash();
     */
    public static Predicate<Task> isEvent() {
        return p-> p.getTaskType().toString().equals("event");
    }
    //@@author

    //@@author A0147928N
    /**
     * Returns true if the list contains a task within the same time frame as the given argument.
     */
    public String eventClash(ReadOnlyTask toCheck) {
        FilteredList<Task> eventList = internalList.filtered(isEvent());

        LocalDate toCheckStartDate = toCheck.getDate().getLocalStartDate();
        LocalDate toCheckEndDate = toCheck.getDate().getLocalEndDate();
        LocalTime toCheckStartTime = toCheck.getTime().getLocalStartTime();
        LocalTime toCheckEndTime = toCheck.getTime().getLocalEndTime();

        for (ReadOnlyTask curr : eventList) {
            if (curr.isSameStateAs(toCheck)) continue;
            LocalDate currStartDate = curr.getDate().getLocalStartDate();
            LocalDate currEndDate = curr.getDate().getLocalEndDate();
            LocalTime currStartTime = curr.getTime().getLocalStartTime();
            LocalTime currEndTime = curr.getTime().getLocalEndTime();

            if (toCheckEndDate.isBefore(currStartDate) || toCheckStartDate.isAfter(currEndDate)) {
                continue;
            } else if (toCheckStartDate.equals(currEndDate) && toCheckStartTime.isAfter(currEndTime)) {
                continue;
            } else if (toCheckEndDate.equals(currStartDate) && toCheckEndTime.isBefore(currStartTime)) {
                continue;
            } else {
                return curr.getName().toString();
            }
        }
        return null;
    }
    //@@author
    
   
    //@@author A0147928N
    /**
     * Archives the task into the internalList and marks the task as complete
     */
    public void archive(ReadOnlyTask toAdd) {
        toAdd.setCompleted(true);
        requireNonNull(toAdd);
        internalList.add(new Task(toAdd));
    }
    //@author
    


    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     * @throws PastTaskException 
     * @throws EventClashException 
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        Task taskToUpdate = internalList.get(index);
        
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated task.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        taskToUpdate.resetTaskType();
        internalList.set(index, taskToUpdate);
    }

    /**
     * Removes the equivalent task from the list.
     *
     * @throws TaskNotFoundException if no such task could be found in the list.
     */
    public boolean remove(ReadOnlyTask toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        final boolean taskFoundAndDeleted = internalList.remove(toRemove);
        if (!taskFoundAndDeleted) {
            throw new TaskNotFoundException();
        }
        return taskFoundAndDeleted;
    }

    public void setTasks(UniqueTaskList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final ReadOnlyTask task : tasks) {
            replacement.add(new Task(task));
        }
        setTasks(replacement);
    }

    public UnmodifiableObservableList<Task> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Task> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueTaskList // instanceof handles nulls
                        && this.internalList.equals(((UniqueTaskList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
    

}
