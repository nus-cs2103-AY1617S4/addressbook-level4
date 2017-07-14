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

<<<<<<< HEAD
    public final BasicTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
                    .withPhone("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@example.com").withPhone("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPhone("95352563")
                    .withEmail("heinz@example.com").withAddress("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPhone("87652533")
                    .withEmail("cornelia@example.com").withAddress("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPhone("9482224")
                    .withEmail("werner@example.com").withAddress("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("9482427")
                    .withEmail("lydia@example.com").withAddress("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPhone("9482442")
                    .withEmail("anna@example.com").withAddress("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPhone("8482424")
                    .withEmail("stefan@example.com").withAddress("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPhone("8482131")
                    .withEmail("hans@example.com").withAddress("chicago ave").build();
=======
    public final BasicTask completeCS2103Assignment, meetJohnForDinner, meetTomForLunch,
                           completeHomework, assignment, cs2103Workshop, camping,
                           cs2010ProblemSet, fypSelection, tester;

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
            tester = new TaskBuilder().withName("Tester")
                    .withStatus(true).build();

            // Manually added
>>>>>>> TeamMain/master
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
<<<<<<< HEAD
        return new BasicTask[]{/alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
=======
        return new BasicTask[]{completeCS2103Assignment, meetJohnForDinner, meetTomForLunch,
                               completeHomework, assignment, cs2103Workshop, camping,
                               cs2010ProblemSet, fypSelection, tester};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
>>>>>>> TeamMain/master
    }
}
