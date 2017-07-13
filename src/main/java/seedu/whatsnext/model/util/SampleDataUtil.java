package seedu.whatsnext.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

public class SampleDataUtil {
    public static BasicTask[] getSamplePersons() {
        try {
            return new BasicTask[] {
                new BasicTask(new TaskName("Complete CS2103 assignment"),
                    getTagSet("HIGH", "NUS")),
                new BasicTask(new TaskName("Complete CS1231 assignment"),
                        getTagSet("MEDIUM", "NUS")),
                new BasicTask(new TaskName("Complete EE2024 assignment"),
                        getTagSet("LOW", "NUS")),
                new BasicTask(new TaskName("Complete EE2021 assignment"),
                        getTagSet("NUS", "EE2021"))
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
