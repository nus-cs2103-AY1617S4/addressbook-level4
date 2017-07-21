package seedu.whatsnext.testutil;

import java.util.Set;

import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

//@@author A0142675B
/**
 * Object use for testing.
 */
public class TypicalTasks {

    public static final Index INDEX_FIRST_TASK = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_TASK = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_TASK = Index.fromOneBased(3);
    public static final Index INDEX_LAST_TASK = Index.fromOneBased(10);

    public final BasicTask completeCS2103Assignment, meetJohnForDinner, meetTomForLunch, camping,
                           cs2010ProblemSet, fypSelection, tester, readaBook;

    public TypicalTasks() {
        try {
            meetJohnForDinner = new TaskBuilder().withName("Meet John for dinner")
                                .withTags("DINNER").build();
            completeCS2103Assignment = new TaskBuilder().withName("Complete")
                    .withStartDateTime("10 Dec 2017 6pm")
                    .withEndDateTime("13 Dec 12pm")
                    .withTags("HIGH", "CS2103").build();
            meetTomForLunch = new TaskBuilder().withName("Meet Tom for lunch")
                              .withTags("LUNCH").build();
            camping = new TaskBuilder().withName("Camping")
                      .withStartDateTime("20 Dec 2017 6pm")
                      .withEndDateTime("25 Dec 12pm")
                      .withTags("CAMPING").build();
            cs2010ProblemSet = new TaskBuilder().withName("CS2010 Problem Set")
                               .withEndDateTime("next thursday")
                               .withTags("HIGH", "CS2010").build();
            fypSelection = new TaskBuilder().withName("FYP Selection")
                           .withEndDateTime("Friday")
                           .withTags("HIGH").build();
            readaBook = new TaskBuilder().withName("Read a Book").build();

            // Tasks used to test Unmark Command
            tester = new TaskBuilder().withName("Tester")
                    .withStatus(true).build();


            // Manually added
        } catch (IllegalValueException e) {
            throw new AssertionError("Sample data cannot be invalid", e);
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (BasicTask task : new TypicalTasks().getTypicalTasks()) {
            try {
                ab.addTask(new BasicTask(task));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    //@@author A0156106M
    /**
     * Loads the TaskManager with sample Marked BasicTask data
     * */
    public static void loadTaskManagerWithSampleMarkedData(TaskManager ab) {
        for (BasicTask task : new TypicalTasks().getTypicalTasks()) {
            try {
                ab.addTask(createMarkedTask(task));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    //@@author A0156106M
    /**
     * Returns a marked BasicTask
     * */
    private static BasicTask createMarkedTask(BasicTask taskToMark) {
        assert taskToMark != null;
        BasicTask toCopy = new BasicTask(taskToMark);
        TaskName updatedName = toCopy.getName();
        TaskDescription updatedDescription = toCopy.getDescription();
        DateTime startDateTime = toCopy.getStartDateTime();
        DateTime endDateTime = toCopy.getEndDateTime();
        toCopy.setCompleted();
        boolean updateIsComplete = toCopy.getIsCompleted();
        Set<Tag> updatedTags = toCopy.getTags();
        return new BasicTask(updatedName, updatedDescription,
                updateIsComplete, startDateTime, endDateTime, updatedTags);
    }

    //@@author A0142675B
    public BasicTask[] getTypicalTasks() {
        return new BasicTask[]{completeCS2103Assignment, camping, cs2010ProblemSet, fypSelection,
                                  tester, meetJohnForDinner, meetTomForLunch};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }

    //@@author A0156106M
    public TaskManager getTypicalMarkTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleMarkedData(tm);
        return tm;
    }


}
