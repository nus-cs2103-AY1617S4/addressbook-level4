package seedu.ticktask.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.ticktask.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.CompleteCommand;
import seedu.ticktask.logic.commands.EditCommand;
import seedu.ticktask.logic.commands.HistoryCommand;
import seedu.ticktask.logic.commands.RedoCommand;
import seedu.ticktask.logic.commands.StorageCommand;
import seedu.ticktask.logic.commands.UndoCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

public class ParserTest {
    private final Parser parser = new Parser();

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
    
    //@@author A0147928N
    @Test
    public void parseCommand_complete() throws IllegalValueException {
        assertTrue(parser.parseCommand(CompleteCommand.COMMAND_WORD + " 1") instanceof CompleteCommand);
        
        try {
            parser.parseCommand("complete");
        } catch (IllegalValueException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void parseCommand_edit() throws IllegalValueException {
        assertTrue(parser.parseCommand(EditCommand.COMMAND_WORD + " 1 name dog") instanceof EditCommand);
        
        try {
            parser.parseCommand("edit 1");
        } catch (IllegalValueException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void parseCommand_storage() throws IllegalValueException {        
        try {
            parser.parseCommand("save");
        } catch (IllegalValueException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void parseCommand_redo() throws IllegalValueException {         
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);

        try {
            parser.parseCommand("redo1");
        } catch (IllegalValueException e) {
            return;
        }
        fail();
    }
    
    @Test
    public void parseCommand_undo() throws IllegalValueException {         
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);

        try {
            parser.parseCommand("undo1");
        } catch (IllegalValueException e) {
            return;
        }
        fail();
    }
}