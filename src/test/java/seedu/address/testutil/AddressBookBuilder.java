//package seedu.whatsnext.testutil;
//
//import seedu.whatsnext.commons.exceptions.IllegalValueException;
//import seedu.whatsnext.model.AddressBook;
//import seedu.whatsnext.model.person.Person;
//import seedu.whatsnext.model.person.exceptions.DuplicatePersonException;
//import seedu.whatsnext.model.tag.Tag;
//
///**
// * A utility class to help with building Addressbook objects.
// * Example usage: <br>
// *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withTag("Friend").build();}
// */
//public class AddressBookBuilder {
//
//    private AddressBook addressBook;
//
//    public AddressBookBuilder() {
//        addressBook = new AddressBook();
//    }
//
//    public AddressBookBuilder(AddressBook addressBook) {
//        this.addressBook = addressBook;
//    }
//
//    public AddressBookBuilder withPerson(Person person) throws DuplicatePersonException {
//        addressBook.addPerson(person);
//        return this;
//    }
//
//    public AddressBookBuilder withTag(String tagName) throws IllegalValueException {
//        addressBook.addTag(new Tag(tagName));
//        return this;
//    }
//
//    public AddressBook build() {
//        return addressBook;
//    }
//}
