package seedu.whatsnext.logic.commands;

import java.util.logging.Logger;

import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.events.ui.ExitAppRequestEvent;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting WhatsNext as requested ...";

    private static final Logger logger = LogsCenter.getLogger(ExitCommand.class);

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ExitAppRequestEvent());
        logger.info(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
