package seedu.ticktask.model.util;

import static seedu.ticktask.model.util.SampleDataUtil.getTagSet;

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

public class SampleDataUtil {
    //@@author A0139819N
    public static Task[] getSampleTasks() {
        
        try {
            return new Task[]{
                    new Task(new Name("Buy eggs and milk"), new DueTime(""), new TaskType("floating"), new DueDate(""), getTagSet("urgent errand")),
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
        } 
        catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }
    //@@author

    public static ReadOnlyTickTask getSampleTickTask() throws DuplicateTaskException {
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
