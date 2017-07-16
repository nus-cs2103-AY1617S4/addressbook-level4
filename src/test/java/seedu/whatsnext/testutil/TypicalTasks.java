package seedu.whatsnext.testutil;

import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

/**
 *
 */
public class TypicalTasks {

    public static final Index INDEX_FIRST_TASK = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_TASK = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_TASK = Index.fromOneBased(3);

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
                      .withStartDateTime("10 Dec 2017 6pm")
                      .withEndDateTime("13 Dec 12pm")
                      .withTags("CAMPING").build();
            cs2010ProblemSet = new TaskBuilder().withName("CS2010 Problem Set")
                               .withEndDateTime("next thursday")
                               .withTags("HIGH", "CS2010").build();
            fypSelection = new TaskBuilder().withName("FYP Selection")
                           .withEndDateTime("Friday")
                           .withTags("HIGH").build();
            readaBook = new TaskBuilder().withName("Read a Book").build();
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

    public BasicTask[] getTypicalTasks() {
        return new BasicTask[]{cs2010ProblemSet, meetJohnForDinner, completeCS2103Assignment,
                               meetTomForLunch, camping, fypSelection, tester};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
