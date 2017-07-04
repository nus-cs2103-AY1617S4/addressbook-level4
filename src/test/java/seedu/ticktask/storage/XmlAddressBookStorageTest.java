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
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.storage.XmlTickTaskStorage;
import seedu.ticktask.testutil.TypicalTasks;

public class XmlAddressBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyTickTask> readAddressBook(String filePath) throws Exception {
        return new XmlTickTaskStorage(filePath).readTickTask(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        TypicalTasks td = new TypicalTasks();
        TickTask original = td.getTypicalAddressBook();
        XmlTickTaskStorage xmlAddressBookStorage = new XmlTickTaskStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveTickTask(original, filePath);
        ReadOnlyTickTask readBack = xmlAddressBookStorage.readTickTask(filePath).get();
        assertEquals(original, new TickTask(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addTask(new Task(td.hoon));
        original.removePerson(new Task(td.alice));
        xmlAddressBookStorage.saveTickTask(original, filePath);
        readBack = xmlAddressBookStorage.readTickTask(filePath).get();
        assertEquals(original, new TickTask(readBack));

        //Save and read without specifying file path
        original.addTask(new Task(td.ida));
        xmlAddressBookStorage.saveAddressBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readTickTask().get(); //file path not specified
        assertEquals(original, new TickTask(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    private void saveAddressBook(ReadOnlyTickTask addressBook, String filePath) throws IOException {
        new XmlTickTaskStorage(filePath).saveTickTask(addressBook, addToTestDataPathIfNotNull(filePath));
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new TickTask(), null);
    }


}
