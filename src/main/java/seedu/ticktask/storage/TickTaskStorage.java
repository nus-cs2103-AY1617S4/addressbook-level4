package seedu.ticktask.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.ticktask.commons.exceptions.DataConversionException;
import seedu.ticktask.model.ReadOnlyTickTask;

/**
 * Represents a storage for {@link seedu.ticktask.model.TickTask}.
 */
public interface TickTaskStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTickTaskFilePath();

    /**
     * Returns TickTask data as a {@link ReadOnlyTickTask}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTickTask> readTickTask() throws DataConversionException, IOException;

    /**
     * @see #getTickTaskFilePath()
     */
    Optional<ReadOnlyTickTask> readTickTask(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTickTask} to the storage.
     * @param tickTask cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTickTask(ReadOnlyTickTask tickTask) throws IOException;

    /**
     * @see #saveTickTask(ReadOnlyTickTask)
     */
    void saveTickTask(ReadOnlyTickTask tickTask, String filePath) throws IOException;

}