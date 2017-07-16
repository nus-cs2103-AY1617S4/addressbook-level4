package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.whatsnext.model.tag.UniqueTagList;
import seedu.whatsnext.model.task.BasicTaskFeatures;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class EventTaskCardHandle extends GuiHandle {
    private static final String TASKNAME_FIELD_ID = "#taskName";
    private static final String STARTDATETIME_FIELD_ID = "#startDateTime";
    private static final String ENDDATETIME_FIELD_ID = "#endDateTime";
    private static final String COMPLETED_FIELD_ID = "#markedCompleted";
    private static final String CATEGORIES_FIELD_ID = "#tags";

    private Node node;

    public EventTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(TASKNAME_FIELD_ID);
    }

    public String getStartDate() {
        String startDate = getTextFromLabel(STARTDATETIME_FIELD_ID);
        startDate = startDate.substring(12 , startDate.length() - 19);
        return startDate;
    }

    public String getStartTime() {
        String startTime = getTextFromLabel(STARTDATETIME_FIELD_ID);
        startTime = startTime.substring(startTime.length() - 4);
        return startTime;
    }

    public String getEndDate() {
        String endDate = getTextFromLabel(ENDDATETIME_FIELD_ID);
        endDate = endDate.substring(12 , endDate.length() - 19);
        return endDate;
    }

    public String getEndTime() {
        String endTime = getTextFromLabel(ENDDATETIME_FIELD_ID);
        endTime = endTime.substring(endTime.length() - 4);
        return endTime;
    }

    public String getIsMarkedAsCompleted() {
        return getTextFromLabel(COMPLETED_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer.getChildrenUnmodifiable().stream().map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags.asObservableList().stream().map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(CATEGORIES_FIELD_ID).query();
    }

    public boolean isSameTask(BasicTaskFeatures task) {
        return task != null || this == task && getName().equals(task.getName().toString())
                && getStartDate().equals(task.getStartDateTime().toString()) && getStartTime().equals(task.getStartDateTime().toString())
                && getEndDate().equals(task.getEndDateTime().toString()) && getEndTime().equals(task.getEndDateTime().toString())
                && getIsMarkedAsCompleted().equals(task.getStatusString())
                && getTags().equals(getTags());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventTaskCardHandle) {
            EventTaskCardHandle handle = (EventTaskCardHandle) obj;
            return getName().equals(handle.getName()) && getStartDate().equals(handle.getStartDate())
                    && getStartTime().equals(handle.getStartTime()) && getEndDate().equals(handle.getEndDate())
                    && getEndTime().equals(handle.getEndTime())
                    && getIsMarkedAsCompleted().equals(handle.getIsMarkedAsCompleted())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName() + " " + getStartDate() + " " + getStartTime() + " " + getEndDate() + " " + getEndTime();
    }
}

