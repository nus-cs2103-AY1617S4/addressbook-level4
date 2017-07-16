package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.whatsnext.commons.core.Config;
import seedu.whatsnext.commons.core.Config.RepeatTaskManagerFilePathException;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;
/**
 *contains integration tests and unit tests for FilePathCommand
 */
//@@author A0149894H

public class FilePathCommandTest {
    public static final String TEST_ORIGINAL_LOCATION = "data";
    public static final String TEST_SECOND_LOCATION = "data/test";
    //@@author A0149894H
    @Test
    public void execute_getFilePath_success() throws RepeatTaskManagerFilePathException, CommandException {
        Config config = new Config();
        config.setTaskManagerFilePath(TEST_ORIGINAL_LOCATION);
        assertFilePathSuccess(config);
    }
    //@@author A0149894H
    @Test
    public void execute_filePathCommand_success()
            throws RepeatTaskManagerFilePathException, DuplicateTaskException, CommandException {
        Config config = new Config();
        config.setTaskManagerFilePath(TEST_SECOND_LOCATION);
        assertCompletedCommandSuccess();
    }
    /**
     * Executes {@code FilePathCommand} on the given {@code model}, confirms that <br>
     * - the result message matches {@code FilePathCommand.MESSAGE_SUCCESS} <br>
     * - file path remains the same {@code model} is empty <br>
     * @throws CommandException
     */
    //@@author A0149894H
    private void assertFilePathSuccess(Config config) throws CommandException {
        assertEquals(TEST_ORIGINAL_LOCATION, config.getTaskManagerFilePath());
    }
    //@@author A0149894H
    private void assertCompletedCommandSuccess() throws DuplicateTaskException, CommandException {


        FilePathCommand command = new FilePathCommand();
        CommandResult result = command.execute();

        assertEquals(FilePathCommand.MESSAGE_SUCCESS, result.feedbackToUser);

    }
}
