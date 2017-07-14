package seedu.whatsnext.testutil;

import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.model.task.BasicTask;

/**
 * A utility class for Person.
 */
public class BasicTaskUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(BasicTask task) {
        return AddCommand.COMMAND_WORD + " " + getTaskDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    private static String getTaskDetails(BasicTask task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().fullTaskName + " ");
        sb.append(PREFIX_DATE + task.getPhone().value + " ");
        sb.append(PREFIX_TIME + task.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + task.getAddress().value + " ");
        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
