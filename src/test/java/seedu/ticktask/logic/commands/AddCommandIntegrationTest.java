package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.person.Task;
import seedu.ticktask.testutil.PersonBuilder;
import seedu.ticktask.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(new TypicalPersons().getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newPerson_success() throws Exception {
        Task validTask = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPerson(validTask);

        CommandResult commandResult = prepareCommand(validTask, model).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validTask), commandResult.feedbackToUser);
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Task taskInList = new Task(model.getAddressBook().getTaskList().get(0));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        try {
            prepareCommand(taskInList, model).execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(AddCommand.MESSAGE_DUPLICATE_PERSON, ce.getMessage());
            assertEquals(expectedModel, model);
        }
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code person} into the {@code model}.
     */
    private AddCommand prepareCommand(Task task, Model model) {
        AddCommand command = new AddCommand(task);
        command.setData(model, new CommandHistory());
        return command;
    }
}
