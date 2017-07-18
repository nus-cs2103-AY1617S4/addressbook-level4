package seedu.ticktask;

import static org.junit.Assert.*;

import java.nio.file.NoSuchFileException;

import org.junit.Test;

import seedu.ticktask.commons.core.Config;
import seedu.ticktask.commons.exceptions.DataConversionException;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.storage.JsonUserPrefsStorage;
import seedu.ticktask.storage.UserPrefsStorage;
//@@author A0139819N
/*
 * Test the main entry point of the application 
 * Conducts tests on TestApp.Java
 */
public class MainAppTest {

    @Test
    public void testinitConfigFromDefaultFilePathSuccess() {
        TestAppStub mainAppStub = new TestAppStub();
        String configFilePath = null;
        Config config = mainAppStub.initConfig(configFilePath);
        assertEquals(config, new Config());
        //assertEquals(config.getAppTitle(), MainApp.APP_TITLE);      
        //assertEquals(config.getUserPrefsFilePath(), TestApp.DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        
    }
    
    @Test 
    public void testinitConfigFromSpecifiedFilePathSuccess() {
        TestAppStub mainAppStub = new TestAppStub();
        String configFilePathUsed = "/main/src/test/data/ConfigUtilTest";
        Config config = mainAppStub.initConfig(configFilePathUsed);
        //assertEquals(confiq,)
    }
    
    @Test (expected = NoSuchFileException.class)
    public void testinitConfigFromSpecifiedFilePathFail_WrongFileFormat() {
        TestAppStub mainAppStub = new TestAppStub();
        String configFilePathUsed = "/wrongpath/src/test/data/ConfigUtilTest";
        
        Config config = mainAppStub.initConfig(configFilePathUsed);
        assertEquals(config, new Config());
    }
    
    /*
    @Test 
    public void testinitSuccess() throws Exception {
        TestAppStub testAppStub = new TestAppStub();
        testAppStub.init();
        
    }
    */
    
    @Test
    public void testinitPrefsSuccess(){
        //UserPrefsStorage storage = new UserPrefsStorage();
        TestAppStub mainAppStub = new TestAppStub();
        Config config = mainAppStub.initConfig("");
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        mainAppStub.initPrefs(userPrefsStorage);
        assertEquals(mainAppStub.userPrefs, new UserPrefs());
    }

    
    
    
    /*
     * TestApp stub
     */
    public class TestAppStub extends TestApp {
        public TestAppStub(){
            super();
        }
    }

}
//@@author
