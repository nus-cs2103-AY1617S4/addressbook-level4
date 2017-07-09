//package seedu.whatsnext.logic.commands;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_FIRST_PERSON;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_SECOND_PERSON;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_THIRD_PERSON;
//
//import java.util.Arrays;
//import java.util.HashSet;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.google.common.eventbus.Subscribe;
//
//import seedu.whatsnext.commons.core.EventsCenter;
//import seedu.whatsnext.commons.core.Messages;
//import seedu.whatsnext.commons.core.index.Index;
//import seedu.whatsnext.commons.events.ui.JumpToListRequestEvent;
//import seedu.whatsnext.logic.CommandHistory;
//import seedu.whatsnext.logic.commands.exceptions.CommandException;
//import seedu.whatsnext.model.Model;
//import seedu.whatsnext.model.ModelManager;
//import seedu.whatsnext.model.UserPrefs;
//import seedu.whatsnext.model.task.ReadOnlyPerson;
//import seedu.whatsnext.testutil.TypicalPersons;
//
///**
// * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
// */
//public class SelectCommandTest {
//
//    private Model model;
//
//    private Index eventTargetedJumpIndex;
//
//    @Subscribe
//    private void recordJumpToListRequestEvent(JumpToListRequestEvent je) {
//        eventTargetedJumpIndex = Index.fromZeroBased(je.targetIndex);
//    }
//
//    @Before
//    public void setUp() {
//        EventsCenter.getInstance().registerHandler(this);
//        model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_validIndexUnfilteredList_success() throws Exception {
//        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());
//
//        assertExecutionSuccess(INDEX_FIRST_PERSON);
//        assertExecutionSuccess(INDEX_THIRD_PERSON);
//        assertExecutionSuccess(lastPersonIndex);
//    }
//
//    @Test
//    public void execute_invalidIndexUnfilteredList_failure() {
//        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
//
//        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
//    }
//
//    @Test
//    public void execute_validIndexFilteredList_success() throws Exception {
//        showFirstPersonOnly(model);
//
//        assertExecutionSuccess(INDEX_FIRST_PERSON);
//    }
//
//    @Test
//    public void execute_invalidIndexFilteredList_failure() {
//        showFirstPersonOnly(model);
//
//        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
//        // ensures that outOfBoundIndex is still in bounds of address book list
//        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
//
//        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
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
//        assertTrue(model.getFilteredPersonList().size() == 1);
//    }
//
//    /**
//     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
//     * is raised with the correct index.
//     */
//    private void assertExecutionSuccess(Index index) throws Exception {
//        eventTargetedJumpIndex = null;
//
//        SelectCommand selectCommand = prepareCommand(index);
//        CommandResult commandResult = selectCommand.execute();
//
//        assertEquals(String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, index.getOneBased()),
//                commandResult.feedbackToUser);
//        assertEquals(index, eventTargetedJumpIndex);
//    }
//
//    /**
//     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
//     * is thrown with the {@code expectedMessage}.
//     */
//    private void assertExecutionFailure(Index index, String expectedMessage) {
//        eventTargetedJumpIndex = null;
//
//        SelectCommand selectCommand = prepareCommand(index);
//
//        try {
//            selectCommand.execute();
//            fail("The expected CommandException was not thrown.");
//        } catch (CommandException ce) {
//            assertEquals(expectedMessage, ce.getMessage());
//            assertNull(eventTargetedJumpIndex);
//        }
//    }
//
//    /**
//     * Returns a {@code SelectCommand} with parameters {@code index}.
//     */
//    private SelectCommand prepareCommand(Index index) {
//        SelectCommand selectCommand = new SelectCommand(index);
//        selectCommand.setData(model, new CommandHistory());
//        return selectCommand;
//    }
//}
