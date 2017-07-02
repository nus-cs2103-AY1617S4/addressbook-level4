package seedu.ticktask.testutil;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TickTaskBuilder {

    private TickTask addressBook;

    public TickTaskBuilder() {
        addressBook = new TickTask();
    }

    public TickTaskBuilder(TickTask addressBook) {
        this.addressBook = addressBook;
    }

    public TickTaskBuilder withPerson(Task task) throws DuplicateTaskException {
        addressBook.addPerson(task);
        return this;
    }

    public TickTaskBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TickTask build() {
        return addressBook;
    }
}
