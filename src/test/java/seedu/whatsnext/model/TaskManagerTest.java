package seedu.whatsnext.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) and unit tests for TaskManager.
 */
public class TaskManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TaskManager taskManager = new TaskManager();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taskManager.getTaskList());
        assertEquals(Collections.emptyList(), taskManager.getTagList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        taskManager.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTaskManager_replacesData() {
        TaskManager newData = new TypicalTasks().getTypicalTaskManager();
        taskManager.resetData(newData);
        assertEquals(newData, taskManager);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsAssertionError() {
        TypicalTasks td = new TypicalTasks();
        // Repeat td.alice twice
        List<BasicTask> newTasks = Arrays.asList(new BasicTask(td.completeCS2103Assignment),
                new BasicTask(td.completeCS2103Assignment));
        List<Tag> newTags = new ArrayList<>(td.completeCS2103Assignment.getTags());
        TaskManagerStub newData = new TaskManagerStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    @Test
    public void resetData_withDuplicateTags_throwsAssertionError() {
        TaskManager typicalTaskManager = new TypicalTasks().getTypicalTaskManager();
        List<BasicTask> newTasks = typicalTaskManager.getTaskList();
        List<Tag> newTags = new ArrayList<>(typicalTaskManager.getTagList());
        // Repeat the first tag twice
        newTags.add(newTags.get(0));
        TaskManagerStub newData = new TaskManagerStub(newTasks, newTags);

        thrown.expect(AssertionError.class);
        taskManager.resetData(newData);
    }

    /**
     * A stub ReadOnlyTaskManager whose tasks and tags lists can violate interface constraints.
     */
    private static class TaskManagerStub implements ReadOnlyTaskManager {
        private final ObservableList<BasicTask> tasks = FXCollections.observableArrayList();
        private final ObservableList<Tag> tags = FXCollections.observableArrayList();

        TaskManagerStub(Collection<? extends BasicTask> tasks, Collection<? extends Tag> tags) {
            this.tasks.setAll(tasks);
            this.tags.setAll(tags);
        }

        @Override
        public ObservableList<BasicTask> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Tag> getTagList() {
            return tags;
        }
    }

}
