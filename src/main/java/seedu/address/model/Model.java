package seedu.address.model;

import java.util.Set;

import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.model.person.ReadOnlyTask;
import seedu.address.model.person.exceptions.DuplicateTaskException;
import seedu.address.model.person.exceptions.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyTickTask newData);

    /** Returns the AddressBook */
    ReadOnlyTickTask getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyTask target) throws TaskNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyTask person) throws DuplicateTaskException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicateTaskException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyTask target, ReadOnlyTask editedPerson)
            throws DuplicateTaskException, TaskNotFoundException;

    /** Returns the filtered person list as an {@code UnmodifiableObservableList<ReadOnlyPerson>} */
    UnmodifiableObservableList<ReadOnlyTask> getFilteredPersonList();

    /** Updates the filter of the filtered person list to show all persons */
    void updateFilteredListToShowAll();

    /** Updates the filter of the filtered person list to filter by the given keywords*/
    void updateFilteredPersonList(Set<String> keywords);

}
