package seedu.whatsnext.logic.commands;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.model.task.exceptions.TaskNotFoundException;

//@@author A0156106M
/**
 * Marks an existing task as completed in the task manager.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": marks the task identified by the index number used in the last task listing to 'completed'.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_SUCCESS = "Marked Task: %1$s";
    public static final String MESSAGE_TASK_MARKED = "Selected task is already marked";

    public static final String MESSAGE_TASK_MISSING_ERROR = "The target task cannot be missing";


    private static final Logger logger = LogsCenter.getLogger(MarkCommand.class);
    public final Index targetIndex;

    public MarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException, DuplicateTaskException {
        List<BasicTaskFeatures> lastShownList = model.getFilteredTaskList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.info(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX + ": " + targetIndex.getOneBased());
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        BasicTaskFeatures taskToMark = lastShownList.get(targetIndex.getZeroBased());
        if (taskToMark.getIsCompleted()) {
            logger.info(MESSAGE_TASK_MARKED + ": " + targetIndex.getOneBased());
            throw new CommandException(MESSAGE_TASK_MARKED);
        }

        BasicTask markedTask = createMarkedTask(taskToMark);
        try {
            model.updateTask(taskToMark, markedTask);
        } catch (TaskNotFoundException e) {

            logger.warning("Targeted task missing!");
            throw new AssertionError("The target task cannot be missing");

        }
        logger.fine(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
        return new CommandResult(String.format(MESSAGE_MARK_TASK_SUCCESS, taskToMark));
    }

    //@@author A0156106M
    /**
     * Creates a new marked BasicTask based on @param taskToMark
     * @return marked BasicTask
     * */
    private static BasicTask createMarkedTask(BasicTaskFeatures taskToMark) {
        assert taskToMark != null;
        BasicTask toCopy = new BasicTask(taskToMark);
        TaskName updatedName = toCopy.getName();
        TaskDescription updatedDescription = toCopy.getDescription();
        DateTime startDateTime = toCopy.getStartDateTime();
        DateTime endDateTime = toCopy.getEndDateTime();
        toCopy.setCompleted();
        boolean updateIsComplete = toCopy.getIsCompleted();
        Set<Tag> updatedTags = toCopy.getTags();
        return new BasicTask(updatedName, updatedDescription,
                updateIsComplete, startDateTime, endDateTime, updatedTags);
    }

}
