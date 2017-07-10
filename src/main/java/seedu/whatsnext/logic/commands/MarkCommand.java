package seedu.whatsnext.logic.commands;

import java.util.List;
import java.util.Set;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;

//@@author A0156106M
/**
 * Marks an existing task in the task manager.
 */
public class MarkCommand extends Command {
    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": marks the task identified by the index number used in the last task listing to 'completed'.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";


    private final Index targetIndex;

    public MarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<BasicTaskFeatures> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        BasicTaskFeatures taskToMark = lastShownList.get(targetIndex.getZeroBased());
        BasicTask markedTask = createMarkedTask(taskToMark);
        try {
            model.updateTask(taskToMark, markedTask);

        } catch (TaskNotFoundException e) {
            throw new AssertionError("The target task cannot be missing");
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

    /**
     * Creates a new marked BasicTask based on @param taskToMark
     * @return marked BasicTask
     * */
    private static BasicTask createMarkedTask(BasicTaskFeatures taskToMark) {
        assert taskToMark != null;
        TaskName updatedName = taskToMark.getName();
        DateTime updatedStartDateTime = taskToMark.getStartDateTime();
        DateTime updatedEndDateTime = taskToMark.getEndDateTime();
        taskToMark.setCompleted();
        boolean updateIsComplete = taskToMark.getIsCompleted();
        Set<Tag> updatedTags = taskToMark.getTags();
        return new BasicTask(updatedName, updatedStartDateTime, updatedEndDateTime, updateIsComplete, updatedTags);
    }

}
