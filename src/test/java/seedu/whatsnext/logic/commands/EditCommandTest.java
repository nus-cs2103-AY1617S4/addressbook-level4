package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.whatsnext.testutil.EditCommandTestUtil.DESC_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.DESC_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_DESCRIPTION_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_ENDDATETIME_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_NAME_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_NAME_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_NAME_READBORNACRIME;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_STARTDATETIME_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_CS2103;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_HIGH;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_SECOND_TASK;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.testutil.EditTaskDescriptorBuilder;
import seedu.whatsnext.testutil.TaskBuilder;
import seedu.whatsnext.testutil.TypicalTasks;

//@@author A0142675B
/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        BasicTask editedTask = new TaskBuilder().withName(VALID_NAME_PROJECTMEETING)
                               .withStartDateTime(VALID_STARTDATETIME_PROJECTMEETING)
                               .withEndDateTime(VALID_ENDDATETIME_PROJECTMEETING)
                               .withTags(VALID_TAG_HIGH)
                               .withDescription(VALID_DESCRIPTION_PROJECTMEETING).build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).withNewTags(VALID_TAG_HIGH)
                                        .withRemoveTags(VALID_TAG_CS2103).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    /*
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        BasicTaskFeatures lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        BasicTask editedTask = taskInList.withName(VALID_NAME_READBORNACRIME)
                .withTags(VALID_TAG_LOW).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_READBORNACRIME)
                .withNewTags(VALID_TAG_LOW).build();
        EditCommand editCommand = prepareCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }*/

    /*
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws Exception {
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        BasicTaskFeatures editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }*/

    /*
    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstTaskOnly();

        BasicTaskFeatures taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        BasicTask editedTask = new TaskBuilder(taskInFilteredList).withName(VALID_NAME_PROJECTDEMO).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECTDEMO).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TaskManager(model.getTaskManager()), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        CommandTestUtil.assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }*/


    @Test
    public void execute_duplicateTaskUnfilteredList_failure() throws Exception {
        BasicTask firstTask = new BasicTask(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditCommand editCommand = prepareCommand(INDEX_SECOND_TASK, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    //@@author A0156106M
    @Test
    public void execute_duplicateTaskFilteredList_failure() throws Exception {
        showFirstTaskOnly();
        // edit task in filtered list into a duplicate in task manager
        BasicTaskFeatures taskInList = model.getTaskManager().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder(taskInList).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_READBORNACRIME).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of task manager
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() throws Exception {
        showFirstTaskOnly();
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of task manager list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskManager().getTaskList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECTDEMO).build());

        CommandTestUtil.assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_TASK, DESC_PROJECTMEETING);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_PROJECTMEETING);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand("all")));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_TASK, DESC_PROJECTMEETING)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_TASK, DESC_PROJECTDEMO)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditTaskDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory());
        return editCommand;
    }

    /**
     * Updates the filtered list to show only the first task in the {@code model}'s task manager.
     */
    private void showFirstTaskOnly() {
        BasicTaskFeatures task = model.getTaskManager().getTaskList().get(0);
        final String[] splitName = task.getName().fullTaskName.split("\\s+");
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));

        assertTrue(model.getFilteredTaskList().size() == 1);
    }
}
