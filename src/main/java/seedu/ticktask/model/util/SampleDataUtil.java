package seedu.ticktask.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.TaskType;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.PastTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {

                new Task(new Name("Sample"), new DueTime("2200"), new TaskType("deadline"),
                    new DueDate("christmas"),
                    getTagSet("friends")),
                
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTickTask getSampleTickTask() {
        try {
            TickTask sampleAb = new TickTask();
            for (Task sampleTask : getSampleTasks()) {
                sampleAb.addTask(sampleTask);
            }
            return sampleAb;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        } catch (PastTaskException e) {
            throw new AssertionError("sample data cannot contain tasks in the past", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
