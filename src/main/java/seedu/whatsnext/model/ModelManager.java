package seedu.whatsnext.model;

import static seedu.whatsnext.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
 * Represents the in-memory model of the Task Manager data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskManager taskManager;
    private final FilteredList<BasicTask> filteredTasks;

    private Stack<TaskManager> undoTaskManager;
    private Stack<TaskManager> redoTaskManager;

    private UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskManager taskManager, UserPrefs userPrefs) {
        super();
        requireAllNonNull(taskManager, userPrefs);

        logger.fine("Initializing with Task Manager: " + taskManager + " and user prefs " + userPrefs);
        this.taskManager = new TaskManager(taskManager);
        filteredTasks = new FilteredList<>(this.taskManager.getTaskList());
        undoTaskManager = new Stack<TaskManager>();
        redoTaskManager = new Stack<TaskManager>();
        this.userPrefs = userPrefs;
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

    // @@author A0154986L
    /** Resets previous task manager instance. */
    @Override
    public void resetPrevTaskManager() {
        undoTaskManager();
        redoTaskManager.clear();
        indicateTaskManagerChanged();
    }

    // @@author A0154986L
    /** Returns current reminder setting. */
    @Override
    public String getReminderSetting() {
        return userPrefs.getReminderSetting();
    }

    // @@author A0154986L
    /** Sets new reminder setting. */
    @Override
    public void setReminderSetting(String newReminderSetting) {
        userPrefs.updateLastUsedReminderSetting(newReminderSetting);
    }

    // @@author A0149894H
    /** Sets new task manager file path. */
    @Override
    public void setTaskManagerFilePath(String newFilePath) {
        userPrefs.setTaskManagerFilePath(newFilePath);
    }

    //@@author A0149894H
    /** Returns current task manager file path. */
    @Override
    public String getTaskManagerFilePath() {
        return userPrefs.getTaskManagerFilePath();
    }

    @Override
    public ReadOnlyTaskManager getTaskManager() {
        return taskManager;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskManagerChanged() {
        raise(new TaskManagerChangedEvent(taskManager));
    }

    //@@author A01498494H
    /**Re-saves data when file path is changed. */
    @Override
    public void saveTaskManager() {
        indicateTaskManagerChanged();
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
        indicateTaskManagerChanged();
    }

    @Override
    public void updateTask(BasicTaskFeatures target, BasicTaskFeatures editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        saveInstance();
        requireAllNonNull(target, editedTask);
        taskManager.updateTask(target, editedTask);
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
        indicateTaskManagerChanged();
    }

    @Override
    public void updateFilteredTaskList(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new NameAndTagQualifier(keywords)));
        indicateTaskManagerChanged();
    }

    private void updateFilteredTaskList(Expression expression) {
        filteredTasks.setPredicate(expression::satisfies);
    }

    @Override
    public void updateFilteredTaskListForInitialView() {
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(false)));
    }

    // @@author A0154986L
    @Override
    public void updateFilteredTaskListToShowByCompletion(boolean isComplete) {
        updateFilteredTaskList(new PredicateExpression(new CompletedQualifier(isComplete)));
        indicateTaskManagerChanged();
    }

    // @@author A0154986L
    /**
     * Updates the filter of the filtered task list to filter for reminder pop up window.
     */
    @Override
    public void updateFilteredTaskListForReminder() {
        updateFilteredTaskList(new PredicateExpression(new ReminderQualifier()));
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
    /*
     * Finds tasks by completion status.
     */
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
            if (isComplete) {
                return "completion status = " + String.join(", ", "true");
            } else {
                return "completion status = " + String.join(", ", "false");
            }
        }
    }

    // @@author A0154986L
    /*
     * Finds the tasks for reminder pop up window.
     */
    private class ReminderQualifier implements Qualifier {

        private Date remindStart = new Date();
        private Date remindEnd = new Date();
        private Calendar cal = Calendar.getInstance();

        private Pattern p = Pattern.compile("(\\d+)\\s+(.*?)s?");

        @SuppressWarnings("serial")
        private Map<String, Integer> fields = new HashMap<String, Integer>() {
            {
                put("minute", Calendar.MINUTE);
                put("hour",   Calendar.HOUR);
                put("day",    Calendar.DATE);
                put("week",   Calendar.WEEK_OF_YEAR);
                put("month",  Calendar.MONTH);
                put("year",   Calendar.YEAR);
            }
        };

        @Override
        public boolean run(BasicTaskFeatures basicTaskFeatures) {
            cal.setTime(remindStart);
            remindStart = cal.getTime();

            if (getReminderSetting() == null || getReminderSetting() == "3 day") {
                cal.add(Calendar.DATE, 3);
                remindEnd = cal.getTime();
            } else {
                Matcher m = p.matcher(getReminderSetting());
                if (m.matches()) {
                    int amount = Integer.parseInt(m.group(1));
                    String unit = m.group(2);
                    cal.add(fields.get(unit), amount);
                    remindEnd = cal.getTime();
                }
            }

            return (basicTaskFeatures.getTaskType().equals("event")
                    && !basicTaskFeatures.getStartDateTime().isBefore(remindStart)
                    && basicTaskFeatures.getStartDateTime().isBefore(remindEnd))
                    || (basicTaskFeatures.getTaskType().equals("deadline")
                            && !basicTaskFeatures.getEndDateTime().isBefore(remindStart)
                            && basicTaskFeatures.getEndDateTime().isBefore(remindEnd));
        }

        @Override
        public String toString() {
            if (getReminderSetting() == null || getReminderSetting() == "3 day") {
                cal.add(Calendar.DATE, 3);
                remindEnd = cal.getTime();
            } else {
                Matcher m = p.matcher(getReminderSetting());
                if (m.matches()) {
                    int amount = Integer.parseInt(m.group(1));
                    String unit = m.group(2);
                    cal.add(fields.get(unit), amount);
                    remindEnd = cal.getTime();
                }
            }
            return remindEnd.toString();
        }
    }

    //@@author A0142675B
    /**
     * Finds the tasks either by name or tag.
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
