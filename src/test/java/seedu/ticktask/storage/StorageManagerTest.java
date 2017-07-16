package seedu.ticktask.storage;


import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.ticktask.commons.events.model.TickTaskChangedEvent;
import seedu.ticktask.commons.events.storage.DataSavingExceptionEvent;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.testutil.EventsCollector;
import seedu.ticktask.testutil.TypicalTasks;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlTickTaskStorage ticktaskBookStorage = new XmlTickTaskStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(ticktaskBookStorage, userPrefsStorage);
    }


    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void ticktaskBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTickTaskStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTickTaskStorageTest} class.
         */
        TickTask original = new TypicalTasks().getTypicalTickTask();
        storageManager.saveTickTask(original);
        ReadOnlyTickTask retrieved = storageManager.readTickTask().get();
        assertEquals(original, new TickTask(retrieved));
    }

    @Test
    public void getTickTaskFilePath() {
        assertNotNull(storageManager.getTickTaskFilePath());
    }

    @Test
    public void handleTickTaskChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTickTaskStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        EventsCollector eventCollector = new EventsCollector();
        storage.handleTickTaskChangedEvent(new TickTaskChangedEvent(new TickTask()));
        assertTrue(eventCollector.get(0) instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTickTaskStorageExceptionThrowingStub extends XmlTickTaskStorage {

        public XmlTickTaskStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveTickTask(ReadOnlyTickTask ticktaskBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}