package seedu.whatsnext.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MARK;

import java.util.Iterator;

import javafx.collections.ObservableList;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.tag.UniqueTagList.DuplicateTagException;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.UniqueTaskList;
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
            UniqueTaskList updatedTaskList = new UniqueTaskList();
            for(BasicTask basicTask : taskList) {
                System.out.println(basicTask.toString() + " " + basicTask.getIsCompleted() );
                System.out.println("HELLO");
                if (basicTask.getIsCompleted()) {
                    System.out.println("TASK " + basicTask.toString());
                    try {
                        updatedTaskList.add(basicTask);
                    } catch (DuplicateTaskException e) {
                        e.printStackTrace();
                    }
                }
            }
            Iterator<BasicTask> iterator = updatedTaskList.iterator();
            while(iterator.hasNext()){
                System.out.println("HELLO ");
                System.out.println("TASK " + iterator.toString());
                iterator = (Iterator<BasicTask>) iterator.next();
            }

            TaskManager taskManager = new TaskManager();
            try {
                taskManager.setTags(tagList);
                taskManager.setTasks(updatedTaskList.asObservableList());
            } catch (DuplicateTagException | DuplicateTaskException e) {
                e.printStackTrace();
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_SUCCESS);
        }


    }
}
