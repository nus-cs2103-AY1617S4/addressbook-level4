package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MARK;

import javafx.collections.ObservableList;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.tag.UniqueTagList.DuplicateTagException;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

/**
 * Clears the Task Manager.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Task List has been cleared!";
    public static final Object MESSAGE_USAGE = null;

    public final String clearArgument;

    public ClearCommand(String clearArgument) {
        this.clearArgument = clearArgument;
    }

    //@@author A0156106M
    @Override
    public CommandResult execute() {
        requireNonNull(model);
        if (clearArgument.equals(PREFIX_ALL.toString())){
            model.resetData(new TaskManager());
            return new CommandResult(MESSAGE_SUCCESS);
        } else if (clearArgument.equals(PREFIX_MARK.toString())){
            System.out.println("MARK FILES TO BE CLEARED");
            ReadOnlyTaskManager readOnlyTaskManager = model.getTaskManager();
            ObservableList<Tag> tagList = readOnlyTaskManager.getTagList();
            ObservableList<BasicTask> taskList = readOnlyTaskManager.getTaskList();
            TaskManager taskManager = new TaskManager();
            for (BasicTask basicTask: taskList){
                if(!basicTask.getIsCompleted()){
                    try {
                        taskManager.addTask(basicTask);
                    } catch (DuplicateTaskException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

            try {
                taskManager.setTags(tagList);
            } catch (DuplicateTagException e) {
                e.printStackTrace();
            }
            model.resetData(taskManager);
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_SUCCESS);
        }


    }
}
