package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.task.BaseTask;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: "
            + PREFIX_NAME + "Submit assignment "
            + "[" + PREFIX_TAG + "homework" + "]";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String MESSAGE_TIME_CLASH = "This task cannot be added as time clashes with another task";

    private final BasicTask toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(BaseTask task) {
//        if(task.getType().equals("floating"))
//            toAdd = new Floating(task);
//        else if (task.getType().equals("deadline"))
//            toAdd = new Deadline(task);
//        else if (task.getType().equals("event"))
//            toAdd = new Event(task);
        toAdd = new BasicTask(task);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
