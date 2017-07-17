package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.testutil.TaskBuilder;
import seedu.whatsnext.testutil.TypicalTasks;

//@@author A0154986L
public class RedoCommandTest {

    /***
     * Tests redoing an undo add command.
     */
    @Test
    public void execute_taskAddUndoThenRedo_success() throws Exception {
        Model actualModel = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        BasicTask validFloatingTask = new TaskBuilder(BasicTask.TASK_TYPE_FLOATING).build();

        //adds a task to model
        AddCommand addCommand = new AddCommand(validFloatingTask);
        addCommand.setData(actualModel, new CommandHistory());

        //undo the add from that model
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(actualModel, new CommandHistory());

        //redo the add from that model
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(actualModel, new CommandHistory());

        //command result of the redo
        CommandResult result = addCommand.execute();
        result = undoCommand.execute();
        result = redoCommand.execute();

        //expected message
        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS);

        //expected model after add then undo
        Model expectedModel = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        expectedModel.addTask(validFloatingTask);
        expectedModel.undoTaskManager();
        expectedModel.redoTaskManager();

        //expected vs actual
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, actualModel);
    }


    /***
     * Tests undo failure.
     */
    @Test
    public void execute_nothingToRedo_fail() throws Exception {
        Model actualModel = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());

        //simply redo the model
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(actualModel, new CommandHistory());

        //expected message
        String expectedMessage = String.format(RedoCommand.MESSAGE_FAILURE);

        //expected vs actual
        CommandTestUtil.assertCommandFailure(redoCommand, actualModel, expectedMessage);
    }
}
