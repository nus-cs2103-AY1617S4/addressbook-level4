package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.events.ui.JumpToListRequestEvent;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.commons.util.CollectionUtil;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
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
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_MESSAGE + "MESSAGE] "
            + "[" + PREFIX_START_DATETIME + "DATE] "
            + "[" + PREFIX_END_DATETIME + "TIME] "
            + "[" + PREFIX_NEW_TAG  + "TAG]... "
            + "[" + PREFIX_DELETE_TAG + "TAG]...\n"
            + "Example 1 : " + COMMAND_WORD + " 1 "
            + PREFIX_START_DATETIME + " 10 July 10PM"
            + PREFIX_END_DATETIME + " 11 July 12AM"
            + "Example 2 : " + COMMAND_WORD + " 2 "
            + PREFIX_NAME + "to project meeting "
            + PREFIX_NEW_TAG + "HIGH"
            + PREFIX_DELETE_TAG + "RELAX";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager.";

    private final Index index;
    private final EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditCommand(Index index, EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskDescriptor(editTaskDescriptor);
    }

    //@@author A0156106M
    @Override
    public CommandResult execute() throws CommandException, TagNotFoundException, IllegalValueException {
        List<BasicTaskFeatures> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        BasicTaskFeatures taskToEdit = lastShownList.get(index.getZeroBased());
        BasicTask editedTask = createEditedTask(taskToEdit, editTaskDescriptor);
        validateEditTask(editedTask);

        UnmodifiableObservableList<BasicTaskFeatures> taskList = model.getFilteredTaskList();

        try {
            if (editedTask.isOverlapTask(taskList)) {
                editedTask = EditCommand.createOverlapTask(editedTask);
            } else {
                editedTask = EditCommand.createNonOverlapTask(editedTask);
            }

            model.updateTask(taskToEdit, editedTask);

        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        }
        int counter = 0;
        for (int i = 0; i < model.getFilteredTaskList().size(); i++) {
            if (taskToEdit.equals(model.getFilteredTaskList().get(i))) {
                counter = i;
                break;
            }
        }
        Index index = new Index(counter);
        EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
    }

    /**
    * Checks the new editedTask created to ensure that the edited task value(s) is/are valid
    * @throws CommandException if edited task is invalid
    */
    public void validateEditTask(BasicTask editedTask) throws CommandException {
        if ((editedTask.getTaskType().equals(BasicTask.TASK_TYPE_EVENT)
                && editedTask.getEndDateTime().toString().equals(DateTime.INIT_DATETIME_VALUE))
                || (editedTask.getTaskType().equals(BasicTask.TASK_TYPE_EVENT)
                        && editedTask.getEndDateTime().isBefore(editedTask.getStartDateTime()))) {
            throw new CommandException(Messages.MESSAGE_INVALID_FLOATING_TO_EVENT_TASK);
        }
    }

    //@@author A0142675B
    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws TagNotFoundException
     * @throws IllegalValueException
     */
    private static BasicTask createEditedTask(BasicTaskFeatures taskToEdit,
                                             EditTaskDescriptor editTaskDescriptor)
                                             throws TagNotFoundException, IllegalValueException {
        assert taskToEdit != null;

        TaskName updatedName = editTaskDescriptor.getName().orElse(taskToEdit.getName());
        TaskDescription updateDescription = editTaskDescriptor.getDescription().orElse(taskToEdit.getDescription());
        DateTime updatedStartDateTime = editTaskDescriptor.getStartDateTime().orElse(taskToEdit.getStartDateTime());
        DateTime updatedEndDateTime = editTaskDescriptor.getEndDateTime().orElse(taskToEdit.getEndDateTime());
        Set<Tag> updatedTags = consolidateTags(taskToEdit, editTaskDescriptor);

        return new BasicTask(updatedName, updateDescription, false,
                updatedStartDateTime, updatedEndDateTime, updatedTags);
    }

    //@@author A0156106M
    /**
     * Creates a new overlapping BasicTask based on @param taskToMark
     * @return marked BasicTask
     * */
    static BasicTask createOverlapTask(BasicTaskFeatures taskToMark) {
        assert taskToMark != null;
        BasicTask toCopy = new BasicTask(taskToMark);
        TaskName updatedName = toCopy.getName();
        TaskDescription updatedDescription = toCopy.getDescription();
        DateTime startDateTime = toCopy.getStartDateTime();
        DateTime endDateTime = toCopy.getEndDateTime();
        boolean updateIsComplete = toCopy.getIsCompleted();
        Set<Tag> copyTags = toCopy.getTags();
        Set<Tag> updatedTags = new HashSet<Tag>(copyTags);

        try {
            updatedTags.add(new Tag(Tag.RESERVED_TAG_OVERLAP));
        } catch (IllegalValueException e) {
            e.printStackTrace();
        }
        return new BasicTask(updatedName, updatedDescription,
                updateIsComplete, startDateTime, endDateTime, updatedTags);
    }

    static BasicTask createNonOverlapTask(BasicTaskFeatures taskToMark) {
        assert taskToMark != null;
        BasicTask toCopy = new BasicTask(taskToMark);
        TaskName updatedName = toCopy.getName();
        TaskDescription updatedDescription = toCopy.getDescription();
        DateTime startDateTime = toCopy.getStartDateTime();
        DateTime endDateTime = toCopy.getEndDateTime();
        boolean updateIsComplete = toCopy.getIsCompleted();
        Set<Tag> copyTags = toCopy.getTags();
        Set<Tag> updatedTags = new HashSet<Tag>();
        for (Tag tag : copyTags) {
            if (!tag.tagName.equals(Tag.RESERVED_TAG_OVERLAP)) {
                try {
                    updatedTags.add(new Tag(tag.tagName));
                } catch (IllegalValueException e) {
                    e.printStackTrace();
                }
            }
        }

        return new BasicTask(updatedName, updatedDescription,
                updateIsComplete, startDateTime, endDateTime, updatedTags);
    }



    //@@author A0142675B
    /**
     * Adds and removes the tags accordingly.
     */
    private static Set<Tag> consolidateTags(
                   BasicTaskFeatures taskToEdit, EditTaskDescriptor editTaskDescriptor)
                           throws TagNotFoundException, IllegalValueException {
        Set<Tag> updatedTags = new HashSet<Tag>();
        Set<Tag> existingTags = taskToEdit.getTags();

        boolean hasNewPriorityTag = false;

        if (editTaskDescriptor.newTags != null) {
            Iterator<Tag> tag = editTaskDescriptor.newTags.iterator();
            while (tag.hasNext()) {
                Tag tagToBeAdded = tag.next();
                if (tagToBeAdded.isPriorityTag() && (!hasNewPriorityTag)) {
                    updatedTags.add(tagToBeAdded);
                    hasNewPriorityTag = true;
                    break;
                }
            }
        }

        if (existingTags != null) {
            Iterator<Tag> tag = existingTags.iterator();
            while (tag.hasNext()) {
                Tag tagToBeRetained = tag.next();
                if (!tagToBeRetained.isPriorityTag()) {
                    updatedTags.add(tagToBeRetained);
                } else if (tagToBeRetained.isPriorityTag() && (!hasNewPriorityTag)) {
                    updatedTags.add(tagToBeRetained);
                }

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


        if (editTaskDescriptor.newTags != null) {
            Iterator<Tag> tag = editTaskDescriptor.newTags.iterator();
            while (tag.hasNext()) {
                Tag tagToBeAdded = tag.next();
                if (!tagToBeAdded.isPriorityTag()) {
                    updatedTags.add(tagToBeAdded);
                }
            }
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
                && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private TaskName name;
        private TaskDescription description;
        private boolean isCompleted;
        private DateTime startDateTime;
        private DateTime endDateTime;
        private Set<Tag> newTags;
        private Set<Tag> removeTags;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.name;
            this.description = toCopy.description;
            this.isCompleted = toCopy.isCompleted;
            this.startDateTime = toCopy.startDateTime;
            this.endDateTime = toCopy.endDateTime;
            this.newTags = toCopy.newTags;
            this.removeTags = toCopy.removeTags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name,
                                               this.description,
                                               this.startDateTime,
                                               this.endDateTime,
                                               this.newTags,
                                               this.removeTags);
        }

        public void setName(TaskName name) {
            this.name = name;
        }

        public Optional<TaskName> getName() {
            return Optional.ofNullable(name);
        }
        //@@author A0156106M
        public void setDescription(TaskDescription description) {
            this.description = description;
        }

        public Optional<TaskDescription> getDescription() {
            return Optional.ofNullable(description);
        }

        //@@author A0142675B
        public void setIsCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        public Optional<DateTime> getStartDateTime() {
            return Optional.ofNullable(startDateTime);
        }

        public DateTime getStartDateTimeNonOptional() {
            return startDateTime;
        }

        public void setStartDateTime(DateTime startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Optional<DateTime> getEndDateTime() {
            return Optional.ofNullable(endDateTime);
        }

        public DateTime getEndDateTimeNonOptional() {
            return endDateTime;
        }

        public void setEndDateTime(DateTime endDateTime) {
            this.endDateTime = endDateTime;
        }

        //@@author A0142675B
        public void setNewTags(Set<Tag> tags) {
            this.newTags = tags;
        }

        //@@author A0142675B
        public void setRemoveTags(Set<Tag> tags) {
            this.removeTags = tags;
        }

        public Optional<Set<Tag>> getNewTags() {
            return Optional.ofNullable(newTags);
        }

        public Optional<Set<Tag>> getRemoveTags() {
            return Optional.ofNullable(removeTags);
        }

        //@@author A0142675B
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

            DateTime thisStartDateTime = this.getStartDateTimeNonOptional();
            DateTime otherStartDateTime = e.getStartDateTimeNonOptional();

            boolean startDateTimeIsEqual = false;
            if (thisStartDateTime == null && otherStartDateTime == null) {
                startDateTimeIsEqual = true;
            } else if (thisStartDateTime != null && otherStartDateTime != null
                       && thisStartDateTime.equals(otherStartDateTime)) {
                startDateTimeIsEqual = true;
            }

            DateTime thisEndDateTime = this.getEndDateTimeNonOptional();
            DateTime otherEndDateTime = e.getEndDateTimeNonOptional();

            boolean endDateTimeIsEqual = false;
            if (thisEndDateTime == null && otherEndDateTime == null) {
                endDateTimeIsEqual = true;
            } else if (thisEndDateTime != null && otherEndDateTime != null
                       && thisEndDateTime.equals(otherEndDateTime)) {
                endDateTimeIsEqual = true;
            }
            return getName().equals(e.getName())
                    && startDateTimeIsEqual
                    && endDateTimeIsEqual
                    && getNewTags().equals(e.getNewTags())
                    && getRemoveTags().equals(e.getRemoveTags())
                    && getDescription().equals(e.getDescription());
        }
    }
}
