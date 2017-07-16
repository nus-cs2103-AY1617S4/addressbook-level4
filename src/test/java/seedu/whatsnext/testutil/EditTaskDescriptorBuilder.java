package seedu.whatsnext.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.whatsnext.logic.parser.ParserUtil;
import seedu.whatsnext.model.task.BasicTaskFeatures;

//@@author A0142675B
/**
 * A utility class to help with building EditTaskDescriptor objects.
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
    public EditTaskDescriptorBuilder(BasicTaskFeatures task) throws IllegalValueException {
        descriptor = new EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setIsCompleted(task.getIsCompleted());
        descriptor.setStartDateTime(task.getStartDateTime());
        descriptor.setEndDateTime(task.getEndDateTime());
        descriptor.setNewTags(task.getTags());
        descriptor.setDescription(task.getDescription());
    }

    public EditTaskDescriptorBuilder withName(String name) throws IllegalValueException {
        ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
        return this;
    }

    public EditTaskDescriptorBuilder withStartDateTime(String startDateTime) throws IllegalValueException {
        ParserUtil.parseStartDateTime(Optional.of(startDateTime)).ifPresent(descriptor::setStartDateTime);
        return this;
    }

    public EditTaskDescriptorBuilder withEndDateTime(String endDateTime) throws IllegalValueException {
        ParserUtil.parseStartDateTime(Optional.of(endDateTime)).ifPresent(descriptor::setEndDateTime);
        return this;
    }

    public EditTaskDescriptorBuilder withDescription(String description) throws IllegalValueException {
        ParserUtil.parseDescription(Optional.of(description)).ifPresent(descriptor::setDescription);
        return this;
    }

    public EditTaskDescriptorBuilder withNewTags(String... tags) throws IllegalValueException {
        descriptor.setNewTags(ParserUtil.parseTags(Arrays.asList(tags)));
        return this;
    }

    public EditTaskDescriptorBuilder withRemoveTags(String... tags) throws IllegalValueException {
        descriptor.setRemoveTags(ParserUtil.parseTags(Arrays.asList(tags)));
        return this;
    }

    public EditTaskDescriptor build() {
        return descriptor;
    }
}
