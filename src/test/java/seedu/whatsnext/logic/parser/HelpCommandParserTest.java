package seedu.whatsnext.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.logic.commands.ChangePathCommand;
import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.DeleteCommand;
import seedu.whatsnext.logic.commands.EditCommand;
import seedu.whatsnext.logic.commands.FilePathCommand;
import seedu.whatsnext.logic.commands.FindCommand;
import seedu.whatsnext.logic.commands.HelpCommand;
import seedu.whatsnext.logic.commands.HistoryCommand;
import seedu.whatsnext.logic.commands.ListCommand;
import seedu.whatsnext.logic.commands.MarkCommand;
import seedu.whatsnext.logic.commands.RedoCommand;
import seedu.whatsnext.logic.commands.RemindCommand;
import seedu.whatsnext.logic.commands.SelectCommand;
import seedu.whatsnext.logic.commands.UndoCommand;
import seedu.whatsnext.logic.commands.UnmarkCommand;
import seedu.whatsnext.logic.parser.exceptions.ParseException;

public class HelpCommandParserTest {

    public static final String INVALID_INPUT = "help blah";

    private HelpCommandParser parser = new HelpCommandParser();

    @Test
    public void parse_validArgs_returnHelpCommand() throws Exception {
        HelpCommand expectedCommand = new HelpCommand();
        assertParseSuccess("", expectedCommand);

        expectedCommand = new HelpCommand(AddCommand.COMMAND_WORD);
        assertParseSuccess(AddCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(DeleteCommand.COMMAND_WORD);
        assertParseSuccess(DeleteCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(ListCommand.COMMAND_WORD);
        assertParseSuccess(ListCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(EditCommand.COMMAND_WORD);
        assertParseSuccess(EditCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(SelectCommand.COMMAND_WORD);
        assertParseSuccess(SelectCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(MarkCommand.COMMAND_WORD);
        assertParseSuccess(MarkCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(UnmarkCommand.COMMAND_WORD);
        assertParseSuccess(UnmarkCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(ClearCommand.COMMAND_WORD);
        assertParseSuccess(ClearCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(FindCommand.COMMAND_WORD);
        assertParseSuccess(FindCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(UndoCommand.COMMAND_WORD);
        assertParseSuccess(UndoCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(RedoCommand.COMMAND_WORD);
        assertParseSuccess(RedoCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(RemindCommand.COMMAND_WORD);
        assertParseSuccess(RemindCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(ChangePathCommand.COMMAND_WORD);
        assertParseSuccess(ChangePathCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(FilePathCommand.COMMAND_WORD);
        assertParseSuccess(FilePathCommand.COMMAND_WORD, expectedCommand);

        expectedCommand = new HelpCommand(HistoryCommand.COMMAND_WORD);
        assertParseSuccess(HistoryCommand.COMMAND_WORD, expectedCommand);
    }

    public void parse_invalidArgs_throwException() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE);
        assertParseFailure(INVALID_INPUT, expectedMessage);
    }

    /**
     * Asserts the parsing of {@code userInput} is successful and the result matches {@code expectedCommand}
     */
    private void assertParseSuccess(String userInput, HelpCommand expectedCommand) throws Exception {
        Command command = parser.parse(userInput);
        assert expectedCommand.equals(command);
    }

    /**
     * Asserts the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            fail("An exception should have been thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
