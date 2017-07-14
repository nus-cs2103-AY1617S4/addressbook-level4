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
     */
    public void add(ReadOnlyTask toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        if(!isChornological(toAdd)){
            throw new DuplicateTaskException();
        }

        //if (eventClash(toAdd)) {
        //    throw new DuplicateTaskException();
        //}
        
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
    
    /**
     * Returns true if the list contains a task within the same time frame as the given argument.
     */
    public boolean eventClash(ReadOnlyTask toCheck) {
        FilteredList<Task> eventList = internalList.filtered(isEvent());

        LocalDate toCheckStartDate = toCheck.getDate().getLocalStartDate();
        LocalDate toCheckendDate = toCheck.getDate().getLocalEndDate();
        LocalTime toCheckStartTime = toCheck.getTime().getLocalStartTime();
        LocalTime toCheckEndTime = toCheck.getTime().getLocalEndTime();

        for (Task curr : eventList) {
            LocalDate currStartDate = curr.getDate().getLocalStartDate();
            LocalDate currEndDate = curr.getDate().getLocalEndDate();
            LocalTime currStartTime = curr.getTime().getLocalStartTime();
            LocalTime currEndTime = curr.getTime().getLocalEndTime();

            if (toCheckendDate.isBefore(currStartDate) && toCheckStartDate.isAfter(currEndDate)) {
                continue;
            } else if (toCheckStartDate.equals(currStartDate) && toCheckStartTime.isBefore(currStartTime)) {
                continue;
            } else if (toCheckendDate.equals(currEndDate) && toCheckEndTime.isAfter(currEndTime)) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }
    //@@author
    
    //@@author A0147928N
    /**
     * Archives the task into the internalList
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
        
        //if (eventClash(editedTask)) {
        //    throw new DuplicateTaskException();
        //}

        if(!isChornological(editedTask)){
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
    
    //@@author A0139964M
    /**
     * Checks if the task added is in the past.
     * @param task
     * @return boolean
     */
    public boolean isChornological(ReadOnlyTask task) {
        LocalDate currDate = LocalDate.now();
        LocalDate taskDate = task.getDate().getLocalStartDate();
        //System.out.println("localDate: " + currDate);
        //System.out.println("TaskDate: " + taskDate);
        
        if(task.getDate().getLocalStartDate() == null){
            //No date either means today or no time, if no time or time is chornological just add
            if(task.getTime().getLocalStartTime() == null || isTimeChornological(task)){
                return true;
            }
        }
        //Check if task's is today.
        if(taskDate.isEqual(currDate)){ //If date is today's date, check if time is chornological
            if(task.getTime().getLocalStartTime() == null|| isTimeChornological(task)){
                return true;
            } else {
                return false;
            }
        }
        //If date exist, but it is in the future, i dont need to check the time.
        if(isDateChornological(task)){
            return true;
        } else{
            return false;
        }
    }
    
    public boolean isTimeChornological(ReadOnlyTask task) {
        LocalTime currTime = LocalTime.now();
        LocalTime taskTime = task.getTime().getLocalStartTime();
        if (taskTime.isBefore(currTime)) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean isDateChornological(ReadOnlyTask task){
        LocalDate currDate = LocalDate.now();
        LocalDate taskDate = task.getDate().getLocalStartDate();
        if(taskDate.isBefore(currDate)){
            return false;
        } else {
            return true;
        }
    }
    //@@author
}
