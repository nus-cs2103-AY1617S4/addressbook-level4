package seedu.ticktask.testutil;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;

/**
 * A utility class to help with building TickTask objects.
 * Example usage: <br>
 *     {@code TickTask ab = new TickTaskBuilder().withTask("wash dog").withTag("Friend").build();}
 */
public class TickTaskBuilder {

    private TickTask tickTask;

    public TickTaskBuilder() {
        tickTask = new TickTask();
    }

    public TickTaskBuilder(TickTask ticktask) {
        this.tickTask = ticktask;
    }

    public TickTaskBuilder withTask(Task task) throws DuplicateTaskException {
        tickTask.addTask(task);
        return this;
    }

    public TickTaskBuilder withTag(String tagName) throws IllegalValueException {
        tickTask.addTag(new Tag(tagName));
        return this;
    }

    public TickTask build() {
        return tickTask;
    }
}
