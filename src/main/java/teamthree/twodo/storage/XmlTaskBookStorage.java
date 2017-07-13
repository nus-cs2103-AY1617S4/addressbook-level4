package teamthree.twodo.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Logger;

import teamthree.twodo.commons.core.LogsCenter;
import teamthree.twodo.commons.exceptions.DataConversionException;
import teamthree.twodo.commons.util.FileUtil;
import teamthree.twodo.model.ReadOnlyTaskBook;
import teamthree.twodo.model.task.ReadOnlyTask;

/**
 * A class to access TaskBook data stored as an xml file on the hard disk.
 */
public class XmlTaskBookStorage implements TaskBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskBookStorage.class);

    private String filePath;

    public XmlTaskBookStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getTaskBookFilePath() {
        return filePath;
    }

    @Override
    public void setTaskBookFilePath(String filePath) throws IOException {
        this.filePath = filePath;
    }

    @Override
    public Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException {
        return readTaskBook(filePath);
    }

    /**
     * Similar to {@link #readTaskBook()}
     *
     * @param filePath
     *            location of the data. Cannot be null
     * @throws DataConversionException
     *             if the file is not in the correct format.
     */

    public Optional<ReadOnlyTaskBook> readTaskBook(String filePath)
            throws DataConversionException, FileNotFoundException {
        requireNonNull(filePath);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("TaskBook file " + addressBookFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskBook addressBookOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(addressBookOptional);
    }

    @Override
    public void saveTaskBook(ReadOnlyTaskBook addressBook) throws IOException {
        saveTaskBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveTaskBook(ReadOnlyTaskBook)}
     *
     * @param filePath
     *            location of the data. Cannot be null
     */
    @Override
    public void saveTaskBook(ReadOnlyTaskBook addressBook, String filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskBook(addressBook));
    }

    /**
     * The following method saves notified tasks
     *
     * @param notified
     * @param filePath
     * @throws IOException
     */
    public void saveNotifiedTasks(HashSet<ReadOnlyTask> notified, String filePath) throws IOException {
        requireNonNull(notified);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveNotificationToFile(file, XmlSerializableTaskBook.getXmlSerializableTaskList(notified));
    }

}
