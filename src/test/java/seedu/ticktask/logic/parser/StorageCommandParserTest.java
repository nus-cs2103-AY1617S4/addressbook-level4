package seedu.ticktask.logic.parser;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.StorageCommand;
import seedu.ticktask.logic.parser.StorageCommandParser;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.testutil.TypicalTasks;

//@@author A0138471A
public class StorageCommandParserTest {

    final int TASK_INDEX = 1;
    private StorageCommandParser storageCommandParser = new StorageCommandParser();
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();
    
    
    @Test
    public void storageCommand_valid() throws IOException, ParseException{
        
        final File tempFolder = folder.newFolder("newLocation");
        storageCommandParser.parse(tempFolder.getAbsolutePath());
        
        assertFalse(new File(tempFolder.getAbsolutePath() + "ticktask.xml").exists());
    }
    
    @Test
    public void storageCommand_throwsInvalidCommandFormatException () {
        String invalidStorageCommand = "save";
        try {
            storageCommandParser.parse(invalidStorageCommand);
        }
        catch (ParseException e) {
            return; 
        }
        
        fail();
    }
    
    @Test
    public void storageCommand_throwsInvalidLocationException () {
        String command_word = "save ";
        String invalid_location = "somelocation";
        try {
            storageCommandParser.parse(invalid_location);
        }
        catch (ParseException e) {
            return; 
        }
        fail();
        
    }
    
    @Test
    public void storageCommand_throwsInvalidLocationEmptyException () {
 
        String invalid_location = "";
        try {
            storageCommandParser.parse(invalid_location);
        }
        catch (ParseException e) {
            return; 
        }
        fail();
    }

}
//@@author
