package teamthree.twodo.model;

import static teamthree.twodo.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import teamthree.twodo.commons.core.ComponentManager;
import teamthree.twodo.commons.core.LogsCenter;
import teamthree.twodo.commons.core.UnmodifiableObservableList;
import teamthree.twodo.commons.events.model.TaskBookChangedEvent;
import teamthree.twodo.commons.util.StringUtil;
import teamthree.twodo.logic.commands.ListCommand.AttributeInputted;
import teamthree.twodo.model.tag.Tag;
import teamthree.twodo.model.task.Deadline;
import teamthree.twodo.model.task.ReadOnlyTask;
import teamthree.twodo.model.task.exceptions.DuplicateTaskException;
import teamthree.twodo.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data. All changes to any
 * model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TaskBook taskBook;
    private final FilteredList<ReadOnlyTask> filteredTasks;

    /**
     * Initializes a ModelManager with the given taskBook and userPrefs.
     */
    public ModelManager(ReadOnlyTaskBook taskBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(taskBook, userPrefs);

        logger.fine("Initializing with task book: " + taskBook + " and user prefs " + userPrefs);

        this.taskBook = new TaskBook(taskBook);
        filteredTasks = new FilteredList<>(this.taskBook.getTaskList());
    }

    public ModelManager() {
        this(new TaskBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyTaskBook newData) {
        taskBook.resetData(newData);
        indicateTaskBookChanged();
    }

    @Override
    public ReadOnlyTaskBook getTaskBook() {
        return taskBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateTaskBookChanged() {
        raise(new TaskBookChangedEvent(taskBook));
    }

    @Override
    public synchronized void deleteTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.removeTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public synchronized void addTask(ReadOnlyTask person) throws DuplicateTaskException {
        taskBook.addTask(person);
        updateFilteredListToShowAll();
        indicateTaskBookChanged();
    }

    @Override
    public void markTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.markTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public void unmarkTask(ReadOnlyTask target) throws TaskNotFoundException {
        taskBook.unmarkTask(target);
        indicateTaskBookChanged();
    }

    @Override
    public void saveTaskBook() {
        indicateTaskBookChanged();
    }

    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        taskBook.updateTask(target, editedTask);
        indicateTaskBookChanged();
    }

    // =========== Filtered Task List Accessors
    // =============================================================

    /**
     * Return a list of {@code ReadOnlyTask} backed by the internal list of
     * {@code taskBook}
     */
    @Override
    public UnmodifiableObservableList<ReadOnlyTask> getFilteredTaskList() {
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
    public void updateFilteredTaskListExtensively(Set<String> keywords) {
        updateFilteredTaskList(new PredicateExpression(new TotalQualifier(keywords)));
    }

    @Override
    public void updateFilteredListToShowPeriod(Deadline deadline, AttributeInputted attInput) {
        updateFilteredTaskList(new PredicateExpression(new PeriodQualifier(deadline, attInput)));
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
        return taskBook.equals(other.taskBook) && filteredTasks.equals(other.filteredTasks);
    }

    // ========== Inner classes/interfaces used for filtering
    // =================================================

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
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)).findAny()
                    .isPresent();
        }

        @Override
        public String toString() {
            return "name=" + String.join(", ", nameKeyWords);
        }
    }

    private class TotalQualifier implements Qualifier {
        private Set<String> keyWords;

        TotalQualifier(Set<String> keyWords) {
            this.keyWords = keyWords;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            return (nameQualifies(task) || descriptionQualifies(task) || tagsQualifies(task));
        }

        private boolean nameQualifies(ReadOnlyTask task) {
            return keyWords.stream()
                    .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getName().fullName, keyword)).findAny()
                    .isPresent();
        }

        private boolean descriptionQualifies(ReadOnlyTask task) {
            if (task.getDeadline().isPresent()) {
                return keyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(task.getDescription().value, keyword))
                        .findAny().isPresent();
            } else {
                return false;
            }
        }

        private boolean tagsQualifies(ReadOnlyTask task) {
            boolean qualifies = false;
            Set<Tag> tags = task.getTags();
            Iterator<Tag> tagIterator = tags.iterator();
            while (!qualifies && tagIterator.hasNext()) {
                qualifies = keyWords.stream()
                        .filter(keyword -> StringUtil.containsWordIgnoreCase(tagIterator.next().tagName, keyword))
                        .findAny().isPresent();
            }
            return qualifies;
        }

        @Override
        public String toString() {
            return "keywords=" + String.join(", ", keyWords);
        }

    }

    private class PeriodQualifier implements Qualifier {
        private Deadline deadlineToCheck;
        private AttributeInputted attInput;

        PeriodQualifier(Deadline deadline, AttributeInputted attInput) {
            this.deadlineToCheck = deadline;
            this.attInput = attInput;
        }

        @Override
        public boolean run(ReadOnlyTask task) {
            if (!task.getDeadline().isPresent()) {
                return false;
            } else {
                switch (attInput) {
                case START:
                    return task.getDeadline().get().getStartDate().after(deadlineToCheck.getStartDate());
                case END:
                    return task.getDeadline().get().getStartDate().before(deadlineToCheck.getEndDate());
                case BOTH:
                    return task.getDeadline().get().getStartDate().after(deadlineToCheck.getStartDate())
                            && task.getDeadline().get().getStartDate().before(deadlineToCheck.getEndDate());
                default:
                    return false;
                }
            }

        }
    }

}
