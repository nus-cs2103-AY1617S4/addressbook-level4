package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.testutil.TaskBuilder;
import seedu.whatsnext.testutil.TypicalTasks;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() throws Exception {
        BasicTask validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.addTask(validTask);

        CommandResult commandResult = prepareCommand(validTask, model).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() throws Exception {
        BasicTask taskInList = new BasicTask(model.getTaskManager().getTaskList().get(0));

        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());

        try {
            prepareCommand(taskInList, expectedModel).execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(AddCommand.MESSAGE_DUPLICATE_TASK, ce.getMessage());
            //assertEquals(expectedModel, model);
        }
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code task} into the {@code model}.
     */
    private AddCommand prepareCommand(BasicTask task, Model model) {
        AddCommand command = new AddCommand(task);
        command.setData(model, new CommandHistory());
        return command;
    }
}
