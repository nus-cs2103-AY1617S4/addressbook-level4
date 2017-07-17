package seedu.ticktask.testutil;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.EditCommand;
import seedu.ticktask.logic.parser.ParserUtil;
import seedu.ticktask.model.task.ReadOnlyTask;

import java.util.Arrays;
import java.util.Optional;

//@@author A0139964M
public class EditTaskDescriptorBuilder {
    
        
        private EditCommand.EditTaskDescriptor descriptor;
        
        public EditTaskDescriptorBuilder() {
            descriptor = new EditCommand.EditTaskDescriptor();
        }
        
        public EditTaskDescriptorBuilder(EditCommand.EditTaskDescriptor descriptor) {
            this.descriptor = new EditCommand.EditTaskDescriptor(descriptor);
        }
        
        /**
         * Returns an {@code EditEntryDescriptor} with fields containing {@code entry}'s details
         */
        public EditTaskDescriptorBuilder(ReadOnlyTask task) throws IllegalValueException {
            descriptor = new EditCommand.EditTaskDescriptor();
            descriptor.setName(task.getName());
            descriptor.setDate(task.getDate());
            descriptor.setTime(task.getTime());
            descriptor.setTags(task.getTags());
        }
        
        
        public EditTaskDescriptorBuilder withName(String name) throws IllegalValueException {
            ParserUtil.parseName(Optional.of(name)).ifPresent(descriptor::setName);
            return this;
        }
    
        public EditTaskDescriptorBuilder withDate(String date) throws IllegalValueException {
            ParserUtil.parseDate(Optional.of(date)).ifPresent(descriptor::setDate);
            return this;
        }
    
        public EditTaskDescriptorBuilder withTime(String time) throws IllegalValueException {
            ParserUtil.parseTime(Optional.of(time)).ifPresent(descriptor::setTime);
            return this;
        }
        
        public EditTaskDescriptorBuilder withTags(String... tags) throws IllegalValueException {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
            return this;
        }
        
        public EditCommand.EditTaskDescriptor build() {
            return descriptor;
        }
    }
//@@author