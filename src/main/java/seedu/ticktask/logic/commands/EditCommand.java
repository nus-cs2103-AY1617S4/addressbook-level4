package seedu.ticktask.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.commons.util.CollectionUtil;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.TaskType;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

/**
 * Edits the details of an existing task in the TickTask.
 */
public class EditCommand extends Command {
    public static final String COMMAND_WORD = "edit";

    //@@author A0139964M
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified"
            + "by the index number used in the last task listing."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Final report submission "
            + PREFIX_DATE + "08/26/17";
    //@@author
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the TickTask program.";

    public static final String MESSAGE_PAST_TASK = "Warning: This task is already passed the current date/time";
    public static final String MESSAGE_EVENT_CLASH = "Warning: There is another task going on within the same time frame: %1$s";


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

    @Override

    public CommandResult execute() throws CommandException, IllegalValueException {

        List<ReadOnlyTask> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        ReadOnlyTask taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        try {
            model.updateTask(taskToEdit, editedTask);
            model.updateFilteredListToShowAll();

            //@@author A0139964M
            if (!editedTask.isChornological()) {
                return new CommandResult(String.format(MESSAGE_PAST_TASK, taskToEdit));
            }
            //@@author
            if (editedTask.getTaskType().toString().equals("event") && model.eventClash(taskToEdit) != null) {
                    return new CommandResult(String.format(MESSAGE_EVENT_CLASH, taskToEdit));
            }
            
            return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, taskToEdit));
            
        } catch (DuplicateTaskException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("The target task cannot be missing");
        } 

        
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code editTaskDescriptor}.
     * @throws IllegalValueException
     */
    private static Task createEditedTask(ReadOnlyTask taskToEdit,
            EditTaskDescriptor editTaskDescriptor) throws IllegalValueException {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElse(taskToEdit.getName());
        TaskType updatedTaskType = editTaskDescriptor.getTaskType().orElse(taskToEdit.getTaskType());
        DueTime updatedTime = editTaskDescriptor.getTime().orElse(taskToEdit.getTime());
        DueDate updatedDate = editTaskDescriptor.getDate().orElse(taskToEdit.getDate());
        Set<Tag> updatedTags = editTaskDescriptor.getTags().orElse(taskToEdit.getTags());

        //@@author A0139819N
        if (editTaskDescriptor.getTaskType().toString().equals("Optional[floating]")) {

            if (taskToEdit.getTaskType().getValue().equals("floating")) {
                throw new ParseException("Task is already floating!");
            } else {
                updatedTime = new DueTime("");
                updatedDate = new DueDate("");
            }
        }
        //@@author
        return new Task(updatedName, updatedTime, updatedTaskType, updatedDate, updatedTags);
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
        private Name name;
        private DueTime time;
        private TaskType type;
        private DueDate date;
        private Set<Tag> tags;

        public EditTaskDescriptor() {}

        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            this.name = toCopy.name;
            this.time = toCopy.time;
            this.type = toCopy.type;
            this.date = toCopy.date;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.time, this.type, this.date, this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setTime(DueTime time) {
            if (time.toString().equals("") || time.toString().equals(" ")) {
                this.time = null;
            } else {
                this.time = time;
            }
        }

        public Optional<DueTime> getTime() {
            return Optional.ofNullable(time);
        }

        public void setTaskType(TaskType type) {
            if (type.toString().equals("") || type.toString().equals(" ")) {
                this.type = null;
            } else {
                this.type = type;
            }
        }

        public Optional<TaskType> getTaskType() {
            return Optional.ofNullable(type);
        }

        public void setDate(DueDate date) {
            if (date.toString().equals("") || date.toString().equals(" ")) {
                this.date = null;
            } else {
                this.date = date;
            }
        }

        public Optional<DueDate> getDate() {
            return Optional.ofNullable(date);
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
                    && getTime().equals(e.getTime())
                    && getTaskType().equals(e.getTaskType())
                    && getDate().equals(e.getDate())
                    && getTags().equals(e.getTags());
        }
    }
}
