package seedu.whatsnext.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.whatsnext.commons.events.model.TaskManagerChangedEvent;
import seedu.whatsnext.commons.events.storage.DataSavingExceptionEvent;
import seedu.whatsnext.commons.exceptions.DataConversionException;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaskManagerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    //@@A0149894H
    @Override
    String getTaskManagerFilePath();

    //@@A0149894H
    @Override
    void changeTaskManagerFilePath(String filePath);

    @Override
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    @Override
    void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce);
}
