//package guitests;
//
//import static org.junit.Assert.assertTrue;
//import static seedu.whatsnext.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_FIRST_PERSON;
//
//import org.junit.Test;
//
//import seedu.whatsnext.commons.core.index.Index;
//import seedu.whatsnext.logic.commands.DeleteCommand;
//import seedu.whatsnext.model.person.Person;
//import seedu.whatsnext.testutil.TestUtil;
//
//public class DeleteCommandTest extends AddressBookGuiTest {
//
//    @Test
//    public void delete() {
//
//        //delete the first in the list
//        Person[] currentList = td.getTypicalPersons();
//        Index targetIndex = INDEX_FIRST_PERSON;
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
//     * Runs the delete command to delete the person at {@code index} and confirms the result is correct.
//     * @param currentList A copy of the current list of persons (before deletion).
//     */
//    private void assertDeleteSuccess(Index index, final Person[] currentList) {
//        Person personToDelete = currentList[index.getZeroBased()];
//        Person[] expectedRemainder = TestUtil.removePersonFromList(currentList, index);
//
//        commandBox.runCommand(DeleteCommand.COMMAND_WORD + " " + index.getOneBased());
//
//        //confirm the list now contains all previous persons except the deleted person
//        assertTrue(personListPanel.isListMatching(expectedRemainder));
//
//        //confirm the result message is correct
//        assertResultMessage(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
//    }
//
//}
