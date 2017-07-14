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
    public static final String LIST_TODAY = "t";
    public static final String LIST_FLOATING = "f";
    public static final String LIST_DEADLINE = "d";
    public static final String LIST_EVENT = "e";
    
    
    private static ArrayList<String> validListCommands = new ArrayList<String>(Arrays.asList(LIST_ALL, LIST_TODAY, LIST_FLOATING, LIST_DEADLINE, LIST_EVENT));
    
    
   public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": View a type of specified tasks.\n"
            + "Parameters: TYPE\n"
            + "Example: " + COMMAND_WORD + " " + LIST_DEADLINE;
    
    public static final String MESSAGE_SUCCESS_VIEW_ALL_TASKS = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_VIEW_FLOATING_TASKS = "List all Floating Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_DEADLINE_TASKS = "List all Deadline Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_EVENT_TASKS = "List all Event Tasks";
    public static final String MESSAGE_SUCCESS_VIEW_TODAY_TASKS = "List all Today's Tasks";

    

    private final String typeOfList;

    public ListCommand(String typeOfList) {
        this.typeOfList = typeOfList;
    }
    
    public static boolean isValidCommand(String command) {
        return validListCommands.contains(command);
    }

    /*@Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }*/
        
        @Override
        public CommandResult execute() {
            switch(typeOfList) {
            case LIST_ALL:
                model.updateFilteredListToShowAll();
                return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
            case LIST_FLOATING:
                model.updateFilteredListToShowFloating();
                return new CommandResult(MESSAGE_SUCCESS_VIEW_FLOATING_TASKS);
            case LIST_DEADLINE:
                model.updateFilteredListToShowDeadline();
                return new CommandResult(MESSAGE_SUCCESS_VIEW_DEADLINE_TASKS);
            case LIST_EVENT:
                model.updateFilteredListToShowEvent();
                return new CommandResult(MESSAGE_SUCCESS_VIEW_EVENT_TASKS);
            case LIST_TODAY:
                model.updateFilteredListToShowToday();
                return new CommandResult(MESSAGE_SUCCESS_VIEW_TODAY_TASKS);
            default:
                model.updateFilteredListToShowAll();
                return new CommandResult(MESSAGE_SUCCESS_VIEW_ALL_TASKS);
            }
        }
}

