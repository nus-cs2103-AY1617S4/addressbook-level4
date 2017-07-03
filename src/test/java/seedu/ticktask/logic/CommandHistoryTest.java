package seedu.ticktask.logic;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import seedu.ticktask.logic.CommandHistory;

public class CommandHistoryTest {
    private CommandHistory history;

    @Before
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void add() {
        final String validCommand = "clear";
        final String invalidCommand = "adds Bob";

        history.add(validCommand);
        history.add(invalidCommand);
        assertEquals(Arrays.asList(validCommand, invalidCommand), history.getHistory());
    }
}
