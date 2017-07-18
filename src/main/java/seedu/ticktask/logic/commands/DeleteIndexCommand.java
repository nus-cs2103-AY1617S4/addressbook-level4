package seedu.ticktask.logic.commands;

import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;

import seedu.ticktask.commons.core.Messages;
import seedu.ticktask.commons.core.UnmodifiableObservableList;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.parser.Prefix;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;

 //@@author A0131884B
 /*
  * Deletes an task identified using the type of task followed by task index.
  */
  public class DeleteIndexCommand extends DeleteCommand {
       private Index targetIndex;
       private Prefix listIndicatorPrefix;

       public DeleteIndexCommand(Index targetIndex, Prefix listIndicatorPrefix) {
            this.targetIndex = targetIndex;
            this.listIndicatorPrefix = listIndicatorPrefix;
       }

       @Override
       public CommandResult execute() throws CommandException , DuplicateTaskException {
            UnmodifiableObservableList<ReadOnlyTask> listToDeleteFrom;
            assert listIndicatorPrefix != null;
            if (listIndicatorPrefix.equals(PREFIX_ACTIVE)) {
                  listToDeleteFrom = model.getFilteredTaskList();
            } else {
                  assert listIndicatorPrefix.equals(PREFIX_COMPLETE);
                  listToDeleteFrom = model.getFilteredCompletedTaskList();
            }
            
            if (targetIndex.getZeroBased() >= listToDeleteFrom.size()) {
                  throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
            }

            taskToDelete = listToDeleteFrom.get(targetIndex.getZeroBased());
                  try {
                         if (listIndicatorPrefix.equals(PREFIX_COMPLETE))
                         model.deleteIndexCompleteTask(taskToDelete);
                         else
                         model.deleteIndexActiveTask(taskToDelete);
                  } catch (TaskNotFoundException enfe) {
                         assert false : "The target entry cannot be missing";
                  }

            return new CommandResult(String.format(MESSAGE_SUCCESS, taskToDelete));
       }

  }
 