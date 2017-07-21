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

import static seedu.ticktask.testutil.EditCommandTestUtil.DESC_EVENT;
import static seedu.ticktask.testutil.EditCommandTestUtil.DESC_MEETING;
import static seedu.ticktask.testutil.EditCommandTestUtil.VALID_NAME_MEETING;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;
import seedu.ticktask.testutil.TypicalTasks;

import static seedu.ticktask.testutil.TypicalTasks.INDEX_SECOND_TASK;

//@@author A0139964M
public class EditCommandTest {
    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());


    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        ReadOnlyTask targetEntry = model.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
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
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredActiveTaskList().size() + 1);
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

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_TASK, DESC_MEETING);

        // same values -> returns true
        EditCommand.EditTaskDescriptor copyDescriptor = new EditCommand.EditTaskDescriptor(DESC_MEETING);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand(null)));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_TASK, DESC_EVENT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_TASK,  DESC_EVENT)));
    }


}

//@@author