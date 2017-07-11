package seedu.whatsnext.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.whatsnext.model.task.BasicTaskFeatures;

public class EventTaskCard extends UiPart<Region> {

    private static final String FXML = "EventTaskCard.fxml";

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
    private Label fromTo;
    @FXML
    private FlowPane tags;

    public EventTaskCard(BasicTaskFeatures task, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ".");
        name.setText(task.getName().fullTaskName);
        //@@author A0154987J
        //name.setWrapText(true);
        status.setText(task.getStatusString());
        fromTo.setText("From: " + task.getStartDateTime().toString() + "  To: " + task.getEndDateTime().toString());
        setPriorityColors(task);
        initTags(task);
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
