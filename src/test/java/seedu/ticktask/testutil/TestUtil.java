package seedu.ticktask.testutil;

import static seedu.ticktask.model.util.SampleDataUtil.getTagSet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import guitests.guihandles.PersonCardHandle;
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
import seedu.ticktask.model.task.ReadOnlyTask;
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

    public static final Task[] SAMPLE_TASK_DATa = getSAMPLE_TASK_DATa();

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

    private static Task[] getSAMPLE_TASK_DATa() {
        try {
            //CHECKSTYLE.OFF: LineLength
            return new Task[]{
              
                new Task(new Name("Ali Muster"), new DueTime("9482424"), new TaskType("hans@example.com"), new DueDate("4th street"), getTagSet()),
                new Task(new Name("Boris Mueller"), new DueTime("87249245"), new TaskType("ruth@example.com"), new DueDate("81th street"), getTagSet()),
                new Task(new Name("Carl Kurz"), new DueTime("95352563"), new TaskType("heinz@example.com"), new DueDate("wall street"), getTagSet()),
                new Task(new Name("Daniel Meier"), new DueTime("87652533"), new TaskType("cornelia@example.com"), new DueDate("10th street"), getTagSet()),
                new Task(new Name("Elle Meyer"), new DueTime("9482224"), new TaskType("werner@example.com"), new DueDate("michegan ave"), getTagSet()),
                new Task(new Name("Fiona Kunz"), new DueTime("9482427"), new TaskType("lydia@example.com"), new DueDate("little tokyo"), getTagSet()),
                new Task(new Name("George Best"), new DueTime("9482442"), new TaskType("anna@example.com"), new DueDate("4th street"), getTagSet()),
                new Task(new Name("Hoon Meier"), new DueTime("8482424"), new TaskType("stefan@example.com"), new DueDate("little india"), getTagSet()),
                new Task(new Name("Ida Mueller"), new DueTime("8482131"), new TaskType("hans@example.com"), new DueDate("chicago ave"), getTagSet())

            };
            //CHECKSTYLE.ON: LineLength
        } catch (IllegalValueException e) {
            assert false;
            // not possible
            return null;
        }
    }

    public static List<Task> generateSamplePersonData() {
        return Arrays.asList(SAMPLE_TASK_DATa);
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
     * Removes a subset from the list of persons.
     * @param tasks The list of persons
     * @param personsToRemove The subset of persons.
     * @return The modified persons after removal of the subset from persons.
     */
    public static Task[] removePersonsFromList(final Task[] tasks, Task... personsToRemove) {
        List<Task> listOfTasks = asList(tasks);
        listOfTasks.removeAll(asList(personsToRemove));
        return listOfTasks.toArray(new Task[listOfTasks.size()]);
    }


    /**
     * Returns a copy of the list with the person at specified index removed.
     * @param list original list to copy from
     */
    public static Task[] removePersonFromList(final Task[] list, Index index) {
        return removePersonsFromList(list, list[index.getZeroBased()]);
    }

    /**
     * Appends persons to the array of persons.
     * @param tasks A array of persons.
     * @param personsToAdd The persons that are to be appended behind the original array.
     * @return The modified array of persons.
     */
    public static Task[] addTasksToList(final Task[] tasks, Task... personsToAdd) {
        List<Task> listOfTasks = asList(tasks);
        listOfTasks.addAll(asList(personsToAdd));
        return listOfTasks.toArray(new Task[listOfTasks.size()]);
    }

    private static <T> List<T> asList(T[] objs) {
        List<T> list = new ArrayList<>();
        for (T obj : objs) {
            list.add(obj);
        }
        return list;
    }

    public static boolean compareCardAndPerson(PersonCardHandle card, ReadOnlyTask person) {
        return card.isSamePerson(person);
    }

}
