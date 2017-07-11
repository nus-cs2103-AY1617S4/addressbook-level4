package seedu.whatsnext.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskName;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedTask {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private boolean isCompleted;
    @XmlElement(required = true)
    private String startDateTime;
    @XmlElement(required = true)
    private String endDateTime;
    @XmlElement(required = true)
    private String taskType;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedTask.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTask() {}


    /**
     * Converts a given Task into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedTask
     */
    public XmlAdaptedTask(BasicTask source) {
        name = source.getName().fullTaskName;
        isCompleted = source.getIsCompleted();
        tagged = new ArrayList<>();
        if (!source.getStartDateTime().toString().equals(DateTime.INIT_DATEVALUE)) {
            startDateTime = source.getStartDateTime().toString();
        }
        if (!source.getEndDateTime().toString().equals(DateTime.INIT_DATEVALUE)) {
            endDateTime = source.getEndDateTime().toString();
        }

        taskType = source.getTaskType();
        for (Tag tag : source.getTags()) {
            tagged.add(new XmlAdaptedTag(tag));
        }
    }

    //@@author A0156106M
    /**
     * Converts this jaxb-friendly adapted person object into the model's Task object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public BasicTask toModelType() throws IllegalValueException {
        final List<Tag> taskTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            taskTags.add(tag.toModelType());
        }
        final TaskName name = new TaskName(this.name);
        final boolean isCompleted = this.isCompleted;
        final Set<Tag> tags = new HashSet<>(taskTags);
        final DateTime startDateTime;
        final DateTime endDateTime;
        // Event Task
        if(this.startDateTime != null && this.endDateTime != null){
            startDateTime = new DateTime(this.startDateTime);
            endDateTime = new DateTime(this.endDateTime);
            return new BasicTask(name, isCompleted, startDateTime, endDateTime, tags);
        } else if(this.endDateTime != null) {
            endDateTime = new DateTime(this.endDateTime);
            return new BasicTask(name, isCompleted, endDateTime, tags);
        } else {
            return new BasicTask(name, isCompleted, tags);
        }

    }
}
