package seedu.whatsnext.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

public class SampleDataUtil {
    public static BasicTask[] getSamplePersons() {
        try {
            return new BasicTask[] {
            		new BasicTask(new TaskName("Complete CS2103 assignment"),
                            getTagSet("HIGH", "cs2103")),
                        new BasicTask(new TaskName("Meet John for dinner"),
                                getTagSet("dinner")),
                        new BasicTask(new TaskName("Meet Tom for lunch"),
                                getTagSet("lunch")),
                        new BasicTask(new TaskName("Complete Homework"),
                                getTagSet("dinner")),
                        new BasicTask(new TaskName("Assignment"), false, new DateTime("06/12/12"), new DateTime(),
                                getTagSet("homework")),
                        new BasicTask(new TaskName("CS2103 workshop"), false, new DateTime("06/12/12"), new DateTime("07/12/12"),
                                getTagSet("workshop")),
                        new BasicTask(new TaskName("Camping"), false, new DateTime("07/10/12"), new DateTime("07/11/12"),
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
