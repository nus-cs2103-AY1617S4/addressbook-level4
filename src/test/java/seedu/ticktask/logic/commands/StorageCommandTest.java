package seedu.ticktask.logic.commands;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.testutil.TypicalTasks;

//@@author A0138471A
public class StorageCommandTest {
        
    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    
    @Rule
    public TemporaryFolder folder= new TemporaryFolder();

    @Test
    public void testExecute_throwIO() throws CommandException {
        File tempFolder;
        try {
            tempFolder = folder.newFolder("//r@nD0m");
            StorageCommand command = new StorageCommand(tempFolder);
            command.setData(model, new CommandHistory());
            command.execute();
            
        } catch (IOException e) {
            return;
        }
        
        fail();
    }
    
}
//@@author 