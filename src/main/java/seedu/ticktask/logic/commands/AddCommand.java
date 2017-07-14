package seedu.ticktask.logic.commands;

import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;

import java.time.LocalDate;
import java.time.LocalTime;

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

    private final Task toAdd;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyTask}
     */
    public AddCommand(ReadOnlyTask task) {
        toAdd = new Task(task);
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            if(isChornological(toAdd)){
                model.addTask(toAdd);
            }
            else{
                throw new CommandException(MESSAGE_PAST_TASK);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }
    //@@author A0139964M
    /**
     * Checks if the task added is in the past.
     * @param task
     * @return boolean
     */
    public boolean isChornological(ReadOnlyTask task) {
        LocalDate currDate = LocalDate.now();
        LocalDate taskDate = task.getDate().getLocalStartDate();
        System.out.println("localDate: " + currDate);
        System.out.println("TaskDate: " + taskDate);
        
        if(task.getDate().getLocalStartDate() == null){
            //No date either means today or no time, if no time or time is chornological just add
            if(task.getTime().getLocalStartTime() == null || isTimeChornological(task)){
                return true;
            }
        }
        //Check if task's is today.
        if(taskDate.isEqual(currDate)){ //If date is today's date, check if time is chornological
            if(task.getTime().getLocalStartTime() == null|| isTimeChornological(task)){
                return true;
            } else {
                return false;
            }
        }
        //If date exist, but it is in the future, i dont need to check the time.
        if(isDateChornological(task)){
            return true;
        } else{
            return false;
        }
    }
    
    public boolean isTimeChornological(ReadOnlyTask task) {
        LocalTime currTime = LocalTime.now();
        LocalTime taskTime = task.getTime().getLocalStartTime();
        if (taskTime.isBefore(currTime)) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean isDateChornological(ReadOnlyTask task){
        LocalDate currDate = LocalDate.now();
        LocalDate taskDate = task.getDate().getLocalStartDate();
        if(taskDate.isBefore(currDate)){
            return false;
        } else {
            return true;
        }
    }
}
    //@@author

