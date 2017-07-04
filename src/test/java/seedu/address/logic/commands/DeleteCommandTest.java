//package seedu.whatsnext.logic.commands;
//
//import static org.junit.Assert.assertTrue;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_FIRST_PERSON;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_SECOND_PERSON;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.HashSet;
//
//import org.junit.Test;
//
//import seedu.whatsnext.commons.core.Messages;
//import seedu.whatsnext.commons.core.index.Index;
//import seedu.whatsnext.logic.CommandHistory;
//import seedu.whatsnext.model.Model;
//import seedu.whatsnext.model.ModelManager;
//import seedu.whatsnext.model.UserPrefs;
//import seedu.whatsnext.model.person.ReadOnlyPerson;
//import seedu.whatsnext.testutil.TypicalPersons;
//
///**
// * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
// */
//public class DeleteCommandTest {
//
//    private Model model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() throws Exception {
//        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
//
//        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        expectedModel.deletePerson(personToDelete);
//
//        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
//        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
//        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
//
//        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_validIndexFilteredList_success() throws Exception {
//        showFirstPersonOnly(model);
//
//        ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
//        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_PERSON);
//
//        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);
//
//        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        expectedModel.deletePerson(personToDelete);
//        showNoPerson(expectedModel);
//
//        CommandTestUtil.assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_throwsCommandException() throws Exception {
//        showFirstPersonOnly(model);
//
//        Index outOfBoundIndex = INDEX_SECOND_PERSON;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
//
//        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
//
//        CommandTestUtil.assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
//    }
//
//    /**
//     * Returns a {@code DeleteCommand} with the parameter {@code index}.
//     */
//    private DeleteCommand prepareCommand(Index index) {
//        DeleteCommand deleteCommand = new DeleteCommand(index);
//        deleteCommand.setData(model, new CommandHistory());
//        return deleteCommand;
//    }
//
//    /**
//     * Updates {@code model}'s filtered list to show only the first person from the address book.
//     */
//    private void showFirstPersonOnly(Model model) {
//        ReadOnlyPerson person = model.getAddressBook().getPersonList().get(0);
//        final String[] splitName = person.getName().fullName.split("\\s+");
//        model.updateFilteredPersonList(new HashSet<>(Arrays.asList(splitName)));
//
//        assert model.getFilteredPersonList().size() == 1;
//    }
//
//    /**
//     * Updates {@code model}'s filtered list to show no one.
//     */
//    private void showNoPerson(Model model) {
//        model.updateFilteredPersonList(Collections.emptySet());
//
//        assert model.getFilteredPersonList().isEmpty();
//    }
//}
