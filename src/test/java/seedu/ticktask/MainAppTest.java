package seedu.ticktask;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.Optional;
import java.util.logging.Level;

import org.junit.Test;


import seedu.ticktask.commons.core.Config;
import seedu.ticktask.commons.core.LogsCenter;
import seedu.ticktask.commons.exceptions.DataConversionException;
import seedu.ticktask.commons.util.JsonUtil;
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
        assertEquals(config.getAppTitle(), TestApp.APP_TITLE);      
        assertEquals(config.getUserPrefsFilePath(), TestApp.DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        
    }
    
    @Test 
    public void testinitConfigFromSpecifiedFilePathSuccess() throws Exception {
        //String 
        TestAppStub mainAppStub = new TestAppStub();
        
        Config config = mainAppStub.initConfig(getTypicalConfigFilePathUsed());
        config.setAppTitle("Typical App Title");
        System.out.println(config.toString());
        assertEquals(config, getTypicalConfig());
        
    }
    
    @Test 
    public void testinitConfigFromWrongPathFail_WrongFileFormat() throws Exception {
        TestAppStub mainAppStub = new TestAppStub();
        String wrongConfigFilePathUsed = "wrongpath";
        try{
            Config config = mainAppStub.initConfig(wrongConfigFilePathUsed);
            config.setAppTitle("Typical App Title");
            assertEquals(config.toString(), getTypicalConfig().toString());
        }catch(DataConversionException dce){
            
        }
    }
    
    /*
    @Test 
    public void testinitSuccess() throws Exception {
        TestAppStub testAppStub = new TestAppStub();
        testAppStub.init();
        
    }
    */
    
    @Test
    public void testinitPrefsSuccess() throws DataConversionException, IOException{
        MainApp mainAppStub = new MainApp();
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTypicalStorageFilePathUsed());
        mainAppStub.initPrefs(userPrefsStorage);
        //System.out.println(mainAppStub.userPrefs.toString());
        //assertEquals(mainAppStub.userPrefs, JsonUtil.readJsonFile(getTypicalStorageFilePathUsed(), null));
    }
    
    
    /*
    @Test
    public void initLoggingSuccess() {
        Config config = new Config();
        MainApp appStub = new MainApp();
        appStub.initLogging(config);
        
    }
    */
    /*
    @Test
    public void testinitSuccess() throws Exception{
        MainApp mainAppStub = new MainApp();
        mainAppStub.init();
        //System.out.println(mainAppStub.userPrefs.toString());
        //assertEquals(mainAppStub.userPrefs, JsonUtil.readJsonFile(getTypicalStorageFilePathUsed(), null));
    }
    */
    

    /*
     * TestApp stub
     */
    public class TestAppStub extends TestApp {
        public TestAppStub(){
            super();
        }
    }
    /*
     * 
     */
    
    private Config getDefaultlConfig() throws Exception {
        Config config = new Config();
        config.setAppTitle("Typical App Title");
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath("./src/test/data/sandbox/pref_testing.json");
        return config;
    }
    
    
    private Config getTypicalConfig() throws Exception {
        Config config = new Config();
        config.setAppTitle("Typical App Title");
        config.setLogLevel(Level.INFO);
        config.setUserPrefsFilePath("./src/test/data/sandbox/pref_testing.json");
        return config;
    }
    
    private String getTypicalConfigFilePathUsed(){
        return new String( "./src/test/data/ConfigUtilTest/TypicalConfig.json");
    }
    
    private String getTypicalStorageFilePathUsed(){
        return new String( "/src/test/data/JsonUserPrefsStorageTest/pref_testing.json");
    }

}
//@@author
