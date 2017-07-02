package seedu.ticktask.testutil;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.person.Task;
import seedu.ticktask.model.person.exceptions.DuplicateTaskException;
import seedu.ticktask.model.tag.Tag;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class AddressBookBuilder {

    private TickTask addressBook;

    public AddressBookBuilder() {
        addressBook = new TickTask();
    }

    public AddressBookBuilder(TickTask addressBook) {
        this.addressBook = addressBook;
    }

    public AddressBookBuilder withPerson(Task task) throws DuplicateTaskException {
        addressBook.addPerson(task);
        return this;
    }

    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
        addressBook.addTag(new Tag(tagName));
        return this;
    }

    public TickTask build() {
        return addressBook;
    }
}
