package seedu.ticktask.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.person.ReadOnlyTask;
import seedu.ticktask.model.person.Task;
import seedu.ticktask.model.person.exceptions.DuplicateTaskException;

/**
 * Adds a person to the TickTask.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the TickTask. "
            + "Parameters: "
            + PREFIX_NAME + "TASK "
            + PREFIX_TIME + "TIME "
            + PREFIX_EMAIL + "EMAIL" 
            + PREFIX_DATE + "DATE "            
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Meeting "
            + PREFIX_TIME + "10:00 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_DATE + "July_2_2017 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the address book";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
