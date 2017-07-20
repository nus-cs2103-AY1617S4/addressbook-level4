package guitests;

import org.junit.Test;

import seedu.whatsnext.commons.core.Messages;

public class ChangePathCommandTest extends TaskManagerGuiTest {

    public static final String INVALID_SAVE_LOCATION = "Invalid input for save location";

    @Test
    public void changePathLocationInvalidCommand() {
        commandBox.pressEnter();
        commandBox.runCommand("CHANGEpath");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

}
