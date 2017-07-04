package seedu.whatsnext.model;

import java.util.Set;

import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.model.person.BaseTask;
import seedu.whatsnext.model.person.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.person.exceptions.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTaskManager newData);

    /** Returns the AddressBook */
    ReadOnlyTaskManager getTaskManager();

    /** Deletes the given person. */
    void deleteTask(BaseTask target) throws TaskNotFoundException;

    /** Adds the given person */
    void addTask(BaseTask baseTask) throws DuplicateTaskException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    void updateTask(BaseTask target, BaseTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException;

    /** Returns the filtered task list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<BaseTask> getFilteredTaskList();

    /** Updates the filter of the filtered task list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered task list to filter by the given keywords*/
    void updateFilteredTaskList(Set<String> keywords);

}
