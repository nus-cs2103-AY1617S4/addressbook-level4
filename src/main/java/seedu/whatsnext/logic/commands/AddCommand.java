package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.person.Person;
import seedu.whatsnext.model.person.ReadOnlyPerson;
import seedu.whatsnext.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2013 Tutorial "
            + PREFIX_DATE + "10 July "
            + PREFIX_TIME + "10-12 "
            + PREFIX_TAG + "school"

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String MESSAGE_TIME_CLASH = "This task cannot be added as time clashes with another event";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(ReadOnlyPerson person) {
        toAdd = new Person(person);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
