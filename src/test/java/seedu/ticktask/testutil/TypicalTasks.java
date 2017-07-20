package seedu.ticktask.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    public static final Index INDEX_THIRD_TASK = Index.fromOneBased(3);
    public static final Index INDEX_FORTH_TASK = Index.fromOneBased(4);
    public static final Index INDEX_FIFTH_TASK = Index.fromOneBased(5);
    public static final Index INDEX_SIXTH_TASK = Index.fromOneBased(6);

    public final Task washdog, dotutorial, meetgirlfriend, floatMethod, eventMethod, todayMethod;

    public TypicalTasks(){
        try {
            washdog = new TaskBuilder().withName("Wash dog")
                    .withDate("01/01/2018").withType("deadline")
                    .withTime("0800")
                    .withTags("cleaning").build();

            dotutorial = new TaskBuilder().withName("Do tutorial").withDate("03/22/19")
                    .withType("deadline").withTime("2300")
                    .withTags("school", "homework").build();
            
            meetgirlfriend = new TaskBuilder().withName("Meet girlfriend").withTime("2300")
                    .withType("deadline").withDate("01/01/2020").build();
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate = LocalDate.now();
           // System.out.println(dtf.format(localDate));

            floatMethod = new TaskBuilder().withName("This is a float Method")
                    .withType("floating").build();
            
            eventMethod = new TaskBuilder().withName("This is a event Method").withTime("2100 - 2300")
                    .withType("event").withDate("01/01/2017 - 01/01/2018").build();
            
            todayMethod = new TaskBuilder().withName("This is a today Method").withTime("2100")
                    .withType("deadline").withDate(dtf.format(localDate).toString()).build();
          
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
