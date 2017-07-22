package seedu.ticktask.logic.commands;

import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

//@@author A0131884B
/*
 * Finds tasks from given task name and deletes task if it is the only one found.
 */
public class DeleteFindCommand extends DeleteCommand {
    public static final String MESSAGE_NO_TASKS = "No tasks found! Please try again "
            + "with different keywords. \nUse 'list' command to go back";

    public static final String MESSAGE_MULTIPLE_TASKS = "More than one task found! \n"
            + "Use " + COMMAND_WORD + " [ "
            + PREFIX_COMPLETE + " ]" + " or " + COMMAND_WORD + " [ "
            + PREFIX_ACTIVE + " ]"
            + " INDEX to specify which task to delete. \nUse 'list' command to go back after finishing deletion.";

    private String keywords;

    public DeleteFindCommand(String keywords) {
        this.keywords = keywords;
    }

    
    /** Executes the delete by find command and returns the result message 
     *
     * @return feedback message of the operation result for display via a CommandResult Object.
     * @throws DuplicateTaskException if more than one task with the same name substring is found
     */
    @Override
    public CommandResult execute() throws CommandException, DuplicateTaskException {
        model.updateMatchedTaskList(keywords);
        List<ReadOnlyTask> tempList = new ArrayList<>();
        tempList.addAll(model.getFilteredActiveTaskList());
        tempList.addAll(model.getFilteredCompletedTaskList());

        if (tempList.size() == 1) {
            taskToDelete = tempList.get(0);
            try {
                model.deleteFindTask(taskToDelete);
            } catch (TaskNotFoundException e) {
                assert false : "The target task cannot be missing";
            }
            model.updateFilteredListToShowAll();
            if (keywords.equals(taskToDelete.getName().fullName)) {
                return new CommandResult(String.format(MESSAGE_SUCCESS, taskToDelete));
            } else {
                return new CommandResult(String.format(MESSAGE_WARNING, taskToDelete));
            }

        } else {
            if (tempList.size() >= 2) {
                return new CommandResult(MESSAGE_MULTIPLE_TASKS);
            } else {
                assert (tempList.size() == 0);
                return new CommandResult(MESSAGE_NO_TASKS);
            }
        }
    }
}


