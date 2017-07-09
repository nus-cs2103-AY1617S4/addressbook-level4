package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME_ALTERNATIVE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.commons.util.CollectionUtil;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;
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
            + "[" + PREFIX_NAME_ALTERNATIVE + " to " + "NAME] "
            + "[" + PREFIX_DATE + " to " + "DATE] "
            + "[" + PREFIX_TIME + " to " + "TIME] "
            + "[" + PREFIX_NEW_TAG  + " TAG]...\n"
            + "[" + PREFIX_DELETE_TAG + " TAG]...\n"
            + "Example 1 : " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + " 10 July "
            + PREFIX_TIME + " 10-12"
            + "Example 2 : " + COMMAND_WORD + " 2 "
            + PREFIX_NAME_ALTERNATIVE + " to project meeting "
            + PREFIX_NEW_TAG + " HIGH"
            + PREFIX_DELETE_TAG + " RELAX";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

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
    public CommandResult execute() throws CommandException, TagNotFoundException, IllegalValueException {
        List<BasicTaskFeatures> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        BasicTaskFeatures personToEdit = lastShownList.get(index.getZeroBased());
        BasicTask editedPerson = createEditedTask(personToEdit, editPersonDescriptor);

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
     * @throws TagNotFoundException
     * @throws IllegalValueException
     * @@author A0142675B
     */
    private static BasicTask createEditedTask(BasicTaskFeatures taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor)
                                             throws TagNotFoundException, IllegalValueException {
        assert taskToEdit != null;

        TaskName updatedName = editTaskDescriptor.getName().orElse(taskToEdit.getName());
        Set<Tag> updatedTags = consolidateTags(taskToEdit, editTaskDescriptor);

        return new BasicTask(updatedName, updatedTags);
    }

    /*
     * Adds and removes the tags accordingly.
     * @@author A0142675B
     */
    private static Set<Tag> consolidateTags(
                   BasicTaskFeatures taskToEdit, EditTaskDescriptor editTaskDescriptor)
                           throws TagNotFoundException, IllegalValueException {
        Set<Tag> updatedTags = new HashSet<Tag>();
        Set<Tag> existingTags = taskToEdit.getTags();

        if (existingTags != null) {
            Iterator<Tag> tag = existingTags.iterator();
            while (tag.hasNext()) {
                Tag tagToBeRetained = tag.next();
                updatedTags.add(tagToBeRetained);
            }
        }

        if (editTaskDescriptor.removeTags != null) {
            Iterator<Tag> tag = editTaskDescriptor.removeTags.iterator();
            while (tag.hasNext()) {
                Tag tagToBeRemoved = tag.next();
                if (updatedTags.contains(tagToBeRemoved)) {
                    updatedTags.remove(tagToBeRemoved);
                } else {
                    throw new TagNotFoundException();
                }
            }
        }

        boolean hasNewPriorityTag = false;
        Tag newPriorityTag = null;

        if (editTaskDescriptor.newTags != null) {
            Iterator<Tag> tag = editTaskDescriptor.newTags.iterator();
            while (tag.hasNext()) {
                Tag tagToBeAdded = tag.next();
                updatedTags.add(tagToBeAdded);
                if (tagToBeAdded.isPriorityTag() && (!hasNewPriorityTag)) {
                    newPriorityTag = tagToBeAdded;
                    hasNewPriorityTag = true;
                }
            }
        }
        if (updatedTags != null && hasNewPriorityTag && newPriorityTag != null) {
            final Tag high = new Tag("HIGH");
            final Tag medium = new Tag("MEDIUM");
            final Tag low = new Tag("LOW");
            updatedTags.remove(high);
            updatedTags.remove(medium);
            updatedTags.remove(low);
            updatedTags.add(newPriorityTag);
        }
        return updatedTags;
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
        private Set<Tag> newTags;
        private Set<Tag> removeTags;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.name;
            this.isCompleted = toCopy.isCompleted;
            this.startDate = toCopy.startDate;
            this.endDate = toCopy.endDate;
            this.newTags = toCopy.newTags;
            this.removeTags = toCopy.removeTags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name,
                                               this.isCompleted,
                                               this.startDate,
                                               this.endDate,
                                               this.newTags,
                                               this.removeTags);
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

        /*
         * @@author A0142675B
         */
        public void addTags(Set<Tag> tags) {
            this.newTags = tags;
        }

        /*
         * @@author A0142675B
         */
        public void deleteTags(Set<Tag> tags) {
            this.removeTags = tags;
        }

        public Optional<Set<Tag>> getNewTags() {
            return Optional.ofNullable(newTags);
        }

        public Optional<Set<Tag>> getRemoveTags() {
            return Optional.ofNullable(removeTags);
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
                    && getNewTags().equals(e.getNewTags())
                    && getRemoveTags().equals(e.removeTags);
        }
    }
}
