package seedu.whatsnext.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.whatsnext.testutil.TaskManagerBuilder;
import seedu.whatsnext.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) and unit tests for the model manager.
 */
public class ModelManagerTest {

    private TypicalTasks typicalTasks = new TypicalTasks();

    @Test
    public void equals() throws Exception {
        TaskManager taskManager = new TaskManagerBuilder().withTask(typicalTasks.completeCS2103Assignment)
                .withTask(typicalTasks.meetJohnForDinner).build();
        TaskManager differentTaskManager = new TaskManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(taskManager, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(taskManager, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different taskmanager -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTaskManager, userPrefs)));

        // different filteredList -> returns false
        modelManager.updateFilteredTaskList(new HashSet<>(
                Arrays.asList(typicalTasks.completeCS2103Assignment.getName().toString().split(" "))));
        assertFalse(modelManager.equals(new ModelManager(taskManager, userPrefs)));
        modelManager.updateFilteredListToShowAll(); // resets modelManager to initial state for upcoming tests

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTaskManagerName("differentName");
        assertTrue(modelManager.equals(new ModelManager(taskManager, differentUserPrefs)));
    }
}
