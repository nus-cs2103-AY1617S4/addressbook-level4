package seedu.ticktask.testutil;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;

/**
 *
 */
public class TypicalTasks {

    public static final Index INDEX_FIRST_TASK = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_TASK = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);

    public final Task washdog, dotutorial, meetgirlfriend;

    public TypicalTasks() {
        try {
            washdog = new TaskBuilder().withName("wash dog")
                    .withDate("01/01").withEmail("alice@example.com")
                    .withTime("2200")
                    .withTags("cleaning").build();
            dotutorial = new TaskBuilder().withName("do tutorial").withDate("23/03/19")
                    .withEmail("dotutorial@example.com").withTime("2300")
                    .withTags("school", "homework").build();
            meetgirlfriend = new TaskBuilder().withName("meet girlfriend").withTime("0800")
                    .withEmail("email@example.com").withDate("23/02").build();
            
          
        } catch (IllegalValueException e) {
            throw new AssertionError("Sample data cannot be invalid", e);
        }
    }

    public static void loadTickTaskWithSampleData(TickTask ab) {
        for (Task task : new TypicalTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public Task[] getTypicalTasks() {
        return new Task[]{washdog, dotutorial, meetgirlfriend};
    }

    public TickTask getTypicalTickTask() {
        TickTask ab = new TickTask();
        loadTickTaskWithSampleData(ab);
        return ab;
    }
}
