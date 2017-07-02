package seedu.ticktask.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.person.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_TIME + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_DATE + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TIME + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_DATE + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(ReadOnlyTask person) {
        toAdd = new Task(person);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

    }

}
