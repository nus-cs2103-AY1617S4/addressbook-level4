package seedu.ticktask.model;

import static seedu.ticktask.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.ticktask.commons.core.ComponentManager;
import seedu.ticktask.commons.core.LogsCenter;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.events.model.TickTaskChangedEvent;
import seedu.ticktask.commons.util.StringUtil;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TickTask tickTask;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyTask> filteredCompletedTasks;

    /**
     * Initializes a ModelManager with the given tickTask and userPrefs.
     */
    public ModelManager(ReadOnlyTickTask addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.tickTask = new TickTask(addressBook);
        filteredTasks = new FilteredList<>(this.tickTask.getTaskList());
        filteredCompletedTasks = new FilteredList<>(this.tickTask.getCompletedTaskList());
    }

    public ModelManager() {
        this(new TickTask(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTickTask newData) {
        tickTask.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTickTask getTickTask() {
        return tickTask;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TickTaskChangedEvent(tickTask));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyTask target) throws TaskNotFoundException {
        tickTask.removePerson(target);
        indicateAddressBookChanged();
    }
    
    public synchronized void deleteCompletedTask(ReadOnlyTask target) throws TaskNotFoundException {
        tickTask.removeCompletedTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(ReadOnlyTask task) throws DuplicateTaskException {
        tickTask.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }
    
    @Override
    public synchronized void completeTask(ReadOnlyTask task) throws TaskNotFoundException {
        tickTask.completeTask(task);
    }

    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        tickTask.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Return a list of {@code ReadOnlyPerson} backed by the internal list of {@code addressBook}
     */
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }
    
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList() {
        return new UnmodifiableObservableList<>(filteredCompletedTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
        filteredCompletedTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
        
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
        filteredCompletedTasks.setPredicate(expression::satisfies);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return tickTask.equals(other.tickTask)
                && filteredTasks.equals(other.filteredTasks);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask person);
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(ReadOnlyTask task) {
            return qualifier.run(task);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(ReadOnlyTask task);
        String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
