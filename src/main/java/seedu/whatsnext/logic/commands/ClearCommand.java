package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

/**
 * Clears incomplete, completed, expired or all BasicTasks in the Task Manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String CLEAR_INCOMPLETE = "incomplete";
    public static final String CLEAR_COMPLETED = "completed";
    public static final String CLEAR_ALL = "all";
    public static final String MESSAGE_SUCCESS = "Task List has been cleared!";
    public static final String MESSAGE_SUCCESS_CLEAR_INCOMPLETE = "Incomplete tasks have been cleared!";
    public static final String MESSAGE_SUCCESS_CLEAR_COMPLETED = "Completed tasks have been cleared!";
    public static final String MESSAGE_SUCCESS_CLEAR_EXPIRED = "Expired tasks have been cleared!";
    public static final String MESSAGE_USAGE = "To clear incomplete tasks: clear incomplete\n"
            + "To clear completed tasks: clear completed\n"
            + "To clear expired tasks: clear expired\n"
            + "To clear all tasks: clear all";
    private static final boolean COMPLETED_TASKS = false;
    private static final boolean INCOMPLETE_TASKS = true;

    private static final Logger logger = LogsCenter.getLogger(ClearCommand.class);

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
        } else if (clearArgument.equals(CLEAR_INCOMPLETE)) {
            return clearCompletedOrIncomplete(INCOMPLETE_TASKS);
        } else {
            return clearExpired();
        }
    }

    //@@author A0156106M
    /**
     * Clears all completed/incomplete task based on the input parameter
     *
     * */
    private CommandResult clearCompletedOrIncomplete(boolean isCompletedOrIncomplete) {
        ReadOnlyTaskManager readOnlyTaskManager = model.getTaskManager();
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
        model.resetData(taskManager);
        if (isCompletedOrIncomplete) {
            logger.info(MESSAGE_SUCCESS_CLEAR_INCOMPLETE);
            return new CommandResult(MESSAGE_SUCCESS_CLEAR_INCOMPLETE);
        } else {
            logger.info(MESSAGE_SUCCESS_CLEAR_COMPLETED);
            return new CommandResult(MESSAGE_SUCCESS_CLEAR_COMPLETED);
        }
    }

    //@@author A0142675B
    /**
     * Clear Expired tasks based on the endDateTime
     * @return MESSAGE_SUCCESS_CLEAR_EXPIRED
     */
    private CommandResult clearExpired() {
        ReadOnlyTaskManager readOnlyTaskManager = model.getTaskManager();
        ObservableList<BasicTask> taskList = readOnlyTaskManager.getTaskList();
        TaskManager taskManager = new TaskManager();
        Date currentTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        currentTime = cal.getTime();
        for (BasicTask basicTask: taskList) {
            if (basicTask.getEndDateTime().toString().equals(DateTime.INIT_DATETIME_VALUE)
                || !basicTask.getEndDateTime().isBefore(currentTime)) {
                try {
                    taskManager.addTask(basicTask);
                } catch (DuplicateTaskException e) {
                    e.printStackTrace();
                }
            }
        }

        taskManager.syncMasterTagListWith(taskManager.getTasks());
        model.resetData(taskManager);
        logger.info(MESSAGE_SUCCESS_CLEAR_EXPIRED);
        return new CommandResult(MESSAGE_SUCCESS_CLEAR_EXPIRED);
    }
}
