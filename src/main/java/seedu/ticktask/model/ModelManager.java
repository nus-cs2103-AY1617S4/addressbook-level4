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
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd/uuuu");
    protected TickTask currentProgramInstance;
    protected Stack<TickTask> previousProgramInstances;
    protected Stack<TickTask> futureProgramInstances;
    private final FilteredList<ReadOnlyTask> filteredActiveTasks;
    private final FilteredList<ReadOnlyTask> filteredCompletedTasks;

    /**
     * Initializes a ModelManager with the given tickTask and userPrefs.
     */
    public ModelManager(ReadOnlyTickTask tickTask, UserPrefs userPrefs) {
        super();
        requireAllNonNull(tickTask, userPrefs);

        logger.fine("Initializing with Tick Task program: " + tickTask + " and user prefs " + userPrefs);

        this.currentProgramInstance = new TickTask(tickTask);
        filteredActiveTasks = new FilteredList<>(this.currentProgramInstance.getActiveTaskList());
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
    
    //@@author A0139819N
    /**Saves the current instance of the TickTask program before any data is modified
     * so that the program can return to previous instances if desired
     */
    private void saveInstance() {
        previousProgramInstances.push(new TickTask(currentProgramInstance));
        futureProgramInstances.clear();
    }
    //@@author

    //@@author A0131884B

    @Override
    /**Deletes the given task using find task name method.
     */
    public synchronized void deleteFindTask(ReadOnlyTask target) throws TaskNotFoundException, DuplicateTaskException {
        saveInstance();
        currentProgramInstance.removeFindTask(target);
        indicateTickTaskModelChanged();
    }
    /**Deletes the given active task using find task index method
    */
    public synchronized void deleteIndexActiveTask(ReadOnlyTask target) throws TaskNotFoundException, DuplicateTaskException {
        saveInstance();
        currentProgramInstance.removeIndexActiveTask(target);
        indicateTickTaskModelChanged();
    }

    public synchronized void deleteIndexCompleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        saveInstance();
        currentProgramInstance.removeIndexCompleteTask(target);
        indicateTickTaskModelChanged();
    }

    @Override
    public void resetActiveData(ReadOnlyTickTask newData) {
        saveInstance();
        currentProgramInstance.resetActiveData(newData);
        indicateTickTaskModelChanged();
    }

    @Override
    public void resetCompleteData(ReadOnlyTickTask newData) {
        saveInstance();
        currentProgramInstance.resetCompleteData(newData);
        indicateTickTaskModelChanged();
    }
    //@@author

    @Override
    public synchronized void addTask(ReadOnlyTask task) throws DuplicateTaskException {
        saveInstance();
        currentProgramInstance.addTask(task);
        updateFilteredListToShowAll();
        indicateTickTaskModelChanged();
    }
    
    //@@author A0147928N
    @Override
    public synchronized void completeTask(ReadOnlyTask task) throws TaskNotFoundException {
        saveInstance();
        currentProgramInstance.completeTask(task);
        updateFilteredListToShowAll();
        indicateTickTaskModelChanged();

    }

    @Override
    public synchronized void restoreTask(ReadOnlyTask task) throws TaskNotFoundException, DuplicateTaskException {
        saveInstance();
        currentProgramInstance.restoreTask(task);
        updateFilteredListToShowAll();
        indicateTickTaskModelChanged();

    }
    //@@author

    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);
        saveInstance();
        currentProgramInstance.updateTask(target, editedTask);
        indicateTickTaskModelChanged();
    }
    
    public String eventClash(ReadOnlyTask t) {
        return currentProgramInstance.eventClash(t);
    }
  
    //@@author A0139819N
    public TickTask getCurrentProgramInstance() {
        return currentProgramInstance;
    }

    public void setCurrentProgramInstance(TickTask currentProgramInstance) {
        this.currentProgramInstance = currentProgramInstance;
    }
    
    public Stack<TickTask> getPreviousProgramInstances() {
        return previousProgramInstances;
    }
    
    public void setPreviousProgramInstances(Stack<TickTask> previousProgramInstances) {
        this.previousProgramInstances = previousProgramInstances;
    }

    public Stack<TickTask> getFutureProgramInstances() {
        return futureProgramInstances;
    }

    public void setFutureProgramInstances(Stack<TickTask> futureProgramInstances) {
        this.futureProgramInstances = futureProgramInstances;
    }
    //@@author
    
    //=========== Filtered Task List Accessors =============================================================

    /**
     * Return a list of {@code ReadOnlyTask} backed by the internal list of {@code TickTask}
     */
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredActiveTaskList() {
        return new UnmodifiableObservableList<>(filteredActiveTasks);
    }

    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredCompletedTaskList() {
        return new UnmodifiableObservableList<>(filteredCompletedTasks);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredActiveTasks.setPredicate(null);
        filteredCompletedTasks.setPredicate(null);
    }

    //@@author A0131884B
    @Override
    public void updateMatchedTaskList(String keywords) {
        updateMatchedTaskList(new PredicateExpression(new newNameQualifier(keywords)));

    }

    private void updateMatchedTaskList(Expression expression) {
        filteredActiveTasks.setPredicate(expression::satisfies);
        filteredCompletedTasks.setPredicate(expression::satisfies);
    }
    //@@author

    //@@author A0138471A
    @Override
    public void updateFilteredListToShowEvent() {

        filteredActiveTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("event");
        });
        filteredCompletedTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("event");
        });

    }
    
    @Override
    public void updateFilteredListToShowDeadline() {

        filteredActiveTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("deadline");
        });
        filteredCompletedTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("deadline");
        });

    }
    
    @Override
    public void updateFilteredListToShowFloating() {

        filteredActiveTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("floating");
        });
        filteredCompletedTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return task.getTaskType().toString().equals("floating");
        });

    }
    
    @Override
    public void updateFilteredListToShowToday() {

        filteredActiveTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return ((task.getDate().getEndDate().equals(LocalDate.now().format(DATE_FORMAT).toString())) || 
            		(task.getDate().getStartDate().equals(LocalDate.now().format(DATE_FORMAT).toString())));
        });
        filteredCompletedTasks.setPredicate((Predicate<? super ReadOnlyTask>) task -> {
            return ((task.getDate().getEndDate().equals(LocalDate.now().format(DATE_FORMAT).toString())) || 
            		(task.getDate().getStartDate().equals(LocalDate.now().format(DATE_FORMAT).toString())));
        });

    }
    
    @Override
    public void saveTickTask(){
    	indicateTickTaskModelChanged();
    }
    //@@author

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameQualifier(keywords)));

    }

    @Override
    public void updateFilteredCompletedTaskList(Set<String> keywords) {
        updateFilteredCompletedTaskList(new PredicateExpression(new NameQualifier(keywords)));

    }

    private void updateFilteredTaskList(Expression expression) {
        filteredActiveTasks.setPredicate(expression::satisfies);
    }

    private void updateFilteredCompletedTaskList(Expression expression) {
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

    //@@author A0131884B
        // state check
        ModelManager other = (ModelManager) obj;
        return currentProgramInstance.equals(other.currentProgramInstance)
                && filteredActiveTasks.equals(other.filteredActiveTasks)
                                && filteredCompletedTasks.equals(other.filteredCompletedTasks);
    }
    //@@author

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

    //@@author A0131884B
    private class newNameQualifier implements Qualifier {
        private String nameKeyWord;


        newNameQualifier(String nameKeyWord) {
            this.nameKeyWord = nameKeyWord;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            String taskName = task.getName().fullName;
            return taskName.toLowerCase().contains(nameKeyWord.toLowerCase());
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWord);
        }
    }
    //@@author

}
