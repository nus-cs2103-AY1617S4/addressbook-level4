package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.util.SampleDataUtil;
import seedu.ticktask.testutil.TestUtil;

public class SampleDataTest extends TickTaskGuiTest {
    @Override
    protected TickTask getInitialData() {
        // return null to force test app to load data from file only
        return null;
    }

    @Override
    protected String getDataFileLocation() {
        // return a non-existent file location to force test app to load sample data
        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
    }

    @Test
    public void addressBook_dataFileDoesNotExist_loadSampleData() throws Exception {
        Task[] expectedList = SampleDataUtil.getSampleTasks();
        assertTrue(personListPanel.isListMatching(expectedList));
    }
}
