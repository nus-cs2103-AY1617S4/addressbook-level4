//package guitests;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.whatsnext.logic.commands.DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS;
//import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;
//import static seedu.whatsnext.model.task.BasicTask.TASK_TYPE_FLOATING;
//
//import org.junit.Test;
//
//import seedu.whatsnext.commons.core.index.Index;
//import seedu.whatsnext.logic.commands.DeleteCommand;
//import seedu.whatsnext.model.task.BasicTask;
//
//import seedu.whatsnext.testutil.TestUtil;
//
//public class DeleteCommandTest extends TaskManagerGuiTest {
//
//    @Test
//    public void delete() {
//
//        commandBox.pressEnter();
//
//        //delete the first in the list
//        BasicTask[] currentList = td.getTypicalTasks();
//        Index targetIndex = INDEX_FIRST_TASK;
//        assertDeleteSuccess(targetIndex, currentList);
//        commandBox.runCommand("undo");
//
//        //delete the last in the list
//        currentList = TestUtil.removeTasksFromList(currentList, targetIndex);
//        targetIndex = Index.fromOneBased(currentList.length);
//        assertDeleteSuccess(targetIndex, currentList);
//        commandBox.runCommand("undo");
//
//        //delete from the middle of the list
//        currentList = TestUtil.removeTasksFromList(currentList, targetIndex);
//        targetIndex = Index.fromOneBased(currentList.length / 2);
//        assertDeleteSuccess(targetIndex, currentList);
//        commandBox.runCommand("undo");
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
//        //have to sort the expectedRemainder in order first... :(((((((
//        BasicTask[] expectedRemainder = TestUtil.removeTasksFromList(currentList, index);
//
//        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " " + index.getOneBased());
//
//        BasicTask[] floatingList = new BasicTask[10];
//        int j = 0;
//
//        for (int i = 0; i < expectedRemainder.length; i++) {
//            if (expectedRemainder[i].getIsCompleted() == false) {
//                floatingList[j++] = expectedRemainder[i];
//            }
//        }
//        for (int i = 0; i < floatingList.length; i++) {
//            System.out.println(floatingList[i].getName().toString());
//        }
//        //confirm the list now contains all previous persons except the deleted person
//        assertTrue(floatingListPanel.isListMatching(floatingList));
//
//        //confirm the result message is correct
//        assertResultMessage(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
//    }
//
//}
