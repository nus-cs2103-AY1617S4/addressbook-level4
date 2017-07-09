package seedu.whatsnext.storage;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import seedu.whatsnext.commons.exceptions.DataConversionException;
import seedu.whatsnext.commons.util.XmlUtil;

/**
 * Stores TaskManager data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given TaskManager data to the specified file.
     */
    public static void saveDataToFile(File file, XmlSerializableTaskManager taskManager)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, taskManager);
        } catch (JAXBException e) {
            assert false : "Unexpected exception " + e.getMessage();
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableTaskManager loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTaskManager.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
