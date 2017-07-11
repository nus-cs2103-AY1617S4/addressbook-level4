package seedu.ticktask.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.ticktask.commons.core.ComponentManager;
import seedu.ticktask.commons.core.LogsCenter;
import seedu.ticktask.commons.events.model.TickTaskChangedEvent;
import seedu.ticktask.commons.events.storage.DataSavingExceptionEvent;
import seedu.ticktask.commons.exceptions.DataConversionException;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.UserPrefs;

/**
 * Manages storage of TickTask program data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TickTaskStorage tickTaskStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TickTaskStorage tickTaskStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.tickTaskStorage = tickTaskStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TickTask methods ==============================

    @Override
    public String getTickTaskFilePath() {
        return tickTaskStorage.getTickTaskFilePath();
    }

    @Override
    public Optional<ReadOnlyTickTask> readTickTask() throws DataConversionException, IOException {
        return readTickTask(tickTaskStorage.getTickTaskFilePath());
    }

    @Override
    public Optional<ReadOnlyTickTask> readTickTask(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tickTaskStorage.readTickTask(filePath);
    }

    @Override
    public void saveTickTask(ReadOnlyTickTask tickTask) throws IOException {
        saveTickTask(tickTask, tickTaskStorage.getTickTaskFilePath());
    }

    @Override
    public void saveTickTask(ReadOnlyTickTask tickTask, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tickTaskStorage.saveTickTask(tickTask, filePath);
    }


    @Override
    @Subscribe
    public void handleTickTaskChangedEvent(TickTaskChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveTickTask(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
