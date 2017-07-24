package guitests;

 import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.testfx.api.FxToolkit;

import guitests.guihandles.CommandBoxHandle;
import guitests.guihandles.DeadlineListPanelHandle;
import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventListPanelHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.FloatingListPanelHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import guitests.guihandles.MainGuiHandle;
import guitests.guihandles.MainMenuHandle;
import guitests.guihandles.ResultDisplayHandle;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.whatsnext.TestApp;
import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.events.BaseEvent;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.testutil.TestUtil;
import seedu.whatsnext.testutil.TypicalTasks;

/**
  * A GUI Test class for TaskManager.
  */
public abstract class TaskManagerGuiTest {

    /*
      * The TestName Rule makes the current test name available inside test
      * methods
      */
    @Rule
     public TestName name = new TestName();

    /*
      * Handles to GUI elements present at the start up are created in advance
      * for easy access from child classes.
      */
    protected TypicalTasks td = new TypicalTasks();
    protected MainGuiHandle mainGui;
    protected MainMenuHandle mainMenu;
    protected EventListPanelHandle eventListPanel;
    protected DeadlineListPanelHandle deadlineListPanel;
    protected FloatingListPanelHandle floatingListPanel;
    protected ResultDisplayHandle resultDisplay;
    protected CommandBoxHandle commandBox;
    protected Stage stage;

    @BeforeClass
     public static void setupSpec() {
        try {
            FxToolkit.registerPrimaryStage();
            FxToolkit.hideStage();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Before
     public void setup() throws Exception {
        FxToolkit.setupStage((stage) -> {
            mainGui = new MainGuiHandle(new GuiRobot(), stage);
            mainMenu = mainGui.getMainMenu();
            eventListPanel = mainGui.getEventListPanel();
            deadlineListPanel = mainGui.getDeadlineListPanel();
            floatingListPanel = mainGui.getFloatingListPanel();
            resultDisplay = mainGui.getResultDisplay();
            commandBox = mainGui.getCommandBox();
            this.stage = stage;
        });
        EventsCenter.clearSubscribers();
        FxToolkit.setupApplication(() -> new TestApp(this::getInitialData, getDataFileLocation()));
        FxToolkit.showStage();
        while (!stage.isShowing());
        mainGui.focusOnMainApp();
    }

    /**
      * Override this in child classes to set the initial local data. Return null
      * to use the data in the file specified in {@link #getDataFileLocation()}
      */
    protected TaskManager getInitialData() {
        TaskManager tm = new TaskManager();
        TypicalTasks.loadTaskManagerWithSampleData(tm);
        return tm;
    }

    /**
      * Override this in child classes to set the data file location.
      */
    protected String getDataFileLocation() {
        return TestApp.SAVE_LOCATION_FOR_TESTING;
    }

    @After
     public void cleanup() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
      * Asserts the task shown in the card is same as the given task
      */
    public void assertMatching(BasicTaskFeatures task, EventTaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
      * Asserts the task shown in the card is same as the given task
      */
    public void assertMatching(BasicTaskFeatures task, DeadlineTaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
      * Asserts the task shown in the card is same as the given task
      */
    public void assertMatching(BasicTaskFeatures task, FloatingTaskCardHandle card) {
        assertTrue(TestUtil.compareCardAndTask(card, task));
    }

    /**
      * Asserts the size of the task list is equal to the given number.
      */
    protected void assertListSize(int size) {
        int numberOfTask = eventListPanel.getNumberOfTask() + deadlineListPanel.getNumberOfTask()
             + floatingListPanel.getNumberOfTask();
        assertEquals(size, numberOfTask);
    }

    /**
      * Asserts the message shown in the Result Display area is same as the given
      * string.
      */
    protected void assertResultMessage(String expected) {
        assertEquals(expected, resultDisplay.getText());
    }

    public void raise(BaseEvent e) {
        // JUnit doesn't run its test cases on the UI thread. Platform.runLater
        // is used to post event on the UI thread.
        Platform.runLater(() -> EventsCenter.getInstance().post(e));
    }
}
