package seedu.whatsnext.ui;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.whatsnext.model.task.BasicTaskFeatures;

//@@author A0154987J
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
    private Label from;
    @FXML
    private Label to;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView notice;

    public EventTaskCard(BasicTaskFeatures task, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ".");
        name.setText(task.getName().fullTaskName);
        status.setText(task.getStatusString());
        from.setText("From: " + task.getStartDateTime().displayDateTime());
        to.setText("To: " + task.getEndDateTime().displayDateTime());
        setPriorityColors(task);
        initTags(task);
        notice.setImage(new Image("/images/notice_icon.png"));
        if (!task.getAllTags().contains("OVERLAP")) {
            notice.setVisible(false);
        }
        if (task.getStartDateTime().isBefore(new Date())) {
            from.setStyle("-fx-background-color : #ff0000;");
            to.setStyle("-fx-background-color : #ff0000;");
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
