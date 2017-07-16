package seedu.ticktask.logic.commands;

import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.model.task.ReadOnlyTask;

//@@author A0131884B
  /*
   * Abstract class that represents what Command word and confirmation messages a delete command will be using.
   */
public abstract class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Delete the task identified by keywords"
            + " if it is the only task found, \nor delete the task "
            + "identified by the index number of the last"
            + " task list.\n" + "Format: " + COMMAND_WORD
            + " [task name]\n" + "Example: " + COMMAND_WORD + " wash clothes\n"
            + "or " + COMMAND_WORD + " " + PREFIX_ACTIVE + " INDEX (must be a positive integer)"
            + "or " + COMMAND_WORD + " " + PREFIX_COMPLETE + " INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_COMPLETE + " 1";
    public static final String MESSAGE_SUCCESS = "Task deleted:" + "\n"
            + Messages.MESSAGE_TASK_DESCRIPTION + "%1$s";
    protected ReadOnlyTask taskToDelete;
}
//@@author