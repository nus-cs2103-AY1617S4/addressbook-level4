package seedu.ticktask.logic.commands;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.model.*;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.testutil.EditTaskDescriptorBuilder;
import seedu.ticktask.testutil.TaskBuilder;

import static seedu.ticktask.testutil.EditCommandTestUtil.VALID_NAME_MEETING;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;
import seedu.ticktask.testutil.TypicalTasks;

import static org.junit.Assert.*;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_SECOND_TASK;

//@@author A0139964M
public class EditCommandTest {
    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        ReadOnlyTask targetEntry = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task validTask = new TaskBuilder().withTime("0800").withDate("01/01/2018").build();
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(validTask).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, targetEntry, validTask);
        Model expectedModel = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
        expectedModel.updateTask(targetEntry,validTask);
        CommandResult result = editCommand.execute();

        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_invalidEntryIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_MEETING).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditCommand.EditTaskDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory());
        return editCommand;
    }

}

//@@author