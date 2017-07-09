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

/**
 * Marks an existing task in the task manager.
 * @@author A0156106M
 */
public class UnmarkCommand extends Command {
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": unmarks the task identified by the index number used in the last task listing to 'completed'.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book.";

    private final Index targetIndex;

    public UnmarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<BasicTaskFeatures> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        BasicTaskFeatures taskToMark = lastShownList.get(targetIndex.getZeroBased());
        BasicTask markedTask = createUnmarkedTask(taskToMark);
        try {
            model.updateTask(taskToMark, markedTask);

        } catch (TaskNotFoundException e) {
            throw new AssertionError("The target task cannot be missing");
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
        model.updateFilteredListToShowAll();
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToMark));
    }

    /**
     * Creates a new unmarked BasicTask based on @param taskToUnmark
     * @return marked BasicTask
     * */
    private static BasicTask createUnmarkedTask(BasicTaskFeatures taskToUnmark) {
        assert taskToUnmark != null;
        TaskName updatedName = taskToUnmark.getName();
        DateTime updatedStartDateTime = taskToUnmark.getStartDateTime();
        DateTime updatedEndDateTime = taskToUnmark.getEndDateTime();
        taskToUnmark.setIncompleted();
        boolean updateIsComplete = taskToUnmark.getIsCompleted();
        Set<Tag> updatedTags = taskToUnmark.getTags();
        return new BasicTask(updatedName, updatedStartDateTime, updatedEndDateTime, updateIsComplete, updatedTags);
    }

}
