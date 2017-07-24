package seedu.whatsnext.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.exceptions.DataConversionException;
import seedu.whatsnext.commons.util.FileUtil;
import seedu.whatsnext.model.ReadOnlyTaskManager;

/**
 * A class to access TaskManager data stored as an xml file on the hard disk.
 */
public class XmlTaskManagerStorage implements TaskManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTaskManagerStorage.class);

    private static String filePath;

    public XmlTaskManagerStorage(String newFilePath) {
        XmlTaskManagerStorage.filePath = newFilePath;
    }

    //@@A0149894H
    @Override
    public String getTaskManagerFilePath() {
        return filePath;
    }

    //@@A0149894H
    public static void changeTaskManagerFilePath(String newFilePath) {
        filePath = newFilePath;
    }

    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager() throws DataConversionException, IOException {
        return readTaskManager(filePath);
    }

    /**
     * Similar to {@link #readTaskManager()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyTaskManager> readTaskManager(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File taskManagerFile = new File(filePath);

        if (!taskManagerFile.exists()) {
            logger.info("TaskManager file "  + taskManagerFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTaskManager taskManagerOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(taskManagerOptional);
    }

    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager) throws IOException {
        saveTaskManager(taskManager, filePath);
    }

    /**
     * Similar to {@link #saveTaskManager(ReadOnlyTaskManager)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveTaskManager(ReadOnlyTaskManager taskManager, String filePath) throws IOException {
        requireNonNull(taskManager);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTaskManager(taskManager));
    }

}
