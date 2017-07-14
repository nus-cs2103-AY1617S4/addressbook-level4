package seedu.ticktask.model;

import static seedu.ticktask.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.Stack;
import java.util.function.Predicate;
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
 * Represents the in-memory model of the TickTask client data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private TickTask currentProgramInstance;
    private Stack<TickTask> previousProgramInstances;
    private Stack<TickTask> futureProgramInstances;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final FilteredList<ReadOnlyTask> filteredCompletedTasks;

    /**
     * Initializes a ModelManager with the given tickTask and userPrefs.
     */
    public ModelManager(ReadOnlyTickTask tickTask, UserPrefs userPrefs) {
        super();
        requireAllNonNull(tickTask, userPrefs);

        logger.fine("Initializing with Tick Task program: " + tickTask + " and user prefs " + userPrefs);

        this.currentProgramInstance = new TickTask(tickTask);
        filteredTasks = new FilteredList<>(this.currentProgramInstance.getTaskList());
        filteredCompletedTasks = new FilteredList<>(this.currentProgramInstance.getCompletedTaskList());
        previousProgramInstances = new Stack<TickTask>();
        futureProgramInstances = new Stack<TickTask>();
    }

    public ModelManager() {
        this(new TickTask(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTickTask newData) {
        saveInstance();
        currentProgramInstance.resetData(newData);
        indicateTickTaskModelChanged();
    }

    @Override
    public ReadOnlyTickTask getTickTask() {
        return currentProgramInstance;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTickTaskModelChanged() {
        raise(new TickTaskChangedEvent(currentProgramInstance));
    }
    //@@author A0139819N

    /**Reverses the previous command executed by the user
     * Restores the TickTask client to one state before the previous command was executed
     * */
    public void undoPreviousCommand() {
        TickTask currentTickTaskInstance = new TickTask(currentProgramInstance);
        currentProgramInstance.resetData(previousProgramInstances.pop());
        futureProgramInstances.push(currentTickTaskInstance);
        indicateTickTaskModelChanged();
    }

    /**Redo an existing command that was previously undone by the user
     * Restores the TickTask client to one state after the undone command was executed
     * User can only use redo command after an undo command was previously executed
     */
    public void redoUndoneCommand() {
        TickTask currentTickTaskInstance = new TickTask(currentProgramInstance);
        currentProgramInstance.resetData(futureProgramInstances.pop());
        previousProgramInstances.push(currentTickTaskInstance);
        indicateTickTaskModelChanged();
    }

    /**Saves the current instance of the TickTask program before any data is modified
     * so that the program can return to previous instances if desired
     */
    private void saveInstance() {
        previousProgramInstances.push(new TickTask(currentProgramInstance));
        futureProgramInstances.clear();
    }

    //@@author A0139819N

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        saveInstance();
        currentProgramInstance.removeTask(target);
        indicateTickTaskModelChanged();
    }

    public synchronized void deleteCompletedTask(ReadOnlyTask target) throws TaskNotFoundException {
        currentProgramInstance.removeCompletedTask(target);
        indicateTickTaskModelChanged();
    }

    @Override
    public synchronized void addTask(ReadOnlyTask task) throws DuplicateTaskException {
        saveInstance();
        currentProgramInstance.addTask(task);
        updateFilteredListToShowAll();
        indicateTickTaskModelChanged();
    }

    @Override
    public synchronized void completeTask(ReadOnlyTask task) throws TaskNotFoundException {
        saveInstance();
        currentProgramInstance.completeTask(task);
    }

    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);
        saveInstance();
        currentProgramInstance.updateTask(target, editedTask);
        indicateTickTaskModelChanged();
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Return a list of {@code ReadOnlyTask} backed by the internal list of {@code TickTask}
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
    
    //@@author A0138471A
    @Override
    public void updateFilteredListToShowEvent() {      
    	
        filteredTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("event");
        });
        filteredCompletedTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("event");
        });

    }
    
    @Override
    public void updateFilteredListToShowDeadline() {      
    	
        filteredTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("deadline");
        });
        filteredCompletedTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("deadline");
        });

    }
    
    @Override
    public void updateFilteredListToShowFloating() {      
    	
        filteredTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("floating");
        });
        filteredCompletedTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("floating");
        });

    }
    
    @Override
    public void updateFilteredListToShowToday() {      
    	
        filteredTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return ((task.getDate().getEndDate().equals(LocalDate.now().format(DATE_FORMAT).toString())) || 
            		(task.getDate().getStartDate().equals(LocalDate.now().format(DATE_FORMAT).toString())));
        });
        filteredCompletedTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return ((task.getDate().getEndDate().equals(LocalDate.now().format(DATE_FORMAT).toString())) || 
            		(task.getDate().getStartDate().equals(LocalDate.now().format(DATE_FORMAT).toString())));
        });

    }
    //@@author

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
        return currentProgramInstance.equals(other.currentProgramInstance)
                && filteredTasks.equals(other.filteredTasks);
    }

    //========== Inner classes/interfaces used for filtering =================================================

    interface Expression {
        boolean satisfies(ReadOnlyTask task);
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
                    .filter(keyword -> StringUtil.containsStringIgnoreCase(task.getName().fullName, keyword))
                    .findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

}