package seedu.whatsnext.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.whatsnext.model.task.BasicTaskFeatures;

//@@author A0154987J
/**
 * A card that shows the details of a floating task
 */
public class FloatingTaskCard extends UiPart<Region> {

    private static final String FXML = "FloatingTaskCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label status;
    @FXML
    private GridPane cardBackground;
    @FXML
    private FlowPane tags;

    public FloatingTaskCard(BasicTaskFeatures task, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ".");
        name.setText(task.getName().fullTaskName);
        status.setText(task.getStatusString());
        setPriorityColors(task);
        initTags(task);
    }

    private void initTags(BasicTaskFeatures task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Changes the border color of the task to indicate its priority
     */
    private void setPriorityColors(BasicTaskFeatures task) {
        if (task.getAllTags().contains("HIGH")) {
            cardBackground.setStyle("-fx-border-color : #ff0000; "
                    + "-fx-border-width : 5px");
        } else if (task.getAllTags().contains("MEDIUM")) {
            cardBackground.setStyle("-fx-border-color : #ffff00; "
                    + "-fx-border-width : 5px");
        } else if (task.getAllTags().contains("LOW")) {
            cardBackground.setStyle("-fx-border-color : #27e833; "
                    + "-fx-border-width : 5px");
        }
    }
}
