package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.model.person.Task;
import seedu.ticktask.testutil.PersonUtil;
import seedu.ticktask.testutil.TestUtil;

public class AddCommandTest extends AddressBookGuiTest {

    @Test
    public void add() {
        //add one person
        Task[] currentList = td.getTypicalPersons();
        Task taskToAdd = td.hoon;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add another person
        taskToAdd = td.ida;
        assertAddSuccess(taskToAdd, currentList);
        currentList = TestUtil.addPersonsToList(currentList, taskToAdd);

        //add duplicate person
        commandBox.runCommand(PersonUtil.getAddCommand(td.hoon));
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        assertTrue(personListPanel.isListMatching(currentList));

        //add to empty list
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        assertAddSuccess(td.alice);

        //invalid command
        commandBox.runCommand("adds Johnny");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertAddSuccess(Task taskToAdd, Task... currentList) {
        commandBox.runCommand(PersonUtil.getAddCommand(taskToAdd));

        //confirm the new card contains the right data
        PersonCardHandle addedCard = personListPanel.navigateToPerson(taskToAdd.getName().fullName);
        assertMatching(taskToAdd, addedCard);

        //confirm the list now contains all previous persons plus the new person
        Task[] expectedList = TestUtil.addPersonsToList(currentList, taskToAdd);
        assertTrue(personListPanel.isListMatching(expectedList));
    }

}
