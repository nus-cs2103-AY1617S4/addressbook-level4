package guitests;

import static org.junit.Assert.assertTrue;

//import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.util.SampleDataUtil;
import seedu.whatsnext.testutil.TestUtil;

public class SampleDataTest extends TaskManagerGuiTest {
    //@@author A0154987J
    @Override
    protected TaskManager getInitialData() {
        //return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        //return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void taskManager_dataFileDoesNotExist_loadSampleData() throws Exception {
        commandBox.pressEnter();
        BasicTask[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(eventListPanel.isListMatching(expectedList));
        assertTrue(deadlineListPanel.isListMatching(expectedList));
        assertTrue(floatingListPanel.isListMatching(expectedList));
    }
}
