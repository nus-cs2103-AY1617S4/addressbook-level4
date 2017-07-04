package seedu.whatsnext.model.task;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.util.CollectionUtil;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Floating#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueTaskList implements Iterable<Floating> {

    private final ObservableList<Floating> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent task as the given argument.
     */
    public boolean contains(BaseTask toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a task to the list.
     *
     * @throws DuplicateTaskException if the task to add is a duplicate of an existing task in the list.
     */
    public void add(BaseTask toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTaskException();
        }
        internalList.add(new Floating(toAdd));
    }

    /**
     * Replaces the person {@code target} in the list with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTask(BaseTask target, BaseTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedTask);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TaskNotFoundException();
        }

        Floating personToUpdate = internalList.get(index);
        if (!personToUpdate.equals(editedTask) && internalList.contains(editedTask)) {
            throw new DuplicateTaskException();
        }

        personToUpdate.resetData(editedTask);
        // TODO: The code below is just a workaround to notify observers of the updated task.
        // The right way is to implement observable properties in the Task class.
        // Then, TaskCard should then bind its text labels to those observable properties.
        internalList.set(index, personToUpdate);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws TaskNotFoundException if no such person could be found in the list.
     */
    public boolean remove(BaseTask toRemove) throws TaskNotFoundException {
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

    public void setTasks(List<? extends BaseTask> baseTasksList) throws DuplicateTaskException {
        final UniqueTaskList replacement = new UniqueTaskList();
        for (final BaseTask task : baseTasksList) {
            replacement.add(new Floating(task));
        }
        setTasks(replacement);
    }

    public UnmodifiableObservableList<Floating> asObservableList() {
        return new UnmodifiableObservableList<>(internalList);
    }

    @Override
    public Iterator<Floating> iterator() {
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
