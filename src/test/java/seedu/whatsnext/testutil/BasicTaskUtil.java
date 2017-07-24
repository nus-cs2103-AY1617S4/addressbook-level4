package seedu.whatsnext.testutil;

import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG_CLI;

import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.model.task.BasicTask;

/**
 * A utility class for Task.
 */
public class BasicTaskUtil {

    /**
     * Returns an add command string for adding the {@code task}.
     */
    public static String getAddCommand(BasicTask task) {
        return AddCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code task}'s details.
     */
    private static String getTaskDetails(BasicTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getName().fullTaskName + " ");
        sb.append(PREFIX_MESSAGE + task.getDescription().toString() + " ");
        sb.append(PREFIX_START_DATETIME + task.getStartDateTime().toString() + " ");
        sb.append(PREFIX_END_DATETIME + task.getEndDateTime().toString() + " ");
        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG_CLI + s.tagName + " ")
        );
        return sb.toString();
    }
}
