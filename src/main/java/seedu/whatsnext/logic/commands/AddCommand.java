package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.events.ui.JumpToListRequestEvent;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;


/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: "
            + "Submit assignment "
            + PREFIX_START_DATETIME + "Monday 10AM "
            + PREFIX_END_DATETIME + "Friday 10AM "
            + PREFIX_TAG + " high";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";
    public static final String INVALID_TASK_CREATED = "Invalid Task Format";
    private final BasicTask toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public AddCommand(BasicTaskFeatures task) {
        toAdd = new BasicTask(task);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        UnmodifiableObservableList<BasicTaskFeatures> taskList = model.getFilteredTaskList();
        try {
            int overlapTaskIndex = BasicTask.getOverlapTaskIndex(toAdd, taskList);
            if (BasicTask.eventTaskOverlap(overlapTaskIndex)) {
                //BasicTaskFeatures taskToEdit = taskList.get(overlapTaskIndex);
                //model.updateTask(taskToEdit, EditCommand.createOverlappingTask(taskToEdit));
                model.addTask(EditCommand.createOverlappingTask(toAdd));
            } else {
                model.addTask(toAdd);
            }
            Index targetIndex = new Index(model.getFilteredTaskList().size() - 1);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }



}
