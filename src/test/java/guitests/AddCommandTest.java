//package guitests;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
//import guitests.guihandles.PersonCardHandle;
//import seedu.whatsnext.commons.core.Messages;
//import seedu.whatsnext.logic.commands.AddCommand;
//import seedu.whatsnext.logic.commands.ClearCommand;
//import seedu.whatsnext.model.task.Person;
//import seedu.whatsnext.testutil.PersonUtil;
//import seedu.whatsnext.testutil.TestUtil;
//
//public class AddCommandTest extends AddressBookGuiTest {
//
//    @Test
//    public void add() {
//        //add one person
//        Person[] currentList = td.getTypicalPersons();
//        Person personToAdd = td.hoon;
//        assertAddSuccess(personToAdd, currentList);
//        currentList = TestUtil.addPersonsToList(currentList, personToAdd);
//
//        //add another person
//        personToAdd = td.ida;
//        assertAddSuccess(personToAdd, currentList);
//        currentList = TestUtil.addPersonsToList(currentList, personToAdd);
//
//        //add duplicate person
//        commandBox.runCommand(PersonUtil.getAddCommand(td.hoon));
//        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
//        assertTrue(personListPanel.isListMatching(currentList));
//
//        //add to empty list
//        commandBox.runCommand(ClearCommand.COMMAND_WORD);
//        assertAddSuccess(td.alice);
//
//        //invalid command
//        commandBox.runCommand("adds Johnny");
//        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
//    }
//
//    private void assertAddSuccess(Person personToAdd, Person... currentList) {
//        commandBox.runCommand(PersonUtil.getAddCommand(personToAdd));
//
//        //confirm the new card contains the right data
//        PersonCardHandle addedCard = personListPanel.navigateToPerson(personToAdd.getName().fullName);
//        assertMatching(personToAdd, addedCard);
//
//        //confirm the list now contains all previous persons plus the new person
//        Person[] expectedList = TestUtil.addPersonsToList(currentList, personToAdd);
//        assertTrue(personListPanel.isListMatching(expectedList));
//    }
//
//}
