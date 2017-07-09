package seedu.whatsnext.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.whatsnext.commons.exceptions.DataConversionException;
import seedu.whatsnext.model.ReadOnlyTaskManager;

/**
 * Represents a storage for {@link seedu.whatsnext.model.TaskManager}.
 */
public interface TaskManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskManagerFilePath();

    /**
     * Changes file path of system
     */
    void changeTaskManagerFilePath(String filePath);

    /**
     * Returns TaskManager data as a {@link ReadOnlyTaskManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException;

    /**
     * @see #getTaskManagerFilePath()
     */
    Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskManager} to the storage.
     * @param TaskManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskManager(ReadOnlyTaskManager TaskManager) throws IOException;

    /**
     * @see #saveTaskManager(ReadOnlyTaskManager)
     */
    void saveTaskManager(ReadOnlyTaskManager TaskManager, String filePath) throws IOException;


}
