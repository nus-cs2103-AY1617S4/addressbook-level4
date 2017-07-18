package seedu.ticktask.testutil;

import static seedu.ticktask.model.util.SampleDataUtil.getTagSet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import junit.framework.AssertionFailedError;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.commons.util.FileUtil;
import seedu.ticktask.commons.util.XmlUtil;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.TaskType;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.DueTime;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    public static final String SANDBOX_FOLDER = FileUtil.getPath("./src/test/data/sandbox/");

    public static final Task[] SAMPLE_TASK_DATA = getSampleTaskData();

    public static void assertThrows(Class<? extends Throwable> expected, Runnable executable) {
        try { 
            executable.run();
        } catch (Throwable actualException) {
            if (actualException.getClass().isAssignableFrom(expected)) {
                return;
            }
            String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                    actualException.getClass().getName());
            throw new AssertionFailedError(message);
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }

    //@@author A0139819N
    /**
     * @Returns a Task array of 10 sample tasks
     * @return
     */
    private static Task[] getSampleTaskData() {
        try {
            //CHECKSTYLE.OFF: LineLength
            return new Task[]{
                    new Task(new Name("Buy eggs and milk"), new DueTime(""), new TaskType("floating"), new DueDate(""), getTagSet()),
                    new Task(new Name("Research on book reviews on Shoe Dog"), new DueTime(""), new TaskType("floating"), new DueDate(""), getTagSet()),
                    new Task(new Name("Final report submission"), new DueTime(""), new TaskType("deadline"), new DueDate("1 august"), getTagSet()),
                    new Task(new Name("Final presentation rehearsal 1"), new DueTime("12pm to 2pm"), new TaskType("event"), new DueDate("2 august"), getTagSet()),
                    new Task(new Name("Final presentation rehearsal 2"), new DueTime("12pm to 2pm"), new TaskType("event"), new DueDate("12 august"), getTagSet()),
                    new Task(new Name("Final presentation"), new DueTime("2pm to 4pm"), new TaskType("event"), new DueDate("22 august"), getTagSet()),
                    new Task(new Name("End of semester"), new DueTime(""), new TaskType("deadline"), new DueDate("08/23"), getTagSet()),
                    new Task(new Name("Arrange meetup with friends"), new DueTime(""), new TaskType("floating"), new DueDate(""), getTagSet()),
                    new Task(new Name("Plan for overseas relaxation trip"), new DueTime(""), new TaskType("floating"), new DueDate(""), getTagSet()),
                    new Task(new Name("University hostel checkout deadline"), new DueTime("1700"), new TaskType("deadline"), new DueDate("08/25"), getTagSet()),
                    new Task(new Name("Pack belongings to move out"), new DueTime("12pm-3pm"), new TaskType("event"), new DueDate("08/25"), getTagSet()),
                    new Task(new Name("Dinner with Rachel"), new DueTime("1900-2200"), new TaskType("event"), new DueDate("08/025"), getTagSet()),
            };
            //CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }
    
    //@@author

    public static List<Task> generateSampleTaskData() {
        return Arrays.asList(SAMPLE_TASK_DATA);
    }

    /**
     * Appends the file name to the sandbox folder path.
     * Creates the sandbox folder if it doesn't exist.
     * @param fileName
     * @return
     */
    public static String getFilePathInSandboxFolder(String fileName) {
        try {
            FileUtil.createDirs(new File(SANDBOX_FOLDER));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER + fileName;
    }

    public static <T> void createDataFileWithData(T data, String filePath) {
        try {
            File saveFileForTesting = new File(filePath);
            FileUtil.createIfMissing(saveFileForTesting);
            XmlUtil.saveDataToFile(saveFileForTesting, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets mid point of a node relative to the screen.
     * @param node
     * @return
     */
    public static Point2D getScreenMidPoint(Node node) {
        double x = getScreenPos(node).getMinX() + node.getLayoutBounds().getWidth() / 2;
        double y = getScreenPos(node).getMinY() + node.getLayoutBounds().getHeight() / 2;
        return new Point2D(x, y);
    }

    public static Bounds getScreenPos(Node node) {
        return node.localToScreen(node.getBoundsInLocal());
    }

    /**
     * Removes a subset from the list of tasks.
     * @param tasks The list of tasks
     * @param tasksToRemove The subset of tasks.
     * @return The modified tasks after removal of the subset from tasks.
     */
    public static Task[] removeTasksFromList(final Task[] tasks, Task... tasksToRemove) {
        List<Task> listOfTasks = asList(tasks);
        listOfTasks.removeAll(asList(tasksToRemove));
        return listOfTasks.toArray(new Task[listOfTasks.size()]);
    }


    /**
     * Returns a copy of the list with the task at specified index removed.
     * @param list original list to copy from
     */
    public static Task[] removeTaskFromList(final Task[] list, Index index) {
        return removeTasksFromList(list, list[index.getZeroBased()]);
    }

    /**
     * Appends tasks to the array of tasks.
     * @param tasks A array of tasks.
     * @param tasksToAdd The tasks that are to be appended behind the original array.
     * @return The modified array of tasks.
     */
    public static Task[] addTasksToList(final Task[] tasks, Task... tasksToAdd) {
        List<Task> listOfTasks = asList(tasks);
        listOfTasks.addAll(asList(tasksToAdd));
        return listOfTasks.toArray(new Task[listOfTasks.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

}
