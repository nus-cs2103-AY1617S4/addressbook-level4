package seedu.whatsnext.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import org.junit.Test;

import seedu.whatsnext.logic.commands.ChangePathCommand;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.WrongCommand;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ChangePathCommand parser.
 */
public class ChangePathCommandParserTest {
    private ChangePathCommandParser parser = new ChangePathCommandParser();
    private String nonExistentFilePath = "data/test";
    private String usage = ChangePathCommand.MESSAGE_USAGE;
    private String messageInvalid = MESSAGE_INVALID_COMMAND_FORMAT;

    @Test
    public void parse_validArgs_returnsChangeCommand() throws Exception {
        Command command = parser.parse("");
        assertEquals(new WrongCommand(String.format(messageInvalid, usage)).getClass(), command.getClass());
    }

    @Test
    public void parse_nonExistentLocation_returnsSuccess() throws Exception {
        Command command = parser.parse(nonExistentFilePath);
        File file = new File(nonExistentFilePath);
        assertEquals(new ChangePathCommand(file).getClass(), command.getClass());
    }

}
