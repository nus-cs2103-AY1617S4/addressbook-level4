package seedu.ticktask.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.ticktask.model.task.TaskType;

public class EmailTest {

    @Test
    public void isValidEmail() {
        // blank email
        assertFalse(TaskType.isValidTaskType("")); // empty string
        assertFalse(TaskType.isValidTaskType(" ")); // spaces only

        // missing parts
        assertFalse(TaskType.isValidTaskType("@example.com")); // missing local part
        assertFalse(TaskType.isValidTaskType("peterjackexample.com")); // missing '@' symbol
        assertFalse(TaskType.isValidTaskType("peterjack@")); // missing domain name

        // invalid parts
        assertFalse(TaskType.isValidTaskType("-@example.com")); // invalid local part
        assertFalse(TaskType.isValidTaskType("peterjack@-")); // invalid domain name
        assertFalse(TaskType.isValidTaskType("peter jack@example.com")); // spaces in local part
        assertFalse(TaskType.isValidTaskType("peterjack@exam ple.com")); // spaces in domain name
        assertFalse(TaskType.isValidTaskType("peterjack@@example.com")); // double '@' symbol
        assertFalse(TaskType.isValidTaskType("peter@jack@example.com")); // '@' symbol in local part
        assertFalse(TaskType.isValidTaskType("peterjack@example@com")); // '@' symbol in domain name

        // valid email
        assertTrue(TaskType.isValidTaskType("PeterJack_1190@example.com"));
        assertTrue(TaskType.isValidTaskType("a@b"));  // minimal
        assertTrue(TaskType.isValidTaskType("test@localhost"));   // alphabets only
        assertTrue(TaskType.isValidTaskType("123@145"));  // numeric local part and domain name
        assertTrue(TaskType.isValidTaskType("a1@example1.com"));  // mixture of alphanumeric and dot characters
        assertTrue(TaskType.isValidTaskType("_user_@_e_x_a_m_p_l_e_.com_"));    // underscores
        assertTrue(TaskType.isValidTaskType("peter_jack@very_very_very_long_example.com"));   // long domain name
        assertTrue(TaskType.isValidTaskType("if.you.dream.it_you.can.do.it@example.com"));    // long local part
    }
}
