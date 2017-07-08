package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.util.CollectionUtil;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;

/**
 * Edits the details of an existing task in the task manager.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the last task listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "10 July "
            + PREFIX_TIME + "10-12";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index index;
    private final EditTaskDescriptor editPersonDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editPersonDescriptor details to edit the task with
     */
    public EditCommand(Index index, EditTaskDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditTaskDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<BasicTaskFeatures> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        BasicTaskFeatures personToEdit = lastShownList.get(index.getZeroBased());
        BasicTask editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updateTask(personToEdit, editedPerson);
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, personToEdit));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static BasicTask createEditedPerson(BasicTaskFeatures personToEdit,
                                             EditTaskDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        TaskName updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new BasicTask(updatedName, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private TaskName name;
        private boolean isCompleted;
        private DateTime startDate;
        private DateTime endDate;
        private Set<Tag> tags;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.name;
            this.isCompleted = toCopy.isCompleted;
            this.startDate = toCopy.startDate;
            this.endDate = toCopy.endDate;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.isCompleted, this.startDate, this.endDate, this.tags);
        }

        public void setName(TaskName name) {
            this.name = name;
        }

        public Optional<TaskName> getName() {
            return Optional.ofNullable(name);
        }

        public void setIsCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        public Optional<DateTime> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public void setStartDate(DateTime startDate) {
            this.startDate = startDate;
        }

        public Optional<DateTime> getEndDate() {
            return Optional.ofNullable(endDate);
        }

        public void setEndDate(DateTime endDate) {
            this.endDate = endDate;
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getName().equals(e.getName())
                    && getStartDate().equals(e.getStartDate())
                    && getEndDate().equals(e.getEndDate())
                    && getTags().equals(e.getTags());
        }
    }
}
