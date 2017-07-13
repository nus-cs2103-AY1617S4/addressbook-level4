/*
 * package guitests;
 *
 * import static org.junit.Assert.assertEquals; import static
 * org.junit.Assert.assertTrue;
 *
 * import java.util.concurrent.TimeoutException;
 *
 * import org.junit.After; import org.junit.Before; import
 * org.junit.BeforeClass; import org.junit.Rule; import
 * org.junit.rules.TestName; import org.testfx.api.FxToolkit;
 *
 * import guitests.guihandles.BrowserPanelHandle; import
 * guitests.guihandles.CommandBoxHandle; import
 * guitests.guihandles.MainMenuHandle; import
 * guitests.guihandles.MainWindowHandle; import
 * guitests.guihandles.PersonListPanelHandle; import
 * guitests.guihandles.ResultDisplayHandle; import
 * guitests.guihandles.StatusBarFooterHandle; import
 * guitests.guihandles.TaskCardHandle; import javafx.application.Platform;
 * import javafx.stage.Stage; import teamthree.twodo.TestApp; import
 * teamthree.twodo.commons.core.EventsCenter; import
 * teamthree.twodo.commons.events.BaseEvent; import
 * teamthree.twodo.model.TaskBook; import
 * teamthree.twodo.model.task.ReadOnlyTask; import
 * teamthree.twodo.testutil.TestUtil; import
 * teamthree.twodo.testutil.TypicalPersons;
 *
 *//**
   * A GUI Test class for TaskBook.
   */
/*
 * public abstract class TaskBookGuiTest {
 *
 * The TestName Rule makes the current test name available inside test methods
 *
 * @Rule public TestName name = new TestName();
 *
 * protected TypicalPersons td = new TypicalPersons();
 *
 *
 * Handles to GUI elements present at the start up are created in advance for
 * easy access from child classes.
 *
 * protected MainWindowHandle mainGui; protected MainMenuHandle mainMenu;
 * protected PersonListPanelHandle personListPanel; protected
 * ResultDisplayHandle resultDisplay; protected CommandBoxHandle commandBox;
 * protected BrowserPanelHandle browserPanel; protected StatusBarFooterHandle
 * statusBarFooter;
 *
 * protected Stage stage;
 *
 * @BeforeClass public static void setupSpec() { try {
 * FxToolkit.registerPrimaryStage(); FxToolkit.hideStage(); } catch
 * (TimeoutException e) { e.printStackTrace(); } }
 *
 * @Before public void setup() throws Exception { FxToolkit.setupStage((stage)
 * -> { mainGui = new MainWindowHandle(new GuiRobot(), stage); mainMenu =
 * mainGui.getMainMenu(); personListPanel = mainGui.getPersonListPanel();
 * resultDisplay = mainGui.getResultDisplay(); commandBox =
 * mainGui.getCommandBox(); browserPanel = mainGui.getBrowserPanel();
 * statusBarFooter = mainGui.getStatusBarFooter(); this.stage = stage; });
 * EventsCenter.clearSubscribers(); FxToolkit.setupApplication(() -> new
 * TestApp(this::getInitialData, getDataFileLocation())); FxToolkit.showStage();
 * while (!stage.isShowing()); mainGui.focusOnMainApp(); }
 *
 *//**
   * Override this in child classes to set the initial local data. Return null
   * to use the data in the file specified in {@link #getDataFileLocation()}
   */
/*
 * protected TaskBook getInitialData() { TaskBook ab = new TaskBook();
 * TypicalPersons.loadAddressBookWithSampleData(ab); return ab; }
 *
 *//**
   * Override this in child classes to set the data file location.
   */
/*
 * protected String getDataFileLocation() { return
 * TestApp.SAVE_LOCATION_FOR_TESTING; }
 *
 * @After public void cleanup() throws TimeoutException {
 * FxToolkit.cleanupStages(); }
 *
 *//**
   * Asserts the person shown in the card is same as the given person
   */
/*
 * public void assertMatching(ReadOnlyTask person, TaskCardHandle card) {
 * assertTrue(TestUtil.compareCardAndTask(card, person)); }
 *
 *//**
   * Asserts the size of the person list is equal to the given number.
   */
/*
 * protected void assertListSize(int size) { int numberOfPeople =
 * personListPanel.getNumberOfPeople(); assertEquals(size, numberOfPeople); }
 *
 *//**
   * Asserts the message shown in the Result Display area is same as the given
   * string.
   *//*
     * protected void assertResultMessage(String expected) {
     * assertEquals(expected, resultDisplay.getText()); }
     *
     * public void raise(BaseEvent e) { //JUnit doesn't run its test cases on
     * the UI thread. Platform.runLater is used to post event on the UI thread.
     * Platform.runLater(() -> EventsCenter.getInstance().post(e)); } }
     */
