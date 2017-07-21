package seedu.whatsnext.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.whatsnext.model.task.BasicTaskFeatures;

//@@author A0154987J
public class DeadlineTaskCard extends UiPart<Region> {

    private static final String FXML = "DeadlineTaskCard.fxml";

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
    private Label dueBy;
    @FXML
    private FlowPane tags;

    public DeadlineTaskCard(BasicTaskFeatures task, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ".");
        name.setText(task.getName().fullTaskName);
        status.setText(task.getStatusString());
        dueBy.setText("Due by: " + task.getEndDateTime().displayDateTime());
        setPriorityColors(task);
        initTags(task);
        if (task.getEndDateTime().isBefore(new Date())) {
            dueBy.setStyle("-fx-background-color : #ff0000;");
        }
    }

    private void initTags(BasicTaskFeatures task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

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

