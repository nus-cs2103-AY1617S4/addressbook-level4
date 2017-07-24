package seedu.ticktask.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.ticktask.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.ticktask.commons.core.EventsCenter;
import seedu.ticktask.commons.events.ui.ShowHelpRequestEvent;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.commands.HelpCommand;

public class HelpCommandTest {
    @Before
    public void setUp() {
        EventsCenter.getInstance().registerHandler(this);
    }
    

    @Test
    public void execute_help_success() {
        CommandResult result = new HelpCommand().execute();
        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
    }

}
