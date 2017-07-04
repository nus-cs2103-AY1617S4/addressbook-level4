//package seedu.whatsnext.logic.commands;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static seedu.whatsnext.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import com.google.common.eventbus.Subscribe;
//
//import seedu.whatsnext.commons.core.EventsCenter;
//import seedu.whatsnext.commons.events.ui.ShowHelpRequestEvent;
//
//public class HelpCommandTest {
//    private boolean isEventCaught = false;
//
//    @Subscribe
//    private void handleShowHelpRequestEvent(ShowHelpRequestEvent shre) {
//        isEventCaught = true;
//    }
//
//    @Before
//    public void setUp() {
//        EventsCenter.getInstance().registerHandler(this);
//    }
//
//    @Test
//    public void execute_help_success() {
//        CommandResult result = new HelpCommand().execute();
//        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
//        assertTrue(isEventCaught);
//    }
//}
