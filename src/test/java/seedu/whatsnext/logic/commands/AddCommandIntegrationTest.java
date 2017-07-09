//package seedu.whatsnext.logic.commands;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import seedu.whatsnext.logic.CommandHistory;
//import seedu.whatsnext.logic.commands.exceptions.CommandException;
//import seedu.whatsnext.model.Model;
//import seedu.whatsnext.model.ModelManager;
//import seedu.whatsnext.model.UserPrefs;
//import seedu.whatsnext.model.task.Person;
//import seedu.whatsnext.testutil.PersonBuilder;
//import seedu.whatsnext.testutil.TypicalPersons;
//
///**
// * Contains integration tests (interaction with the Model) for {@code AddCommand}.
// */
//public class AddCommandIntegrationTest {
//
//    private Model model;
//
//    @Before
//    public void setUp() {
//        model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
//    }
//
//    @Test
//    public void execute_newPerson_success() throws Exception {
//        Person validPerson = new PersonBuilder().build();
//
//        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//        expectedModel.addPerson(validPerson);
//
//        CommandResult commandResult = prepareCommand(validPerson, model).execute();
//
//        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
//        assertEquals(expectedModel, model);
//    }
//
//    @Test
//    public void execute_duplicatePerson_throwsCommandException() throws Exception {
//        Person personInList = new Person(model.getAddressBook().getPersonList().get(0));
//
//        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
//
//        try {
//            prepareCommand(personInList, model).execute();
//            fail("The expected CommandException was not thrown.");
//        } catch (CommandException ce) {
//            assertEquals(AddCommand.MESSAGE_DUPLICATE_TASK, ce.getMessage());
//            assertEquals(expectedModel, model);
//        }
//    }
//
//    /**
//     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
//     */
//    private AddCommand prepareCommand(Person person, Model model) {
//        AddCommand command = new AddCommand(person);
//        command.setData(model, new CommandHistory());
//        return command;
//    }
//}
