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

/**
 * Provides a handle for the panel containing the task list.
 */
public class DeadlineListPanelHandle extends GuiHandle {

    //author A0154987J
    public static final int NOT_FOUND = -1;
    public static final String CARD_PANE_ID = "#cardPane";

    private static final String DEADLINE_TASK_LIST_VIEW_ID = "#deadlineListView";

    public DeadlineListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<Pair<BasicTaskFeatures, Integer>> getSelectedTasks() {
        ListView<Pair<BasicTaskFeatures, Integer>> taskList = getDeadlineListView();
        return taskList.getSelectionModel().getSelectedItems();
    }

    public ListView<Pair<BasicTaskFeatures, Integer>> getDeadlineListView() {
        ListView<Pair<BasicTaskFeatures, Integer>> deadlineListView = getNode(DEADLINE_TASK_LIST_VIEW_ID);
        return deadlineListView;
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
     * @param tasks
     *            A list of task in the correct order.
     */
    public boolean isListMatching(int startPosition, BasicTaskFeatures... tasks) throws IllegalArgumentException {
        List<BasicTaskFeatures> deadlineTasks = new ArrayList<>(Arrays.asList(tasks));
        ObservableList<BasicTaskFeatures> taskList = FXCollections.observableArrayList();
        for (int index = 0; index < deadlineTasks.size(); index++) {
            if (deadlineTasks.get(index).getTaskType().equals("deadline")) {
                taskList.add(deadlineTasks.get(index));
            }
        }
        Collections.sort(taskList, new TaskNameComparator());
        Collections.sort(taskList, new EndDateTimeComparator());

        if (taskList.size() + startPosition != getDeadlineListView().getItems().size()) {

            throw new IllegalArgumentException(
                    "List size mismatched\n" + "Expected " + (getDeadlineListView().getItems().size() - 1) + " tasks");
        }
        assertTrue(this.containsInOrder(startPosition, taskList));
        for (int i = 0; i < taskList.size(); i++) {
            final int scrollTo = i + startPosition;
            guiRobot.interact(() -> getDeadlineListView().scrollTo(scrollTo));
            guiRobot.sleep(200);
            if (!TestUtil.compareCardAndTask(getDeadlineTaskCardHandle(startPosition + i), deadlineTasks.get(i))) {
                return false;
            }
        }
        return true;
    }

    class TaskNameComparator implements Comparator<BasicTask> {
        public int compare(BasicTask c1, BasicTask c2) {
            return c2.getName().toString().compareTo(c1.getName().toString());
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
        Point2D point = TestUtil.getScreenMidPoint(getDeadlineListView());
        guiRobot.clickOn(point.getX(), point.getY());
    }

    /**
     * Returns true if the {@code tasks} appear as the sub list (in that order)
     * at position {@code startPosition}.
     */
    public boolean containsInOrder(int startPosition, ObservableList<BasicTaskFeatures> taskList) {
        List<Pair<BasicTaskFeatures, Integer>> tasksInList = getDeadlineListView().getItems();

        // Return false if the list in panel is too short to contain the given
        // list

        if (startPosition + taskList.size() > tasksInList.size()) {
            return false;
        }

        // Return false if any of the tasks doesn't match
        for (int i = 0; i < taskList.size(); i++) {
            if (!tasksInList.get(startPosition + i).getKey().getName().fullTaskName
                    .equals(taskList.get(i).getName().fullTaskName)) {
                return false;
            }
        }
        return true;
    }

    public DeadlineTaskCardHandle navigateToDeadlineTask(String taskname) {
        guiRobot.sleep(500); // Allow a bit of time for the list to be updated
        final Optional<Pair<BasicTaskFeatures, Integer>> task = getDeadlineListView().getItems().stream()
                .filter(p -> p.getKey().getName().toString().equals(taskname)).findAny();
        if (!task.isPresent()) {
            throw new IllegalStateException("Task name not found: " + taskname);
        }

        return navigateToDeadlineTask(task.get().getKey());
    }

    /**
     * Navigates the listview to display and select the task.
     */
    public DeadlineTaskCardHandle navigateToDeadlineTask(BasicTaskFeatures task) {
        int index = getDeadlineTaskIndex(task);

        guiRobot.interact(() -> {
            getDeadlineListView().scrollTo(index);
            guiRobot.sleep(150);
            getDeadlineListView().getSelectionModel().select(index);
        });
        guiRobot.sleep(100);
        return getDeadlineTaskCardHandle(task);
    }

    /**
     * Returns the position of the task given, {@code NOT_FOUND} if not found
     * in the list.
     */
    public int getDeadlineTaskIndex(BasicTaskFeatures targetTask) {
        List<Pair<BasicTaskFeatures, Integer>> tasksInList = getDeadlineListView().getItems();
        for (int i = 0; i < tasksInList.size(); i++) {
            if (tasksInList.get(i).getKey().getName().equals(targetTask.getName())) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Gets a task from the list by index
     */
    public BasicTaskFeatures getDeadlineTask(int index) {
        return getDeadlineListView().getItems().get(index).getKey();
    }

    public DeadlineTaskCardHandle getDeadlineTaskCardHandle(int index) {
        return getDeadlineTaskCardHandle(new BasicTask(getDeadlineListView().getItems().get(index).getKey()));
    }

    public DeadlineTaskCardHandle getDeadlineTaskCardHandle(BasicTaskFeatures task) {
        Set<Node> nodes = getAllCardNodes();
        Optional<Node> taskCardNode = nodes.stream()
                .filter(n -> new DeadlineTaskCardHandle(guiRobot, primaryStage, n).isSameTask(task)).findFirst();
        if (taskCardNode.isPresent()) {
            return new DeadlineTaskCardHandle(guiRobot, primaryStage, taskCardNode.get());
        } else {
            return null;
        }
    }

    protected Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public int getNumberOfTask() {
        return getDeadlineListView().getItems().size();
    }
}

