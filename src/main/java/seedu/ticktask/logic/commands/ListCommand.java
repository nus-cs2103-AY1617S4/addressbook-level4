package seedu.ticktask.logic.commands;

import seedu.ticktask.model.task.ReadOnlyTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//@@author A0138471A
/**
 * Lists all tasks in the TickTask to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String LIST_ALL = "";
    public static final String LIST_ALL_FULL = "all";
    public static final String LIST_EVENT = "event";
    public static final String LIST_DEADLINE = "deadline";
    public static final String LIST_FLOATING = "floating";
    public static final String LIST_TODAY = "today";

    public static final String MESSAGE_EMPTY_TASK_LIST = "No task is found";
    public static final String MESSAGE_SUCCESS_VIEW_ALL_TASKS = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_VIEW_EVENT_TASKS = "List all Event Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_DEADLINE_TASKS = "List all Deadline Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_FLOATING_TASKS = "List all Floating Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_TODAY_TASKS = "List all Today's Tasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List a type of specified tasks.\n"
            + "Example: " + COMMAND_WORD + " " + LIST_DEADLINE;

    private static ArrayList<String> list_command_array = new ArrayList<String>(Arrays.asList(
            LIST_ALL, LIST_ALL_FULL, LIST_EVENT, LIST_EVENT, LIST_DEADLINE, LIST_FLOATING, LIST_TODAY));
    
    private String listCommandType;



    public ListCommand(String list) {
        this.listCommandType = list;
    }

    public static boolean isValidCommand(String command) {
        return list_command_array.contains(command);
    }

    @Override
    public CommandResult execute() {

        switch (listCommandType) {
            case LIST_ALL:
            case LIST_ALL_FULL:
                model.updateFilteredListToShowAll();
                //@@author
                //@@author A0131884B
                if (checkEmpty()) {
                    return new CommandResult(MESSAGE_EMPTY_TASK_LIST);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
                }
                //@@author
                //@@author A0138471A
            case LIST_EVENT:
                model.updateFilteredListToShowEvent();
                if (checkEmpty()) {
                    return new CommandResult(MESSAGE_EMPTY_TASK_LIST);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
                }
            case LIST_DEADLINE:
                model.updateFilteredListToShowDeadline();
                if (checkEmpty()) {
                    return new CommandResult(MESSAGE_EMPTY_TASK_LIST);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
                }
            case LIST_FLOATING:
                model.updateFilteredListToShowFloating();
                if (checkEmpty()) {
                    return new CommandResult(MESSAGE_EMPTY_TASK_LIST);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
                }
            case LIST_TODAY:
                model.updateFilteredListToShowToday();
                if (checkEmpty()) {
                    return new CommandResult(MESSAGE_EMPTY_TASK_LIST);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
                }
            default:
                model.updateFilteredListToShowAll();
                if (checkEmpty()) {
                    return new CommandResult(MESSAGE_EMPTY_TASK_LIST);
                } else {
                    return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
                }
        }
    }
//@@author
    //@@author A0131884B
    /**
     * return true is the target list is empty
     */
        boolean checkEmpty(){
            List<ReadOnlyTask> tempList = new ArrayList<>();
            tempList.addAll(model.getFilteredActiveTaskList());
            tempList.addAll(model.getFilteredCompletedTaskList());

            if (tempList.size() == 0) {
                return true;
            } else {
                return false;
            }
        }
    //@@author
}
