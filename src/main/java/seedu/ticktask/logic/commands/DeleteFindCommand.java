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

        private Set<String> keywords;

        public DeleteFindCommand(Set<String> keywords) {
                this.keywords = keywords;
            }

        @Override
        public CommandResult execute() throws CommandException , DuplicateTaskException {
        /*
        * update all 2 lists with new keywords.
        */

            model.updateMatchedTaskList(keywords);
            /*
            * find out whether only 1 task is found.
            */
            List<ReadOnlyTask> tempList = new ArrayList<>();
            tempList.addAll(model.getFilteredTaskList());
            tempList.addAll(model.getFilteredCompletedTaskList());

            if (tempList.size() == 1) {
                taskToDelete = tempList.get(0);
            try {
                model.deleteFindTask(taskToDelete);
            } catch (TaskNotFoundException e) {
                assert false : "The target task cannot be missing";
            }
            model.updateFilteredListToShowAll();
            return new CommandResult(String.format(MESSAGE_SUCCESS, taskToDelete));
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

