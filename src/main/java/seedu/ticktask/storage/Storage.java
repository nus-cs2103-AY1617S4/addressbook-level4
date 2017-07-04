package seedu.ticktask.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.ticktask.commons.events.model.TickTaskChangedEvent;
import seedu.ticktask.commons.events.storage.DataSavingExceptionEvent;
import seedu.ticktask.commons.exceptions.DataConversionException;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TickTaskStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getTickTaskFilePath();

    @Override
    Optional<ReadOnlyTickTask> readTickTask() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyTickTask addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(TickTaskChangedEvent abce);
}
