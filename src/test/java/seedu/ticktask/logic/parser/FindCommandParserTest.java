package seedu.ticktask.logic.parser;

import org.junit.Test;

import seedu.ticktask.logic.CommandHistory;
import seedu.ticktask.logic.commands.FindCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.testutil.TypicalTasks;

import static org.junit.Assert.*;

import java.util.TreeSet;

//@@author A0147928N
public class FindCommandParserTest {
    final int TASK_INDEX = 1;
    
    private Model model = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    private Model modelCopy = new ModelManager(new TypicalTasks().getTypicalTickTask(), new UserPrefs());
    
    private FindCommandParser findCommandParser = new FindCommandParser();
    
    @Test
    public void testValidParse() throws ParseException {
        String validFindKeywords = "active wash";
        TreeSet<String> keywordsSet = new TreeSet<String>();
        keywordsSet.add(validFindKeywords);
        
        FindCommand commandReturned = (FindCommand) findCommandParser.parse(validFindKeywords);        
        FindCommand commandExpected = new FindCommand(keywordsSet);
                
        commandReturned.setData(model, new CommandHistory());
        commandReturned.execute();
        
        assertFalse(model.equals(modelCopy));
        
        commandExpected.setData(modelCopy, new CommandHistory());
        commandExpected.execute();
        
        assertEquals(model, modelCopy);
    }
    
    @Test
    public void testInvalidParse() throws ParseException {
        String invalidFindKeywords = "active wish";
        TreeSet<String> keywordsSet = new TreeSet<String>();
        keywordsSet.add(invalidFindKeywords);
        
        findCommandParser.parse(invalidFindKeywords);
        
        FindCommand commandReturned = (FindCommand) findCommandParser.parse(invalidFindKeywords);        
        commandReturned.setData(model, new CommandHistory());
        commandReturned.execute();
        
        Model emptyModel = new ModelManager();
                
        assertEquals(model.getFilteredTaskList(), emptyModel.getFilteredTaskList());
        assertEquals(model.getFilteredCompletedTaskList(), emptyModel.getFilteredCompletedTaskList());
    }
}
//@@author