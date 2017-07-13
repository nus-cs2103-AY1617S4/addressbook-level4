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

    public final BasicTask completeCS2103Assignment, meetJohnForDinner, meetTomForLunch,
                           completeHomework, assignment, cs2103Workshop, camping,
                           cs2010ProblemSet, fypSelection;

    public TypicalTasks() {
        try {
            completeCS2103Assignment = new TaskBuilder().withName("Complete CS2103 Assignment")
                                       .withTags("HIGH", "CS2103").build();
            meetJohnForDinner = new TaskBuilder().withName("Meet John for dinner")
                                .withTags("DINNER").build();
            meetTomForLunch = new TaskBuilder().withName("Meet Tom for lunch")
                              .withTags("LUNCH").build();
            completeHomework = new TaskBuilder().withName("Complete Homework")
                               .withTags("HOMEWORK").build();
            assignment = new TaskBuilder().withName("Assignment")
                         .withTags("HOMEWORK").build();
            cs2103Workshop = new TaskBuilder().withName("CS2103 workshop")
                             .withStartDateTime("12 Jun 2017 10am")
                             .withEndDateTime("12 Jun 2017 4pm")
                             .withTags("WORKSHOP").build();
            camping = new TaskBuilder().withName("Camping")
                      .withStartDateTime("10 July 2017 6pm")
                      .withEndDateTime("13 July 12pm")
                      .withTags("CAMPING").build();
            cs2010ProblemSet = new TaskBuilder().withName("CS2010 Problem Set")
                               .withEndDateTime("next thursday")
                               .withTags("HIGH", "CS2010").build();
            fypSelection = new TaskBuilder().withName("FYP Selection")
                           .withEndDateTime("Friday")
                           .withTags("HIGH").build();

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
        return new BasicTask[]{completeCS2103Assignment, meetJohnForDinner, meetTomForLunch,
                               completeHomework, assignment, cs2103Workshop, camping,
                               cs2010ProblemSet, fypSelection};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
