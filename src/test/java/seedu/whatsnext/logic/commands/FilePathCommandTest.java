package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.CommandHistory;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;
import seedu.whatsnext.testutil.TypicalTasks;


/**
 *contains integration tests and unit tests for FilePathCommand
 */
//@@author A0149894H

public class FilePathCommandTest {
    //private Model model;

    @Test
    public void execute_filePath_showFilePath() throws Exception, TagNotFoundException, IllegalValueException {
        Model model = new ModelManager(new TypicalTasks().getTypicalTaskManager(), new UserPrefs());
        FilePathCommand command = new FilePathCommand();
        command.setData(model, new CommandHistory());
        assertCommandSuccess(command, FilePathCommand.MESSAGE_SUCCESS + model.getTaskManagerFilePath(), model);
    }

    //@@author A0149894H
    /**
     * Executes {@code FilePathCommand} on the given {@code model}, confirms that <br>
     * - the result message matches {@code FilePathCommand.MESSAGE_SUCCESS} <br>
     * - file path remains the same {@code model} is empty <br>
     * @throws IllegalValueException
     * @throws TagNotFoundException
     * @throws CommandException
     */

    public static void assertCommandSuccess(Command command, String expectedMessage, Model model)
            throws CommandException, TagNotFoundException, IllegalValueException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
    }
}
