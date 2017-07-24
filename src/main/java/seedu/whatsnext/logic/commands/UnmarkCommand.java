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
 * Marks an existing completed task as incomplete in the task manager.
 */
public class UnmarkCommand extends Command {
    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": unmarks the task identified by the index number used in the last task listing to 'completed'.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNMARK_TASK_SUCCESS = "Unmarked Task: %1$s";

    public static final String MESSAGE_TASK_UNMARKED = "Selected task is already unmarked";

    private static final Logger logger = LogsCenter.getLogger(UnmarkCommand.class);

    public final Index targetIndex;

    public UnmarkCommand(Index targetIndex) {
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
        BasicTask markedTask = createUnmarkedTask(taskToMark);
        if (!taskToMark.getIsCompleted()) {
            logger.info(MESSAGE_TASK_UNMARKED + ": " + targetIndex.getOneBased());
            throw new CommandException(MESSAGE_TASK_UNMARKED);
        }
        try {
            model.updateTask(taskToMark, markedTask);
        } catch (TaskNotFoundException e) {
            logger.warning("Targeted task missing!");
            throw new AssertionError("The target task cannot be missing");
        }
        logger.fine(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToMark));
        return new CommandResult(String.format(MESSAGE_UNMARK_TASK_SUCCESS, taskToMark));
    }

    //@@author A0156106M
    /**
     * Creates a new unmarked BasicTask based on @param taskToUnmark
     * @return marked BasicTask
     * */
    private static BasicTask createUnmarkedTask(BasicTaskFeatures taskToUnmark) {
        assert taskToUnmark != null;
        BasicTask toCopy = new BasicTask(taskToUnmark);
        TaskName updatedName = toCopy.getName();
        TaskDescription updateDescription = toCopy.getDescription();
        DateTime startDateTime = toCopy.getStartDateTime();
        DateTime endDateTime = toCopy.getEndDateTime();
        toCopy.setIncomplete();
        boolean updateIsComplete = toCopy.getIsCompleted();
        Set<Tag> updatedTags = toCopy.getTags();
        return new BasicTask(updatedName, updateDescription, updateIsComplete, startDateTime, endDateTime, updatedTags);
    }

}
