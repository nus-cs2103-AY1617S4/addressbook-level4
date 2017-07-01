package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Date.isValidAddress("")); // empty string
        assertFalse(Date.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Date.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Date.isValidAddress("-")); // one character
        assertTrue(Date.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
