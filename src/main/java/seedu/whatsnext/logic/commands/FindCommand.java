package seedu.whatsnext.logic.commands;

import java.util.Set;
import java.util.logging.Logger;

import seedu.whatsnext.commons.core.LogsCenter;

/**
 * Finds and lists all tasks in TaskManager whose name or tags contain any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all task names or tags which contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " lunch tutorial school";

    private static final Logger logger = LogsCenter.getLogger(FindCommand.class);

    private final Set<String> keywords;

    public FindCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(keywords);
        logger.info(COMMAND_WORD + ": " + keywords.toString() + " "
                + getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

}
