package seedu.ticktask.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.ticktask.commons.exceptions.DataConversionException;
import seedu.ticktask.commons.util.XmlUtil;

/**
 * Stores TickTask program data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given TickTask program data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTickTask tickTask)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, tickTask);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns a TickTask program in the file or an empty TickTask program
     */
    public static XmlSerializableTickTask loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTickTask.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
