package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Time.isValidTime("")); // empty string
        assertFalse(Time.isValidTime(" ")); // spaces only
        assertFalse(Time.isValidTime("91")); // less than 3 numbers
        assertFalse(Time.isValidTime("phone")); // non-numeric
        assertFalse(Time.isValidTime("9011p041")); // alphabets within digits
        assertFalse(Time.isValidTime("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Time.isValidTime("911")); // exactly 3 numbers
        assertTrue(Time.isValidTime("93121534"));
        assertTrue(Time.isValidTime("124293842033123")); // long phone numbers
    }
}
