package seedu.ticktask.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.ticktask.logic.parser.ParserUtil;
import seedu.ticktask.model.task.ReadOnlyTask;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditTaskDescriptorBuilder(ReadOnlyTask person) throws IllegalValueException {
        descriptor = new EditTaskDescriptor();
        descriptor.setName(person.getName());
        descriptor.setTime(person.getTime());
        descriptor.setEmail(person.getEmail());
        descriptor.setDate(person.getDate());
        descriptor.setTags(person.getTags());
    }

    public EditTaskDescriptorBuilder withName(String name) throws IllegalValueException {
        ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        return this;
    }

    public EditTaskDescriptorBuilder withPhone(String phone) throws IllegalValueException {
        ParserUtil.parseTime(Optional.of(phone)).ifPresent(descriptor::setTime);
        return this;
    }

    public EditTaskDescriptorBuilder withEmail(String email) throws IllegalValueException {
        ParserUtil.parseEmail(Optional.of(email)).ifPresent(descriptor::setEmail);
        return this;
    }

    public EditTaskDescriptorBuilder withAddress(String address) throws IllegalValueException {
        ParserUtil.parseDate(Optional.of(address)).ifPresent(descriptor::setDate);
        return this;
    }

    public EditTaskDescriptorBuilder withTags(String... tags) throws IllegalValueException {
        descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
