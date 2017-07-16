package seedu.ticktask.logic.commands;

import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.commands.exceptions.WarningException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.EventClashException;
import seedu.ticktask.model.task.exceptions.PastTaskException;

import static java.util.Objects.requireNonNull;

/**
 * Adds a Task to the TickTask.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the TickTask. "
            + "Parameters: add [TASKNAME] by [DUE DATE] at [DUE TIME] #[TAG1 TAG2 TAG3]\n"
            + "Examples: \" " + COMMAND_WORD + " Submit final report by 08/23/17 at 2359 #CAP5  \" "
            + "or \" " + COMMAND_WORD + " Upload presentation slides by 24 August at 11pm #CAP5 \"";


    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the TickTask program";
    public static final String MESSAGE_PAST_TASK = "This task is already passed the current date/time";
    public static final String MESSAGE_EVENT_CLASH = "There is another task going on within the same time frame.";


    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyTask}
     */
    public AddCommand(ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult execute() throws CommandException, WarningException {
        requireNonNull(model);
        try {
                model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        } catch (PastTaskException e) {
            throw new CommandException(MESSAGE_PAST_TASK);
        } catch (EventClashException e) {
            throw new CommandException(MESSAGE_EVENT_CLASH);
        }

    }

}
