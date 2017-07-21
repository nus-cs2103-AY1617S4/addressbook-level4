package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_COMPLETED;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_EXPIRED;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_INCOMPLETE;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.testutil.TypicalTasks;
//@@author A0149894H
public class ClearCommandTest {

    //@@author A0149894H
    @Test
    public void execute_emptyTaskManager_success() {
        Model model = new ModelManager();
        assertCommandSuccess(model);
    }

    //@@author A0149894H
    @Test
    public void execute_nonEmptyTaskManager_success() {
        Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        assertCommandSuccess(model);
    }

    //@@author A0149894H
    @Test
    public void execute_clearIncompletedTasks_success() throws DuplicateTaskException {
        Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        assertIncompleteCommandSuccess(model);
    }

    //@@author A0149894H
    @Test
    public void execute_completedTasks_success() throws DuplicateTaskException {
        Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        assertCompletedCommandSuccess(model);
    }

    //@@author A0142675B
    @Test
    public void execute_expiredTasks_success() throws DuplicateTaskException {
        Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        assertExpiredCommandSuccess(model);
    }


    /**
     * Executes {@code ClearCommand} on the given {@code model}, confirms that <br>
     * - the result message matches {@code ClearCommand.MESSAGE_SUCCESS} <br>
     * - the taskManager and filtered task list in {@code model} is empty <br>
     */
    //@@author A0149894H
    /**
     * Asserts that clear all command creates empty task list.
     */
    private void assertCommandSuccess(Model model) {
        ClearCommand command = new ClearCommand(PREFIX_ALL.toString());
        command.setData(model, new CommandHistory());
        CommandResult result = command.execute();

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertEquals(new ModelManager(), model);
    }
    //@@author A0149894H
    /**
     * Asserts that clear incomplete returns task list with only complete tasks
     */
    private void assertIncompleteCommandSuccess(Model model) throws DuplicateTaskException {
        TaskManager taskManagerIncomplete = new TaskManager();
        for (BasicTask task: model.getTaskManager().getTaskList()) {
            if (task.getIsCompleted()) {
                taskManagerIncomplete.addTask(task);
            }
        }
        Model modelIncomplete = new ModelManager(taskManagerIncomplete, new UserPrefs());

        ClearCommand command = new ClearCommand(PREFIX_INCOMPLETE.toString());
        command.setData(model, new CommandHistory());
        CommandResult result = command.execute();

        assertEquals(ClearCommand.MESSAGE_SUCCESS_CLEAR_INCOMPLETE, result.feedbackToUser);
        assertEquals(modelIncomplete, model);


    }
    //@@author A0149894H
    /**
     * Asserts that clear completed returns task list of only incomplete tasks
     */
    private void assertCompletedCommandSuccess(Model model) throws DuplicateTaskException {
        TaskManager taskManagerComplete = new TaskManager();
        for (BasicTask task: model.getTaskManager().getTaskList()) {
            if (!task.getIsCompleted()) {
                taskManagerComplete.addTask(task);
            }
        }
        Model modelComplete = new ModelManager(taskManagerComplete, new UserPrefs());

        ClearCommand command = new ClearCommand(PREFIX_COMPLETED.toString());
        command.setData(model, new CommandHistory());
        CommandResult result = command.execute();

        assertEquals(ClearCommand.MESSAGE_SUCCESS_CLEAR_COMPLETED, result.feedbackToUser);
        assertEquals(modelComplete, model);

    }

    //@@author A0149894H
    /**
     * Asserts that clear expired command returns task list of not expired tasks
     */
    private void assertExpiredCommandSuccess(Model model) throws DuplicateTaskException {
        TaskManager taskManagerNotExpired = new TaskManager();
        Date currentTime = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentTime);
        currentTime = cal.getTime();
        for (BasicTask task:model.getTaskManager().getTaskList()) {
            if (task.getEndDateTime().toString().equals(DateTime.INIT_DATETIME_VALUE)
                    || !task.getEndDateTime().isBefore(currentTime)) {
                taskManagerNotExpired.addTask(task);
            }
        }

        Model modelNotExpired = new ModelManager(taskManagerNotExpired, new UserPrefs());

        ClearCommand command = new ClearCommand(PREFIX_EXPIRED.toString());
        command.setData(model, new CommandHistory());
        CommandResult result = command.execute();

        assertEquals(ClearCommand.MESSAGE_SUCCESS_CLEAR_EXPIRED, result.feedbackToUser);
        assertEquals(modelNotExpired, model);
    }

}
