package seedu.ticktask.model.task;

import static java.util.Objects.requireNonNull;

import seedu.ticktask.commons.exceptions.IllegalValueException;

/**
 * Represents a TickTask's task type in the program.
 * Guarantees: immutable; is valid as declared in {@link #isValidTaskType(String)}
 */
public class TaskType {

    public static final String MESSAGE_TASK_TYPE_CONSTRAINTS =
            "Task types should be one of the following: [event], [deadline] or [floating]";
    

    public String value;
    public static final String TASK_TYPE_EVENT = "event";
    public static final String TASK_TYPE_DEADLINE = "deadline";
    public static final String TASK_TYPE_FLOATING = "floating";

    /**
     * Validates given task type.
     *
     * @throws IllegalValueException if given task type string is invalid.
     */
    public TaskType(String taskType) throws IllegalValueException {
        requireNonNull(taskType);
        String trimmedType = taskType.trim();
        System.out.println(trimmedType);
        if (!trimmedType.equals("")) {
            if (!isValidTaskType(trimmedType)) {
                throw new IllegalValueException(MESSAGE_TASK_TYPE_CONSTRAINTS);
            }
        }
        
        this.value = trimmedType;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String value){
        this.value=value;
    }
    /**
     * Returns if a given string is a valid  task type.
     */
    public static boolean isValidTaskType(String test) {
        return test.equals(TASK_TYPE_EVENT) || test.equals(TASK_TYPE_DEADLINE) || test.equals(TASK_TYPE_FLOATING) ;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskType // instanceof handles nulls
                && this.value.equals(((TaskType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
