package seedu.ticktask.logic.commands;

import java.util.Set;

//@@author A0147928N
/**
 * Performs power search on completed tasks within TickTask.
 * Keyword matching is not case sensitive.
 */
public class FindCompleteCommand extends Command {

    public static final String COMMAND_WORD = "find complete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Performs power search on completed tasks. Ignores case."
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " tutorial";

    private final Set<String> keywords;

    public FindCompleteCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredCompletedTaskList(keywords);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredCompletedTaskList().size()));
    }

}
