package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TickTask;
import seedu.address.model.person.Task;
import seedu.address.model.person.exceptions.DuplicateTaskException;
import seedu.address.model.tag.Tag;

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
