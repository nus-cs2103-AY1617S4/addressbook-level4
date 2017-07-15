package seedu.ticktask.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

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
    

    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_VIEW_ALL_TASKS = "List All Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_EVENT_TASKS = "List all Event Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_DEADLINE_TASKS = "List all Deadline Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_FLOATING_TASKS = "List all Floating Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_TODAY_TASKS = "List all Today's Tasks";
    
    private final String list_command_type;

    private static ArrayList<String> list_command_array = new ArrayList<String>(Arrays.asList(
            LIST_ALL, LIST_ALL_FULL, LIST_EVENT, LIST_EVENT, LIST_DEADLINE, LIST_FLOATING, LIST_TODAY));

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": List a type of specified tasks.\n"
            + "Example: " + COMMAND_WORD + " " + LIST_DEADLINE;
    
    public ListCommand(String list) {
        this.list_command_type = list;
    }
    
    public static boolean isValidCommand(String command) {
        return list_command_array.contains(command);
    }

    @Override
    public CommandResult execute() {
    	switch(list_command_type) {
        case LIST_ALL:
        case LIST_ALL_FULL:
            model.updateFilteredListToShowAll();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
        case LIST_EVENT:
            model.updateFilteredListToShowEvent();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_EVENT_TASKS);
        case LIST_DEADLINE:
            model.updateFilteredListToShowDeadline();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_DEADLINE_TASKS);
        case LIST_FLOATING:
            model.updateFilteredListToShowFloating();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_FLOATING_TASKS);
       case LIST_TODAY:
            model.updateFilteredListToShowToday();
            return new CommandResult(MESSAGE_SUCCESS_VIEW_TODAY_TASKS);
    	default:
	        model.updateFilteredListToShowAll();
	        return new CommandResult(MESSAGE_SUCCESS);
    	}
    }
}
