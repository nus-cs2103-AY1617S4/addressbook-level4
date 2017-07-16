package seedu.ticktask.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ticktask.model.TickTask;
import seedu.ticktask.storage.XmlSerializableTickTask;
import seedu.ticktask.testutil.TickTaskBuilder;
import seedu.ticktask.testutil.TestUtil;

public class XmlUtilTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/XmlUtilTest/");
    private static final File EMPTY_FILE = new File(TEST_DATA_FOLDER + "empty.xml");
    private static final File MISSING_FILE = new File(TEST_DATA_FOLDER + "missing.xml");
    private static final File VALID_FILE = new File(TEST_DATA_FOLDER + "validTickTask.xml");
    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempTickTask.xml"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, TickTask.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, TickTask.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, TickTask.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        XmlSerializableTickTask dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableTickTask.class);
        assertEquals(0, dataFromFile.getTagList().size());
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new TickTask());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new TickTask());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        TEMP_FILE.createNewFile();
        XmlSerializableTickTask dataToWrite = new XmlSerializableTickTask(new TickTask());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableTickTask dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTickTask.class);
        assertEquals((new TickTask(dataToWrite)).toString(), (new TickTask(dataFromFile)).toString());
        //TODO: use equality instead of string comparisons

        TickTaskBuilder builder = new TickTaskBuilder(new TickTask());
        dataToWrite = new XmlSerializableTickTask(
                builder.withTask(TestUtil.generateSampleTaskData().get(0)).withTag("Friends").build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableTickTask.class);
        assertEquals((new TickTask(dataToWrite)).toString(), (new TickTask(dataFromFile)).toString());
    }
}