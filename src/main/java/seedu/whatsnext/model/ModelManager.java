package seedu.whatsnext.model;

import static seedu.whatsnext.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.whatsnext.commons.core.ComponentManager;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.events.model.TaskManagerChangedEvent;
import seedu.whatsnext.commons.util.StringUtil;
import seedu.whatsnext.model.task.BaseTask;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook taskManager;
    private final FilteredList<BaseTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.taskManager = new AddressBook(addressBook);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        taskManager.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(BaseTask target) throws TaskNotFoundException {
        taskManager.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(BaseTask task) throws DuplicateTaskException {
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateAddressBookChanged();
    }

    @Override
    public void updateTask(BaseTask target, BaseTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        taskManager.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Return a list of {@code BaseTask} backed by the internal list of {@code taskManager}
     */
    @Override
    public UnmodifiableObservableList<BaseTask> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
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
        return taskManager.equals(other.taskManager)
                && filteredTasks.equals(other.filteredTasks);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(BaseTask baseTask);
        @Override
		String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(BaseTask baseTask) {
            return qualifier.run(baseTask);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(BaseTask baseTask);
        @Override
		String toString();
    }

    private class NameQualifier implements Qualifier {
        private Set<String> nameKeyWords;

        NameQualifier(Set<String> nameKeyWords) {
            this.nameKeyWords = nameKeyWords;
        }

        @Override
        public boolean run(BaseTask baseTask) {
            return nameKeyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(baseTask.getName().fullTaskName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}
