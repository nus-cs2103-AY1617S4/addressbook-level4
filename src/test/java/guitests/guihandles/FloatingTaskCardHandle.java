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
public class FloatingTaskCardHandle extends GuiHandle {
    private static final String TASKNAME_FIELD_ID = "#taskName";
    private static final String COMPLETED_FIELD_ID = "#completed";
    private static final String CATEGORIES_FIELD_ID = "#tags";

    private Node node;

    public FloatingTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getName() {
        return getTextFromLabel(TASKNAME_FIELD_ID);
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
                && getIsMarkedAsCompleted().equals(task.getStatusString())
                && getTags().equals(getTags());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FloatingTaskCardHandle) {
            FloatingTaskCardHandle handle = (FloatingTaskCardHandle) obj;
            return getName().equals(handle.getName())
                    && getIsMarkedAsCompleted().equals(handle.getIsMarkedAsCompleted())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getName();
    }
}

