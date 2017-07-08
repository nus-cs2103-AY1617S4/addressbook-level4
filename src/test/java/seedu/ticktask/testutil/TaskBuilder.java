package seedu.ticktask.testutil;

import java.util.Set;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.Email;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class TaskBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_TAGS = "friends";

    private Task task;

    public TaskBuilder() throws IllegalValueException {
        Name defaultName = new Name(DEFAULT_NAME);
        DueTime defaultTime = new DueTime(DEFAULT_PHONE);
        Email defaultEmail = new Email(DEFAULT_EMAIL);
        DueDate defaultAddress = new DueDate(DEFAULT_ADDRESS);
        Set<Tag> defaultTags = SampleDataUtil.getTagSet(DEFAULT_TAGS);
        this.task = new Task(defaultName, defaultTime, defaultEmail, defaultAddress, defaultTags);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public TaskBuilder(ReadOnlyTask personToCopy) {
        this.task = new Task(personToCopy);
    }

    public TaskBuilder withName(String name) throws IllegalValueException {
        this.task.setName(new Name(name));
        return this;
    }

    public TaskBuilder withTags(String ... tags) throws IllegalValueException {
        this.task.setTags(SampleDataUtil.getTagSet(tags));
        return this;
    }

    public TaskBuilder withAddress(String address) throws IllegalValueException {
        this.task.setDate(new DueDate(address));
        return this;
    }

    public TaskBuilder withPhone(String phone) throws IllegalValueException {
        this.task.setTime(new DueTime(phone));
        return this;
    }

    public TaskBuilder withEmail(String email) throws IllegalValueException {
        this.task.setEmail(new Email(email));
        return this;
    }

    public Task build() {
        return this.task;
    }

}
