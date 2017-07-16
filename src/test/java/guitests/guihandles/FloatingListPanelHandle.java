package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.whatsnext.TestApp;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.testutil.TestUtil;

/**
 * Provides a handle for the panel containing the task list.
 */
public class FloatingListPanelHandle extends GuiHandle {

    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String FLOATING_TASK_LIST_VIEW_ID = "#floatingListView";

    public FloatingListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Pair<BasicTaskFeatures, Integer>> getSelectedTasks() {
        ListView<Pair<BasicTaskFeatures, Integer>> taskList = getFloatingListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<Pair<BasicTaskFeatures, Integer>> getFloatingListView() {
        ListView<Pair<BasicTaskFeatures, Integer>> floatingListView = getNode(FLOATING_TASK_LIST_VIEW_ID);
        return floatingListView;
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
        List<BasicTaskFeatures> floatingTasks = new ArrayList<>(Arrays.asList(tasks));
        for (int index = 0; index < floatingTasks.size(); index++) {
            if (!floatingTasks.get(index).getTaskType().equals("floating")) {
                floatingTasks.remove(index);
                index--;
            }
        }
        BasicTaskFeatures[] taskArray = floatingTasks.toArray(new BasicTaskFeatures[floatingTasks.size()]);
        if (taskArray.length + startPosition != getFloatingListView().getItems().size()) {
            throw new IllegalArgumentException(
                    "List size mismatched\n" + "Expected " + (getFloatingListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, taskArray));
        for (int i = 0; i < taskArray.length; i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getFloatingListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getFloatingTaskCardHandle(startPosition + i), taskArray[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Clicks on the ListView.
     */
    public void clickOnListView() {
        Point2D point = TestUtil.getScreenMidPoint(getFloatingListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order)
     * at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, BasicTaskFeatures... tasks) {
        List<Pair<BasicTaskFeatures, Integer>> tasksInList = getFloatingListView().getItems();

        // Return false if the list in panel is too short to contain the given
        // list
        if (startPosition + tasks.length > tasksInList.size()) {
            return false;
        }

        // Return false if any of the persons doesn't match
        for (int i = 0; i < tasks.length; i++) {
            if (!tasksInList.get(startPosition + i).getKey().getName().fullTaskName
                    .equals(tasks[i].getName().fullTaskName)) {
                return false;
            }
        }

        return true;
    }

    public FloatingTaskCardHandle navigateToFloatingTask(String taskname) {
        guiRobot.sleep(500); // Allow a bit of time for the list to be updated
        final Optional<Pair<BasicTaskFeatures, Integer>> task = getFloatingListView().getItems().stream()
                .filter(p -> p.getKey().getName().toString().equals(taskname)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task name not found: " + taskname);
        }

        return navigateToFloatingTask(task.get().getKey());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public FloatingTaskCardHandle navigateToFloatingTask(BasicTaskFeatures task) {
        int index = getFloatingTaskIndex(task);

        guiRobot.interact(() -> {
            getFloatingListView().scrollTo(index);
            guiRobot.sleep(150);
            getFloatingListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getFloatingTaskCardHandle(task);
    }

    /**
     * Returns the position of the person given, {@code NOT_FOUND} if not found
     * in the list.
     */
    public int getFloatingTaskIndex(BasicTaskFeatures targetTask) {
        List<Pair<BasicTaskFeatures, Integer>> tasksInList = getFloatingListView().getItems();
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
    public BasicTaskFeatures getFloatingTask(int index) {
        return getFloatingListView().getItems().get(index).getKey();
    }

    public FloatingTaskCardHandle getFloatingTaskCardHandle(int index) {
        return getFloatingTaskCardHandle(new BasicTask(getFloatingListView().getItems().get(index).getKey()));
    }

    public FloatingTaskCardHandle getFloatingTaskCardHandle(BasicTaskFeatures task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new FloatingTaskCardHandle(guiRobot, primaryStage, n).isSameTask(task)).findFirst();
        if (taskCardNode.isPresent()) {
            return new FloatingTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTask() {
        return getFloatingListView().getItems().size();
    }
}

