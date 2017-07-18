package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.testutil.TypicalTasks;
//@@author A013847A
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;
    private ListCommand listCommandFloat;

    @Before
    public void setUp() {
        model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
        expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());

        listCommand = new ListCommand("");
        listCommand.setData(model, new CommandHistory());
        
        listCommandFloat = new ListCommand("floating");
        listCommandFloat.setData(model, new CommandHistory());
    }
    
    @Test
    public void isValidCommandReturnsTrue(){
        assertTrue(ListCommand.isValidCommand("floating"));
        assertTrue(ListCommand.isValidCommand("deadline"));
        assertTrue(ListCommand.isValidCommand("event"));
        assertTrue(ListCommand.isValidCommand("today"));
    }
    @Test
    public void isValidCommandReturnsFalse(){
        assertFalse(ListCommand.isValidCommand("randomname"));
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() throws Exception {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() throws Exception {
        showFirstTaskOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
    
    

    /**
     * Updates the filtered list to show only the first task in the {@code model}'s TickTask.
     */
    private void showFirstTaskOnly(Model model) {
        ReadOnlyTask task = model.getTickTask().getTaskList().get(0);
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));

        assertTrue(model.getFilteredTaskList().size() == 1);
        
    }
    
    /**
     * Updates the filtered list to show only the first task in the {@code model}'s TickTask.
     */
    private void showFloatTaskOnly(Model model) {
        ReadOnlyTask task = model.getTickTask().getTaskList().get(0);
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new HashSet<>(Arrays.asList(splitName)));
        //if(task.getTaskType().equals("floating")){
            assertTrue(model.getFilteredTaskList().size() == 1);
       // }
        
        
    }
    

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the TickTask and the filtered task list in the {@code model} matches that of {@code expectedModel}
     * @throws WarningException 
     * @throws IllegalValueException 
     */
    public static void assertCommandSuccess(Command command, Model model, String expectedMessage, Model expectedModel)
            throws CommandException, IllegalValueException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, model);
    }
    
}
