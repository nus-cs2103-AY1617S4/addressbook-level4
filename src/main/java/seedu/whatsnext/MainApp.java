package seedu.whatsnext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import seedu.whatsnext.commons.core.Config;
import seedu.whatsnext.commons.core.Config.RepeatTaskManagerFilePathException;
import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.core.Version;
import seedu.whatsnext.commons.events.ui.ExitAppRequestEvent;
import seedu.whatsnext.commons.exceptions.DataConversionException;
import seedu.whatsnext.commons.util.ConfigUtil;
import seedu.whatsnext.commons.util.StringUtil;
import seedu.whatsnext.logic.Logic;
import seedu.whatsnext.logic.LogicManager;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.util.SampleDataUtil;
import seedu.whatsnext.storage.JsonUserPrefsStorage;
import seedu.whatsnext.storage.Storage;
import seedu.whatsnext.storage.StorageManager;
import seedu.whatsnext.storage.TaskManagerStorage;
import seedu.whatsnext.storage.UserPrefsStorage;
import seedu.whatsnext.storage.XmlTaskManagerStorage;
import seedu.whatsnext.ui.Ui;
import seedu.whatsnext.ui.UiManager;

/**
 * The main entry point to the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(1, 0, 0, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static String path;

    protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

    //@@author A0149894H
    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing WhatsNext ]===========================");
        super.init();

        config = initConfig(getApplicationParameter("config"));

        initFilePath(path);


        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        TaskManagerStorage taskManagerStorage = new XmlTaskManagerStorage(Config.getTaskManagerFilePath());
        storage = new StorageManager(taskManagerStorage, userPrefsStorage);

        initLogging(config);

        model = initModelManager(storage, userPrefs);

        logic = new LogicManager(model);

        ui = new UiManager(logic, config, userPrefs);

        initEventsCenter();
    }

    //@@author A0149894H
    public void initFilePath(String path) throws IOException {
        InputStream resourceStream = this.getClass().getResourceAsStream("/filepath/filepath");
        InputStreamReader streamReader = new InputStreamReader(resourceStream);

        BufferedReader in = new BufferedReader(streamReader);
        String filepath = in.readLine();

        try {
            config.setTaskManagerFilePath(filepath);
        } catch (RepeatTaskManagerFilePathException e) {
            e.printStackTrace();
        }
    }

    private String getApplicationParameter(String parameterName) {
        Map<String, String> applicationParameters = getParameters().getNamed();
        return applicationParameters.get(parameterName);
    }

    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyTaskManager> taskManagerOptional;
        ReadOnlyTaskManager initialData;
        try {
            taskManagerOptional = storage.readTaskManager();
            if (!taskManagerOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample TaskManager");
            }
            initialData = taskManagerOptional.orElseGet(SampleDataUtil::getSampleTaskManager);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty TaskManager");
            initialData = new TaskManager();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initialData = new TaskManager();
        }

        return new ModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    protected Config initConfig(String configFilePath) {
        Config initializedConfig;
        String configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataConversionException e) {
            logger.warning("Config file at " + configFilePathUsed + " is not in the correct format. "
                    + "Using default config properties");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        String prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initializedPrefs;
        try {
            Optional<UserPrefs> prefsOptional = storage.readUserPrefs();
            initializedPrefs = prefsOptional.orElse(new UserPrefs());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                    + "Using default user prefs");
            initializedPrefs = new UserPrefs();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty TaskManager");
            initializedPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    private void initEventsCenter() {
        EventsCenter.getInstance().registerHandler(this);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting TaskManager " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping WhatsNext ] =============================");
        ui.stop();
        try {
            storage.saveUserPrefs(userPrefs);
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleExitAppRequestEvent(ExitAppRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
