package seedu.ticktask.testutil;

import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.model.task.Task;

/**
 * A utility class for Person.
 */
public class TaskUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Task task) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(task);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    private static String getPersonDetails(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + task.getName().fullName + " ");
        sb.append(PREFIX_TIME + task.getTime().value + " ");
        sb.append(PREFIX_EMAIL + task.getEmail().value + " ");

        sb.append(PREFIX_DATE + task.getDate().toString() + " ");

        task.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }
}
