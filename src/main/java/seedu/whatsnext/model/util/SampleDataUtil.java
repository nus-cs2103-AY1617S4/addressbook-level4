package seedu.whatsnext.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.AddressBook;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.person.Floating;
import seedu.whatsnext.model.person.TaskName;
import seedu.whatsnext.model.person.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.tag.Tag;

public class SampleDataUtil {
    public static Floating[] getSamplePersons() {
        try {
            return new Floating[] {
                new Floating(new TaskName("Bernice Yu"),
                    getTagSet("colleagues", "friends"))
            };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleAddressBook() {
        try {
            AddressBook sampleAb = new AddressBook();
            for (Floating samplePerson : getSamplePersons()) {
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
