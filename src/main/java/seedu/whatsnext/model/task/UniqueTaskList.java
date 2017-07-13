package seedu.whatsnext.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.util.CollectionUtil;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;

/**
 * A list of tasks that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see BasicTask#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<BasicTask> {

    private final ObservableList<BasicTask> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(BasicTaskFeatures toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(BasicTaskFeatures toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(new BasicTask(toAdd));
    }

    /**
     * Replaces the task {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTask(BasicTaskFeatures target, BasicTaskFeatures editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        BasicTask taskToUpdate = internalList.get(index);
        if (!taskToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        taskToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated task.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        internalList.set(index, taskToUpdate);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws TaskNotFoundException if no such person could be found in the list.
     */
    public boolean remove(BasicTaskFeatures toRemove) throws TaskNotFoundException {
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

    public void setTasks(List<? extends BasicTaskFeatures> baseTasksList) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final BasicTaskFeatures task : baseTasksList) {
            replacement.add(new BasicTask(task));
        }
        setTasks(replacement);
    }

    //@@author A0154986L
    /***
     * Sorts all tasks by Event tasks first, then Deadline tasks then Floating tasks
     */
    public void sort() {
        ObservableList<BasicTask> eventList = FXCollections.observableArrayList();
        ObservableList<BasicTask> deadlineList = FXCollections.observableArrayList();
        ObservableList<BasicTask> floatingList = FXCollections.observableArrayList();
        for (BasicTask basicTask : internalList) {
            if (basicTask.getTaskType().equals("event")) {
                eventList.add(basicTask);
            }
            if (basicTask.getTaskType().equals("deadline")) {
                deadlineList.add(basicTask);
            }
            if (basicTask.getTaskType().equals("floating")) {
                floatingList.add(basicTask);
            }
        }

        internalList.clear();
        sortEvents(eventList);
        sortDeadlines(deadlineList);
        sortFloating(floatingList);

        for (BasicTask basicTask : eventList) {
            internalList.add(basicTask);
        }
        for (BasicTask basicTask : deadlineList) {
            internalList.add(basicTask);
        }
        for (BasicTask basicTask : floatingList) {
            internalList.add(basicTask);
        }
    }

    //@@author A0154986L
    /***
     * Sorts event tasks by end date and time first, then start date and time.
     */
    private void sortEvents(ObservableList<BasicTask> eventList) {
        Collections.sort(eventList, new EndDateTimeComparator());
        Collections.sort(eventList, new StartDateTimeComparator());
    }

    //@@author A0154986L
    /***
     * Sorts deadline tasks by end date and time only.
     */
    private void sortDeadlines(ObservableList<BasicTask> deadlineList) {
        Collections.sort(deadlineList, new EndDateTimeComparator());
    }

    //@@author A0154986L
    /***
     * Sorts floating tasks by priority tags only.
     * Ordered by HIGH, MEDIUM, LOW
     */
    private void sortFloating(ObservableList<BasicTask> floatingList) {
        ObservableList<BasicTask> highList = FXCollections.observableArrayList();
        ObservableList<BasicTask> mediumList = FXCollections.observableArrayList();
        ObservableList<BasicTask> lowList = FXCollections.observableArrayList();
        ObservableList<BasicTask> otherList = FXCollections.observableArrayList();

        for (BasicTask basicTask : floatingList) {
            if (basicTask.getAllTags().contains("HIGH")) {
                highList.add(basicTask);
            } else if (basicTask.getAllTags().contains("MEDIUM")) {
                mediumList.add(basicTask);
            } else if (basicTask.getAllTags().contains("LOW")) {
                lowList.add(basicTask);
            } else {
                otherList.add(basicTask);
            }
        }

        floatingList.clear();

        for (BasicTask basicTask : highList) {
            floatingList.add(basicTask);
        }
        for (BasicTask basicTask : mediumList) {
            floatingList.add(basicTask);
        }
        for (BasicTask basicTask : lowList) {
            floatingList.add(basicTask);
        }
        for (BasicTask basicTask : otherList) {
            floatingList.add(basicTask);
        }
    }

    //@@author A0154986L
    /***
     * Compares the start data time value of the tasks.
     */
    static class StartDateTimeComparator implements Comparator<BasicTask> {
        public int compare(BasicTask c1, BasicTask c2) {
            return c1.getStartDateTime().toString().compareTo(c2.getStartDateTime().toString());
        }
    }

    //@@author A0154986L
    /***
     * Compares the end data time value of the tasks.
     */
    static class EndDateTimeComparator implements Comparator<BasicTask> {
        public int compare(BasicTask c1, BasicTask c2) {
            return c1.getEndDateTime().toString().compareTo(c2.getEndDateTime().toString());
        }
    }

    public UnmodifiableObservableList<BasicTask> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<BasicTask> iterator() {
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
