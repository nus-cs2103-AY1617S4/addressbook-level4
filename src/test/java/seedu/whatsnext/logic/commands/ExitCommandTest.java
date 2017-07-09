//package seedu.whatsnext.logic.commands;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.whatsnext.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.google.common.eventbus.Subscribe;
//
//import seedu.whatsnext.commons.core.EventsCenter;
//import seedu.whatsnext.commons.events.ui.ExitAppRequestEvent;
//
//public class ExitCommandTest {
//    private boolean isEventCaught = false;
//
//    @Subscribe
//    private void handleExitAppRequestEvent(ExitAppRequestEvent eare) {
//        isEventCaught = true;
//    }
//
//    @Before
//    public void setUp() {
//        EventsCenter.getInstance().registerHandler(this);
//    }
//
//    @Test
//    public void execute_exit_success() {
//        CommandResult result = new ExitCommand().execute();
//        assertEquals(MESSAGE_EXIT_ACKNOWLEDGEMENT, result.feedbackToUser);
//        assertTrue(isEventCaught);
//    }
//}
