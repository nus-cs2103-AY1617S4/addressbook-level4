package teamthree.twodo.model;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import teamthree.twodo.commons.core.UnmodifiableObservableList;
import teamthree.twodo.model.tag.Tag;
import teamthree.twodo.model.tag.UniqueTagList;
import teamthree.twodo.model.task.ReadOnlyTask;
import teamthree.twodo.model.task.Task;
import teamthree.twodo.model.task.TaskWithDeadline;
import teamthree.twodo.model.task.UniqueTaskList;
import teamthree.twodo.model.task.exceptions.DuplicateTaskException;
import teamthree.twodo.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the address-book level Duplicates are not allowed (by
 * .equals comparison)
 */
public class TaskBook implements ReadOnlyTaskBook {

    private final UniqueTaskList tasks;
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block,
     * sometimes used to avoid duplication between constructors. See
     * https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Description that non-static init blocks are not recommended to use. There
     * are other ways to avoid duplication among constructors.
     */
    {
        tasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TaskBook() {
    }

    /**
     * Creates an TaskBook using the Persons and Tags in the {@code toBeCopied}
     */
    public TaskBook(ReadOnlyTaskBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        this.tasks.setPersons(tasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTaskBook newData) {
        requireNonNull(newData);
        try {
            setTasks(newData.getTaskList());
        } catch (DuplicateTaskException e) {
            assert false : "AddressBooks should not have duplicate tasks";
        }
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "AddressBooks should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

    //// person-level operations

    /**
     * Adds a person to the address book. Also checks the new person's tags and
     * updates {@link #tags} with any new tags found, and updates the Tag
     * objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicateTaskException
     *             if an equivalent person already exists.
     */
    public void addTask(ReadOnlyTask p) throws DuplicateTaskException {
        Task newPerson;
        if (p instanceof TaskWithDeadline) {
            newPerson = new TaskWithDeadline(p);
        } else {
            newPerson = new Task(p);
        }
        syncMasterTagListWith(newPerson);
        tasks.add(newPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with
     * {@code editedReadOnlyPerson}. {@code TaskBook}'s tag list will be updated
     * with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicateTaskException
     *             if updating the person's details causes the person to be
     *             equivalent to another existing person in the list.
     * @throws TaskNotFoundException
     *             if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Task)
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedReadOnlyTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedReadOnlyTask);

        Task editedTask;
        if (editedReadOnlyTask instanceof TaskWithDeadline) {
            editedTask = new TaskWithDeadline(editedReadOnlyTask);
        } else {
            editedTask = new Task(editedReadOnlyTask);
        }
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        tasks.updateTask(target, editedTask);
    }

    /**
     * Ensures that every tag in this person: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = new UniqueTagList(task.getTags());
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these tasks: - exists in the master list
     * {@link #tags} - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    public boolean removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    public boolean markTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.mark(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    public boolean unmarkTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.unmark(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskBook // instanceof handles nulls
                        && this.tasks.equals(((TaskBook) other).tasks)
                        && this.tags.equalsOrderInsensitive(((TaskBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
}
