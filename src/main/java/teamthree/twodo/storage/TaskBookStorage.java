package teamthree.twodo.storage;

import java.io.IOException;
import java.util.Optional;

import teamthree.twodo.commons.exceptions.DataConversionException;
import teamthree.twodo.model.ReadOnlyTaskBook;

/**
 * Represents a storage for {@link teamthree.twodo.model.TaskBook}.
 */
public interface TaskBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getTaskBookFilePath();

    /**
     * Returns TaskBook data as a {@link ReadOnlyTaskBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    /**
     * @see #getTaskBookFilePath()
     */
    Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaskBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaskBook(ReadOnlyTaskBook addressBook) throws IOException;

    /**
     * @see #saveTaskBook(ReadOnlyTaskBook)
     */
    void saveTaskBook(ReadOnlyTaskBook addressBook, String filePath) throws IOException;

    /**
     * Changes the filePath at which task book will be saved at
     * @param filePath cannot be invalid or null
     * @throws IOException if there is any problem in the filePath
     */
    void setTaskBookFilePath(String filePath) throws IOException;

}
