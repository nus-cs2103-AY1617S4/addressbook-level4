package seedu.whatsnext.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

public class SampleDataUtil {
    public static BasicTask[] getSamplePersons() {
        try {
            return new BasicTask[] {
                new BasicTask(new TaskName("Complete CS2103 assignment"),new TaskDescription(), false,
                    getTagSet("HIGH", "cs2103")),
                new BasicTask(new TaskName("Assignment"),new TaskDescription(), false, new DateTime("06/12/12"), new DateTime(),
                        getTagSet("homework")),
                new BasicTask(new TaskName("CS2103 Workshop"), new TaskDescription(), false, new DateTime("August 3rd"), new DateTime("August 4th"),
                        getTagSet("camping")),
                new BasicTask(new TaskName("Camping"), new TaskDescription(), false, new DateTime("07/10/12"), new DateTime("07/11/12"),
                        getTagSet("camping"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleAb = new TaskManager();
            for (BasicTask samplePerson : getSamplePersons()) {
                sampleAb.addTask(samplePerson);
            }
            return sampleAb;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
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
