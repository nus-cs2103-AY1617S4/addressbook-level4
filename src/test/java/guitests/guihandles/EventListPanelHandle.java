package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Collections;

import guitests.GuiRobot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.whatsnext.TestApp;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.testutil.TestUtil;
//author A0154987J
/**
 * Provides a handle for the panel containing the task list.
 */
public class EventListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String FLOATING_TASK_LIST_VIEW_ID = "#eventListView";

    public EventListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Pair<BasicTaskFeatures, Integer>> getSelectedTasks() {
        ListView<Pair<BasicTaskFeatures, Integer>> taskList = getEventListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<Pair<BasicTaskFeatures, Integer>> getEventListView() {
        ListView<Pair<BasicTaskFeatures, Integer>> eventListView = getNode(FLOATING_TASK_LIST_VIEW_ID);
        return eventListView;
    }

    /**
     * Returns true if the list is showing the task details correctly and in
     * correct order.
     *
     * @param tasks
     *            A list of tasks in the correct order.
     */
    public boolean isListMatching(BasicTaskFeatures... tasks) {
        return this.isListMatching(0, tasks);
    }

    /**
     * Returns true if the list is showing the task details correctly and in
     * correct order.
     *
     * @param startPosition
     *            The starting position of the sub list.
     * @param persons
     *            A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, BasicTaskFeatures... tasks) throws IllegalArgumentException {
        List<BasicTaskFeatures> eventTasks = new ArrayList<>(Arrays.asList(tasks));
        ObservableList<BasicTaskFeatures> taskList = FXCollections.observableArrayList();
        for (int index = 0; index < eventTasks.size(); index++) {
            if (eventTasks.get(index).getTaskType().equals("event")) {
                taskList.add(eventTasks.get(index));
            }
        }

        Collections.sort(taskList, new EndDateTimeComparator());
        Collections.sort(taskList, new StartDateTimeComparator());

        if (taskList.size() + startPosition != getEventListView().getItems().size()) {

            throw new IllegalArgumentException(
                    "List size mismatched\n" + "Expected " + (getEventListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, taskList));
        for (int i = 0; i < taskList.size(); i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getEventListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getEventTaskCardHandle(startPosition + i), eventTasks.get(i))) {
                return false;
            }
        }
        return true;
    }

    class StartDateTimeComparator implements Comparator<BasicTask> {
        public int compare(BasicTask c1, BasicTask c2) {
            if (c1.getStartDateTime().isBefore(c2.getStartDateTime()) == true) {
                return -1;
            }
            return 1;
        }
    }
    class EndDateTimeComparator implements Comparator<BasicTask> {
        public int compare(BasicTask c1, BasicTask c2) {
            if (c1.getEndDateTime().isBefore(c2.getEndDateTime()) == true) {
                return -1;
            }
            return 1;
        }
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getEventListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order)
     * at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ObservableList<BasicTaskFeatures> taskList) {
        List<Pair<BasicTaskFeatures, Integer>> tasksInList = getEventListView().getItems();

        // Return false if the list in panel is too short to contain the given
        // list
        if (startPosition + taskList.size() > tasksInList.size()) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < taskList.size(); i++) {
            if (!tasksInList.get(startPosition + i).getKey().getName().toString()
                    .equals(taskList.get(i).getName().toString())) {
                return false;
            }
        }

        return true;
    }

    public EventTaskCardHandle navigateToEventTask(String taskname) {
        guiRobot.sleep(500); // Allow a bit of time for the list to be updated
        final Optional<Pair<BasicTaskFeatures, Integer>> task = getEventListView().getItems().stream()
                .filter(p -> p.getKey().getName().toString().equals(taskname)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task name not found: " + taskname);
        }

        return navigateToEventTask(task.get().getKey());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public EventTaskCardHandle navigateToEventTask(BasicTaskFeatures task) {
        int index = getEventTaskIndex(task);

        guiRobot.interact(() -> {
            getEventListView().scrollTo(index);
            guiRobot.sleep(150);
            getEventListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getEventTaskCardHandle(task);
    }

    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found
     * in the list.
     */
    public int getEventTaskIndex(BasicTaskFeatures targetTask) {
        List<Pair<BasicTaskFeatures, Integer>> tasksInList = getEventListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getKey().getName().equals(targetTask.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a person from the list by index
     */
    public BasicTaskFeatures getEventTask(int index) {
        return getEventListView().getItems().get(index).getKey();
    }

    public EventTaskCardHandle getEventTaskCardHandle(int index) {
        return getEventTaskCardHandle(new BasicTask(getEventListView().getItems().get(index).getKey()));
    }

    public EventTaskCardHandle getEventTaskCardHandle(BasicTaskFeatures task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new EventTaskCardHandle(guiRobot, primaryStage, n).isSameTask(task)).findFirst();
        if (taskCardNode.isPresent()) {
            return new EventTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTask() {
        return getEventListView().getItems().size();
    }
}

