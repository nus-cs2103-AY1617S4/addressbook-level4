package seedu.ticktask.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.ticktask.logic.parser.ParserUtil;
import seedu.ticktask.model.person.ReadOnlyTask;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditPersonDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(ReadOnlyTask person) throws IllegalValueException {
        descriptor = new EditTaskDescriptor();
        descriptor.setName(person.getName());
        descriptor.setTime(person.getTime());
        descriptor.setEmail(person.getEmail());
        descriptor.setDate(person.getDate());
        descriptor.setTags(person.getTags());
    }

    public EditPersonDescriptorBuilder withName(String name) throws IllegalValueException {
        ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        return this;
    }

    public EditPersonDescriptorBuilder withPhone(String phone) throws IllegalValueException {
        ParserUtil.parsePhone(Optional.of(phone)).ifPresent(descriptor::setTime);
        return this;
    }

    public EditPersonDescriptorBuilder withEmail(String email) throws IllegalValueException {
        ParserUtil.parseEmail(Optional.of(email)).ifPresent(descriptor::setEmail);
        return this;
    }

    public EditPersonDescriptorBuilder withAddress(String address) throws IllegalValueException {
        ParserUtil.parseAddress(Optional.of(address)).ifPresent(descriptor::setDate);
        return this;
    }

    public EditPersonDescriptorBuilder withTags(String... tags) throws IllegalValueException {
        descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
