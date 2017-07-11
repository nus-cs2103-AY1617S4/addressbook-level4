package seedu.ticktask.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.Email;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {

                new Task(new Name("Alex Yeoh"), new DueTime("87438807"), new Email("alexyeoh@example.com"),
                    new DueDate("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
                new Task(new Name("Bernice Yu"), new DueTime("99272758"), new Email("berniceyu@example.com"),
                    new DueDate("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
                new Task(new Name("Charlotte Oliveiro"), new DueTime("93210283"), new Email("charlotte@example.com"),
                    new DueDate("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
                new Task(new Name("David Li"), new DueTime("91031282"), new Email("lidavid@example.com"),
                    new DueDate("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
                new Task(new Name("Irfan Ibrahim"), new DueTime("92492021"), new Email("irfan@example.com"),
                    new DueDate("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
                new Task(new Name("Roy Balakrishnan"), new DueTime("92624417"), new Email("royb@example.com"),
                    new DueDate("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
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
