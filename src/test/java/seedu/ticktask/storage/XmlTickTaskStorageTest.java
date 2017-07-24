package seedu.ticktask.storage;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.ticktask.commons.exceptions.DataConversionException;
import seedu.ticktask.commons.util.FileUtil;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.testutil.TypicalTasks;

public class XmlTickTaskStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlTickTaskStorageTest/");
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTickTask_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTickTask(null);
    }

    private java.util.Optional<ReadOnlyTickTask> readTickTask(String filePath) throws Exception {
        return new XmlTickTaskStorage(filePath).readTickTask(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTickTask("NonExistentFile.xml").isPresent());
    }

    @Test
    public void readAndSaveTickTask_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTickTask.xml";
        TypicalTasks td = new TypicalTasks();
        TickTask original = td.getTypicalTickTask();
        XmlTickTaskStorage xmlTickTaskStorage = new XmlTickTaskStorage(filePath);

        //Save in new file and read back
        xmlTickTaskStorage.saveTickTask(original, filePath);
        ReadOnlyTickTask readBack = xmlTickTaskStorage.readTickTask(filePath).get();
        assertEquals(original, new TickTask(readBack));

        //Modify data, overwrite exiting file, and read back
        original.removeIndexActiveTask(new Task(td.dotutorial));
        xmlTickTaskStorage.saveTickTask(original, filePath);
        readBack = xmlTickTaskStorage.readTickTask(filePath).get();
        assertEquals(original, new TickTask(readBack));

        //Save and read without specifying file path
        xmlTickTaskStorage.saveTickTask(original); //file path not specified
        readBack = xmlTickTaskStorage.readTickTask().get(); //file path not specified
        assertEquals(original, new TickTask(readBack));

    }

    @Test
    public void saveTickTask_nullTickTask_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveTickTask(null, "SomeFile.xml");
    }

    private void saveTickTask(ReadOnlyTickTask ticktaskBook, String filePath) throws IOException {
        new XmlTickTaskStorage(filePath).saveTickTask(ticktaskBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveTickTask_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveTickTask(new TickTask(), null);
    }


}