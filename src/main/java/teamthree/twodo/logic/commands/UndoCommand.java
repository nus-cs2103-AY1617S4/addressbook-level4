//@@author A0162253M
package teamthree.twodo.logic.commands;

import teamthree.twodo.logic.commands.exceptions.CommandException;
import teamthree.twodo.logic.parser.exceptions.ParseException;
import teamthree.twodo.model.ReadOnlyTaskBook;
import teamthree.twodo.model.task.ReadOnlyTask;
import teamthree.twodo.model.task.exceptions.DuplicateTaskException;
import teamthree.twodo.model.task.exceptions.TaskNotFoundException;

/**
 * Undo the previous command by the user
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String COMMAND_WORD_UNIXSTYLE = "-u";
    public static final String MESSAGE_SUCCESS = "Successfully undo command!!!\n";
    public static final String MESSAGE_NO_HISTORY = "Failed to undo: You have not yet entered any commands.";
    public static final String MESSAGE_INVALID_PREVIOUS_COMMAND = "Failed to undo: Invalid previous command ";

    private static String fullMessage;

    @Override
    public CommandResult execute() throws CommandException {
        if (history.getUserInputHistory().isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        CommandResult undoResult = null;
        try {
            undoResult = processUserInput();
        } catch (DuplicateTaskException e) {
            throw new CommandException(AddCommand.MESSAGE_DUPLICATE_TASK);
        } catch (TaskNotFoundException e) {
            assert false : "The target task cannot be missing";
        } catch (ParseException e) {
            assert false : "The Command is invalid";
        }
        return undoResult;
    }

    private CommandResult processUserInput() throws TaskNotFoundException, DuplicateTaskException, ParseException {
        final String previousCommandWord = history.getUserInputHistory().pop();
        undoHistory.addToUserInputHistory(previousCommandWord);
        switch (previousCommandWord) {

        case AddCommand.COMMAND_WORD:
        case AddCommand.COMMAND_WORD_QUICK:
        case AddCommand.COMMAND_WORD_UNIXSTYLE:
            ReadOnlyTask taskToDelete = history.getAddHistory().pop();
            undoHistory.addToDeleteHistory(taskToDelete);
            model.deleteTask(taskToDelete);
            fullMessage = MESSAGE_SUCCESS.concat(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS);
            return new CommandResult(String.format(fullMessage, taskToDelete));

        case EditCommand.COMMAND_WORD:
            ReadOnlyTask edittedTask = history.getBeforeEditHistory().pop();
            ReadOnlyTask originalTask = history.getAfterEditHistory().pop();
            undoHistory.addToBeforeEditHistory(edittedTask);
            undoHistory.addToAfterEditHistory(originalTask);
            model.updateTask(originalTask, edittedTask);
            fullMessage = MESSAGE_SUCCESS.concat(EditCommand.MESSAGE_EDIT_TASK_SUCCESS);
            return new CommandResult(String.format(fullMessage, edittedTask));

        case DeleteCommand.COMMAND_WORD_QUICK:
        case DeleteCommand.COMMAND_WORD_UNIXSTYLE:
        case DeleteCommand.COMMAND_WORD_SHORT:
        case DeleteCommand.COMMAND_WORD:
            ReadOnlyTask taskToAdd = history.getDeleteHistory().pop();
            undoHistory.addToAddHistory(taskToAdd);
            model.addTask(taskToAdd);
            fullMessage = MESSAGE_SUCCESS.concat(AddCommand.MESSAGE_SUCCESS);
            return new CommandResult(String.format(fullMessage, taskToAdd));

        case ClearCommand.COMMAND_WORD:
            ReadOnlyTaskBook taskBook = history.getClearHistory().pop();
            model.resetData(taskBook);
            fullMessage = MESSAGE_SUCCESS.concat("Restored TaskBook");
            return new CommandResult(fullMessage);

        case MarkCommand.COMMAND_WORD:
        case MarkCommand.COMMAND_WORD_UNIXSTYLE:
            ReadOnlyTask taskToUnmark = history.getMarkHistory().pop();
            undoHistory.addToUnmarkHistory(taskToUnmark);
            model.unmarkTask(taskToUnmark);
            fullMessage = MESSAGE_SUCCESS.concat(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS);
            return new CommandResult(String.format(fullMessage, taskToUnmark));

        case UnmarkCommand.COMMAND_WORD:
        case UnmarkCommand.COMMAND_WORD_UNIXSTYLE:
            ReadOnlyTask taskToMark = history.getUnmarkHistory().pop();
            undoHistory.addToMarkHistory(taskToMark);
            model.markTask(taskToMark);
            fullMessage = MESSAGE_SUCCESS.concat(UnmarkCommand.MESSAGE_UNMARK_TASK_SUCCESS);
            return new CommandResult(String.format(fullMessage, taskToMark));

        default:
            String message = MESSAGE_INVALID_PREVIOUS_COMMAND.concat(history.getUserInputHistory().peek());
            return new CommandResult(message + previousCommandWord);
        }
    }
}
