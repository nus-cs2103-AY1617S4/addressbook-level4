package seedu.whatsnext.model;

import static seedu.whatsnext.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.whatsnext.commons.core.ComponentManager;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.events.model.TaskManagerChangedEvent;
import seedu.whatsnext.commons.util.StringUtil;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final TaskManager taskManager;
    private final FilteredList<BasicTask> filteredTasks;

    private Stack<TaskManager> undoTaskManager;
    private Stack<TaskManager> redoTaskManager;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        requireAllNonNull(taskManager, userPrefs);

        logger.fine("Initializing with address book: " + taskManager + " and user prefs " + userPrefs);
        this.userPrefs = userPrefs;
        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        undoTaskManager = new Stack<TaskManager>();
        redoTaskManager = new Stack<TaskManager>();
    }

    public ModelManager() {
        this(new TaskManager(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskManager newData) {
        saveInstance();
        taskManager.resetData(newData);
        indicateTaskManagerChanged();
    }

    // @@author A0154986L
    /** Save a copy of task manager before data is changed. */
    private void saveInstance() {
        undoTaskManager.push(new TaskManager(taskManager));
        redoTaskManager.clear();
    }

    // @@author A0154986L
    /** Undo previous action of task manager. */
    @Override
    public void undoTaskManager() {
        TaskManager currentTaskManager = new TaskManager(taskManager);
        taskManager.resetData(undoTaskManager.peek());
        undoTaskManager.pop();
        redoTaskManager.push(currentTaskManager);
        indicateTaskManagerChanged();
    }

    // @@author A0154986L
    /** Redo previous action of task manager. */
    @Override
    public void redoTaskManager() {
        TaskManager currentTaskManager = new TaskManager(taskManager);
        taskManager.resetData(redoTaskManager.peek());
        redoTaskManager.pop();
        undoTaskManager.push(currentTaskManager);
        indicateTaskManagerChanged();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    @Override
    public synchronized void deleteTask(BasicTaskFeatures target) throws TaskNotFoundException {
        saveInstance();
        taskManager.removeTask(target);
        indicateTaskManagerChanged();
    }

    @Override
    public synchronized void addTask(BasicTask task) throws DuplicateTaskException {
        saveInstance();
        taskManager.addTask(task);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(BasicTaskFeatures target, BasicTaskFeatures editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);
        saveInstance();

        taskManager.updateTask(target, editedTask);
        updateFilteredListToShowAll();
        indicateTaskManagerChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Return a list of {@code BaseTask} backed by the internal list of {@code taskManager}
     */
    @Override
    public UnmodifiableObservableList<BasicTaskFeatures> getFilteredTaskList() {
        return new UnmodifiableObservableList<>(filteredTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredTasks.setPredicate(null);

    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameAndTagQualifier(keywords)));
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    // @@author A0154986L
    @Override
    public void updateFilteredTaskListToShowByCompletion(boolean isComplete) {
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(isComplete)));
        indicateTaskManagerChanged();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instance of handles nulls
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
        boolean satisfies(BasicTaskFeatures basicTaskFeatures);
        @Override
        String toString();
    }

    private class PredicateExpression implements Expression {

        private final Qualifier qualifier;

        PredicateExpression(Qualifier qualifier) {
            this.qualifier = qualifier;
        }

        @Override
        public boolean satisfies(BasicTaskFeatures basicTaskFeatures) {
            return qualifier.run(basicTaskFeatures);
        }

        @Override
        public String toString() {
            return qualifier.toString();
        }
    }

    interface Qualifier {
        boolean run(BasicTaskFeatures basicTaskFeatures);
        @Override
        String toString();
    }

    // @@author A0154986L
    private class CompletedQualifier implements Qualifier {
        private boolean isComplete;

        CompletedQualifier(boolean isComplete) {
            this.isComplete = isComplete;
        }

        @Override
        public boolean run(BasicTaskFeatures basicTaskFeatures) {
            return (basicTaskFeatures.getIsCompleted() == isComplete);
        }

        @Override
        public String toString() {
            return "task name=" + String.join(", ", "w");
        }
    }

    @Override
    public String getFilePath() {
        return userPrefs.getTaskManagerFilePath();
    }

    /*
     * Find the task either by name or tag.
     * @@author A0142675B
     */
    private class NameAndTagQualifier implements Qualifier {
        private Set<String> keyWords;

        NameAndTagQualifier(Set<String> keyWords) {
            this.keyWords =  keyWords;
        }

        @Override
        public boolean run(BasicTaskFeatures basicTaskFeatures) {
            return (keyWords.stream()
                    .filter(keyword ->
                            StringUtil.containsWordIgnoreCase(basicTaskFeatures.getAllTags(), "[" + keyword + "]"))
                    .findAny()
                    .isPresent())
                    || (keyWords.stream()
                    .filter(keyword ->
                            StringUtil.containsWordIgnoreCase(basicTaskFeatures.getName().fullTaskName, keyword))
                    .findAny()
                    .isPresent());
        }

        @Override
        public String toString() {
            return "keywords = " + String.join(", ", keyWords);
        }
    }
}

