package seedu.ticktask.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.tag.UniqueTagList;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.UniqueTaskList;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the TickTask level
 * Duplicates are not allowed (by .equals comparison)
 */
public class TickTask implements ReadOnlyTickTask {

    private final UniqueTaskList tasks;
    //@@author A0147928N
    private final UniqueTaskList completedTasks;
    //@@author
    private final UniqueTagList tags;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tasks = new UniqueTaskList();
        completedTasks = new UniqueTaskList();
        tags = new UniqueTagList();
    }

    public TickTask() {}
    
    /**
     * Creates an TickTask list using the Tasks and Tags in the {@code toBeCopied}
     */
    public TickTask(ReadOnlyTickTask toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setTasks(List<? extends ReadOnlyTask> tasks,
            List<? extends ReadOnlyTask> completedTasks) throws DuplicateTaskException {
        this.tasks.setTasks(tasks);
        this.completedTasks.setTasks(completedTasks);
    }

    public void setTags(Collection<Tag> tags) throws UniqueTagList.DuplicateTagException {
        this.tags.setTags(tags);
    }

    public void resetData(ReadOnlyTickTask newData) {
        requireNonNull(newData);
        try {
            setTasks(newData.getTaskList(), newData.getCompletedTaskList());
        } catch (DuplicateTaskException e) {
            assert false : "The TickTask program should not have duplicate tasks";
        } 
        try {
            setTags(newData.getTagList());
        } catch (UniqueTagList.DuplicateTagException e) {
            assert false : "The TickTask program should not have duplicate tags";
        }
        syncMasterTagListWith(tasks);
    }

    //// task-level operations

    /**
     * Adds a task to the TickTask.
     * Also checks the new task's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the task to point to those in {@link #tags}.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     * @throws PastTaskException 
     * @throws EventClashException 
     */
    public void addTask(ReadOnlyTask p) throws DuplicateTaskException {
        Task newTask = new Task(p);
        newTask.resetTaskType();
        syncMasterTagListWith(newTask);
        tasks.add(newTask);
    }
    
    /**
     * Replaces the given task {@code target} in the list with {@code editedReadOnlyTask}.
     * {@code TickTask}'s tag list will be updated with the tags of {@code editedReadOnlyTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     * @throws PastTaskException 
     * @throws EventClashException 
     *
     * @see #syncMasterTagListWith(Task)
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedReadOnlyTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedReadOnlyTask);

        Task editedTask = new Task(editedReadOnlyTask);
        editedTask.resetTaskType();
        syncMasterTagListWith(editedTask);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any task
        // in the tasklist.
        tasks.updateTask(target, editedTask);
    }

    /**
     * Ensures that every tag in this task:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Task task) {
        final UniqueTagList taskTags = new UniqueTagList(task.getTags());
        tags.mergeFrom(taskTags);

        // Create map with values = tag object references in the master list
        // used for checking task tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of task tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        taskTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        task.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these tasks:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Task)
     */
    private void syncMasterTagListWith(UniqueTaskList tasks) {
        tasks.forEach(this::syncMasterTagListWith);
    }

    //@@author A0131884B

    /**
     * Removes an task from the task list using find task name method
     * @param key is of type ReadOnlyTask
     * @return boolean
     */
    public boolean removeFindTask(ReadOnlyTask key) throws TaskNotFoundException {
         if (completedTasks.contains(key)) {
             return completedTasks.remove(key);
         } else if (tasks.contains(key)) {
             return tasks.remove(key);
         } else {
             throw new TaskNotFoundException();
         }
    }

    /**
     * Removes an task from the active task list using find task index method
     * @param key is of type ReadOnlyTask
     * @return boolean
     */
    public boolean removeIndexActiveTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)){
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Removes an task from the complete task list using find task index method
     * @param key is of type ReadOnlyTask
     * @return boolean
     */
    public boolean removeIndexCompleteTask(ReadOnlyTask key) throws TaskNotFoundException {
                if (completedTasks.remove(key)){
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    //@@author

    //@@author A0147928N
    /**
     * Adds the task to the list of completed tasks and removes it from the tasks list.
     */
    public boolean completeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.contains(key)) {
            completedTasks.archive(key);
            tasks.remove(key);
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }
    //@@author

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return tasks.asObservableList().size() + " tasks, " + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return new UnmodifiableObservableList<>(tasks.asObservableList());
    }

    public ObservableList<ReadOnlyTask> getCompletedTaskList() {
        return new UnmodifiableObservableList<>(completedTasks.asObservableList());
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return new UnmodifiableObservableList<>(tags.asObservableList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TickTask // instanceof handles nulls
                        && this.tasks.equals(((TickTask) other).tasks)
                        && this.tags.equalsOrderInsensitive(((TickTask) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(tasks, tags);
    }
    
    public boolean isChornological(ReadOnlyTask t) {
        return tasks.isChornological(t);
    }
    
    public String eventClash(ReadOnlyTask t) {
        return tasks.eventClash(t);
    }

}
