//package guitests;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.whatsnext.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
//import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;
//
//import org.junit.Test;
//
//import seedu.whatsnext.commons.core.index.Index;
//import seedu.whatsnext.logic.commands.AddCommand;
//import seedu.whatsnext.logic.commands.DeleteCommand;
//import seedu.whatsnext.model.task.BasicTask;
//import seedu.whatsnext.model.task.BasicTaskFeatures;
//import seedu.whatsnext.model.task.TaskDescription;
//import seedu.whatsnext.model.task.TaskName;
//import seedu.whatsnext.testutil.TestUtil;
//
//public class DeleteCommandTest extends TaskManagerGuiTest {
//
//    @Test
//    public void delete() {
//
//        commandBox.pressEnter();
//        commandBox.runCommand("add Buy a country m/to rule");
//        BasicTask floatToAdd = new BasicTask(new TaskName("Buy a country"),
//                new TaskDescription("to rule"), getTagSet());
//        assertResultMessage(floatToAdd.getTaskDetails());
//        BasicTaskFeatures selectedFloatingTask = floatingListPanel.getSelectedTasks().get(0).getKey();
//        assertEquals(floatToAdd, selectedFloatingTask);
//        //add duplicate floating task
//        commandBox.runCommand("add Buy a country m/to rule");
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        commandBox.runCommand("undo");
//        commandBox.runCommand("undo");
//
//        //delete the first in the list
//        BasicTask[] currentList = td.getTypicalTasks();
//        Index targetIndex = INDEX_FIRST_TASK;
//        assertDeleteSuccess(targetIndex, currentList);
//
//        //delete the last in the list
//        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
//        targetIndex = Index.fromOneBased(currentList.length);
//        assertDeleteSuccess(targetIndex, currentList);
//
//        //delete from the middle of the list
//        currentList = TestUtil.removePersonFromList(currentList, targetIndex);
//        targetIndex = Index.fromOneBased(currentList.length / 2);
//        assertDeleteSuccess(targetIndex, currentList);
//
//        //invalid index
//        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " " + currentList.length + 1);
//        assertResultMessage("The person index provided is invalid");
//
//    }
//
//    /**
//     * Runs the delete command to delete the task at {@code index} and confirms the result is correct.
//     * @param currentList A copy of the current list of tasks (before deletion).
//     */
//    private void assertDeleteSuccess(Index index, final BasicTask[] currentList) {
//        BasicTask taskToDelete = currentList[index.getZeroBased()];
//        BasicTask[] expectedRemainder = TestUtil.removeTasksFromList(currentList, index);
//
//        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " " + index.getOneBased());
//
//        //confirm the list now contains all previous persons except the deleted person
//        assertTrue(taskListPanel.isListMatching(expectedRemainder));
//
//        //confirm the result message is correct
//        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, personToDelete));
//    }
//
//}
