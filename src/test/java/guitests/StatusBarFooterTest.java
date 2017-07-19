package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.ticktask.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.ticktask.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.ticktask.logic.commands.ListCommand;
import seedu.ticktask.testutil.TaskUtil;
import seedu.ticktask.ui.StatusBarFooter;

public class StatusBarFooterTest extends TickTaskGuiTest {

    private Clock originalClock;
    private Clock injectedClock;

    @Before
    public void injectFixedClock() {
        originalClock = StatusBarFooter.getClock();
        injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        StatusBarFooter.setClock(injectedClock);
    }

    @After
    public void restoreOriginalClock() {
        StatusBarFooter.setClock(originalClock);
    }

    @Test
    public void syncStatus_initialValue() {
        assertEquals(SYNC_STATUS_INITIAL, statusBarFooter.getSyncStatus());
    }

    @Test
    public void syncStatus_nonMutatingCommandSucceeds_syncStatusRemainsUnchanged() {
        assertTrue(commandBox.runCommand(ListCommand.COMMAND_WORD)); // non-mutating command succeeds
        assertEquals(SYNC_STATUS_INITIAL, statusBarFooter.getSyncStatus());
    }

//    @Test
//    public void syncStatus_commandFails_syncStatusRemainsUnchanged() {
//        assertFalse(commandBox.runCommand("invalid command")); // invalid command fails
//        assertEquals(SYNC_STATUS_INITIAL, statusBarFooter.getSyncStatus());
//    }

}
