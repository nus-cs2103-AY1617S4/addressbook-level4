package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_COMPLETED;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_INCOMPLETE;

import org.junit.Test;

import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
import seedu.whatsnext.testutil.TypicalTasks;

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

    /**
     * Executes {@code ClearCommand} on the given {@code model}, confirms that <br>
     * - the result message matches {@code ClearCommand.MESSAGE_SUCCESS} <br>
     * - the address book and filtered person list in {@code model} is empty <br>
     */
    //@@author A0149894H
    private void assertCommandSuccess(Model model) {
        ClearCommand command = new ClearCommand(PREFIX_ALL.toString());
        command.setData(model, new CommandHistory());
        CommandResult result = command.execute();

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertEquals(new ModelManager(), model);
    }
    //@@author A0149894H
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

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertEquals(modelIncomplete, model);


    }
    //@@author A0149894H
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

        assertEquals(ClearCommand.MESSAGE_SUCCESS, result.feedbackToUser);
        assertEquals(modelComplete, model);

    }

}
