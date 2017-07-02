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
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyTickTask}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTickTask> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyTickTask> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTickTask} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyTickTask addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyTickTask)
     */
    void saveAddressBook(ReadOnlyTickTask addressBook, String filePath) throws IOException;

}
