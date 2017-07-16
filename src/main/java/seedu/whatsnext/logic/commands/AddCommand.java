package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG_CLI;

import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager.\n"
            + "Parameters: "
            + "TASKNAME "
            + "[" + PREFIX_MESSAGE + "DESCRIPTIONS] "
            + "[" + PREFIX_START_DATETIME + "START_DATETIME] "
            + "[" + PREFIX_END_DATETIME + "END_DATETIME] "
            + "[" + PREFIX_TAG_CLI + "TAG] \n"
            + "Example: add Task1 m/this is an example s/today e/tomorrow 10pm t/tag ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String INVALID_TASK_CREATED = "Invalid Task Format";
    public static final String MESSAGE_OVERLAP_TASK = "This Task causes an overlapping Event Task.";
    private BasicTask toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(BasicTaskFeatures task) {
        toAdd = new BasicTask(task);
    }

    //@@author A0156106M
    @Override
    public CommandResult execute() throws CommandException, IllegalValueException {
        requireNonNull(model);
        UnmodifiableObservableList<BasicTaskFeatures> taskList = model.getFilteredTaskList();
        if (toAdd.isOverlapTask(taskList)) {
            toAdd = EditCommand.createOverlapTask(toAdd);
        }
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }
}
