package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

/**
 * Clears the Task Manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String CLEAR_INCOMPLETE = "incomplete";
    public static final String CLEAR_COMPLETED = "completed";
    public static final String CLEAR_ALL = "all";
    public static final String MESSAGE_SUCCESS = "Task List has been cleared!";
    public static final Object MESSAGE_USAGE = null;
    private static final boolean COMPLETED_TASKS = false;
    private static final boolean INCOMPLETE_TASKS = true;

    public final String clearArgument;

    public ClearCommand(String clearArgument) {
        this.clearArgument = clearArgument;
    }

    //@@author A0156106M
    @Override
    public CommandResult execute() {
        requireNonNull(model);
        if (clearArgument.equals(CLEAR_ALL)) {
            model.resetData(new TaskManager());
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (clearArgument.equals(CLEAR_COMPLETED)) {
            return clearCompletedOrIncomplete(COMPLETED_TASKS);
        } else {
            return clearCompletedOrIncomplete(INCOMPLETE_TASKS);
        }
    }

    //@@author A0156106M
    /**
     * Clears all completed/incomplete task based on the input parameter
     *
     * */
    private CommandResult clearCompletedOrIncomplete(boolean isCompletedOrIncomplete) {
        ReadOnlyTaskManager readOnlyTaskManager = model.getTaskManager();
        ObservableList<Tag> tagList = readOnlyTaskManager.getTagList();
        ObservableList<BasicTask> taskList = readOnlyTaskManager.getTaskList();
        TaskManager taskManager = new TaskManager();
        for (BasicTask basicTask: taskList) {
            if (isCompletedOrIncomplete ? basicTask.getIsCompleted() : !basicTask.getIsCompleted()) {
                try {
                    taskManager.addTask(basicTask);
                } catch (DuplicateTaskException e) {
                    e.printStackTrace();
                }
            }
        }

        taskManager.syncMasterTagListWith(taskManager.getTasks());
        //taskManager.setTags(tagList);
        model.resetData(taskManager);
        return new CommandResult(MESSAGE_SUCCESS);

    }
}
