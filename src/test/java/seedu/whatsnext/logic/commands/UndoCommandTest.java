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
public class UndoCommandTest {

    private Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());

    /***
     * Tests undoing an add command.
     */
    @Test
    public void execute_taskAddThenUndo_success() throws Exception {
        BasicTask validFloatingTask = new TaskBuilder(BasicTask.TASK_TYPE_FLOATING).build();

        //adds a task to model
        AddCommand addCommand = new AddCommand(validFloatingTask);
        addCommand.setData(model, new CommandHistory());

        //undo the add from that model
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory());

        //command result of the undo
        CommandResult result = addCommand.execute();
        result = undoCommand.execute();

        //expected message
        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS);

        //expected model after add then undo
        ModelManager expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        expectedModel.addTask(validFloatingTask);
        expectedModel.undoTaskManager();

        //expected vs actual
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, model);
    }


    /***
     * Tests undo failure.
     */
    @Test
    public void execute_nothingToUndo_fail() throws Exception {
        //simply undo the model
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory());

        //expected message
        String expectedMessage = String.format(UndoCommand.MESSAGE_FAILURE);

        //expected vs actual
        CommandTestUtil.assertCommandFailure(undoCommand, model, expectedMessage);
    }
}
