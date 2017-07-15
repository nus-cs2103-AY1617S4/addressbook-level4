package seedu.whatsnext.testutil;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;


/**
 * A utility class to help with building TaskManager objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new TaskManagerBuilder().withPerson("John", "Doe").withTag("Friend").build();}
 */
public class TaskManagerBuilder {

    private TaskManager taskManger;

    public TaskManagerBuilder() {
        taskManger = new TaskManager();
    }

    public TaskManagerBuilder(TaskManager addressBook) {
        this.taskManger = addressBook;
    }

    public TaskManagerBuilder withPerson(BasicTask task) throws DuplicateTaskException {
        taskManger.addTask(task);
        return this;
    }

    public TaskManagerBuilder withTag(String tagName) throws IllegalValueException {
        taskManger.addTag(new Tag(tagName));
        return this;
    }

    public TaskManager build() {
        return taskManger;
    }
}
