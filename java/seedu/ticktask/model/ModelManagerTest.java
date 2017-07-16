package seedu.ticktask.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.ticktask.testutil.TickTaskBuilder;
import seedu.ticktask.testutil.TypicalTasks;

public class ModelManagerTest {

    private TypicalTasks typicalTasks = new TypicalTasks();

    @Test
    public void equals() throws Exception {
        TickTask tickTask = new TickTaskBuilder().withTask(typicalTasks.meetgirlfriend)
                .withTask(typicalTasks.washdog).build();
        TickTask differentTickTask = new TickTask();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(tickTask, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(tickTask, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different tickTask -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentTickTask, userPrefs)));

        // different filteredList -> returns false
        modelManager.updateFilteredTaskList(new HashSet<>(
                Arrays.asList(typicalTasks.dotutorial.getName().fullName.split(" "))));
        assertFalse(modelManager.equals(new ModelManager(tickTask, userPrefs)));
        modelManager.updateFilteredListToShowAll(); // resets modelManager to initial state for upcoming tests

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setTickTaskName("differentName");
        assertTrue(modelManager.equals(new ModelManager(tickTask, differentUserPrefs)));
    }
}