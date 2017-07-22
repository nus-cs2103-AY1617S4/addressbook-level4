package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;
import seedu.whatsnext.testutil.TypicalTasks;


//@@author A0149894H
public class ChangePathCommandTest {
    private static String location = "data/test";
    private static File file = new File(location);

    //@@author A0149894H
    @Test
    public void execute_noSaveLocation_success() throws CommandException, TagNotFoundException, IllegalValueException {
        Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        ChangePathCommand command = new ChangePathCommand(file);
        command.setData(model,  new CommandHistory());
        assertCommandSuccess(command, command.MESSAGE_SUCCESS, model);
    }

    //@@author A0149894Hs
    /**
     * Executes {@code ChangePathCommand} on the given {@code model}, confirms that <br>
     * - the result message matches {@code ChangePathCommand.MESSAGE_SUCCESS} <br>
     * - file path remains the same {@code model} is empty <br>
     * @throws IllegalValueException
     * @throws TagNotFoundException
     * @throws CommandException
     */
    public static void assertCommandSuccess(Command command, String messageSuccess, Model model)
            throws CommandException, TagNotFoundException, IllegalValueException {
        CommandResult result = command.execute();
        String expectedMessage = messageSuccess + model.getTaskManagerFilePath();
        assertEquals(expectedMessage, result.feedbackToUser);
    }
}
