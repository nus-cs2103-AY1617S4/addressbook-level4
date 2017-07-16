package seedu.ticktask.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.ticktask.commons.core.LogsCenter;
import seedu.ticktask.commons.exceptions.DataConversionException;
import seedu.ticktask.commons.util.FileUtil;
import seedu.ticktask.model.ReadOnlyTickTask;

/**
 * A class to access TickTask program data stored as an xml file on the hard disk.
 */
public class XmlTickTaskStorage implements TickTaskStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlTickTaskStorage.class);

    private static String filePath;

    public XmlTickTaskStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getTickTaskFilePath() {
        return filePath;
    }
    
    //@@author A0138471A
    public static String newTickTaskFilePath(){
    	return filePath;
    }
    
    public static void setTickTaskFilePath(String newFilePath){
    	filePath = newFilePath;
    }
    //@@author
    @Override
    public Optional<ReadOnlyTickTask> readTickTask() throws DataConversionException, IOException {
        return readTickTask(filePath);
    }

    /**
     * Similar to {@link #readTickTask()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTickTask> readTickTask(String filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        File tickTaskFile = new File(filePath);

        if (!tickTaskFile.exists()) {
            logger.info("TickTask file "  + tickTaskFile + " not found");
            return Optional.empty();
        }

        ReadOnlyTickTask tickTaskOptional = XmlFileStorage.loadDataFromSaveFile(new File(filePath));

        return Optional.of(tickTaskOptional);
    }

    @Override
    public void saveTickTask(ReadOnlyTickTask ticktask) throws IOException {
        saveTickTask(ticktask, filePath);
    }

    /**
     * Similar to {@link #saveTickTask(ReadOnlyTickTask)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveTickTask(ReadOnlyTickTask ticktask, String filePath) throws IOException {
        requireNonNull(ticktask);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableTickTask(ticktask));
    }

}
