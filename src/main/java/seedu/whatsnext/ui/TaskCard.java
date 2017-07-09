package seedu.whatsnext.ui;

import com.sun.prism.paint.Color;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.whatsnext.model.task.BasicTaskFeatures;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private GridPane cardBackground;

    public TaskCard(BasicTaskFeatures task, int displayedIndex) {
        super(FXML);
        id.setText(displayedIndex + ".");
        name.setText(task.getName().fullTaskName);
        //@@author A0154987J
        name.setWrapText(true);
        setPriorityColors(task);
    }

	private void setPriorityColors(BasicTaskFeatures task) {
		if (task.getAllTags().contains("HIGH")) {
            cardBackground.setStyle("-fx-border-color : red; "
            		+ "-fx-border-width : 5px");
        } else if (task.getAllTags().contains("MEDIUM")) {
            cardBackground.setStyle("-fx-border-color : green; "
            		+ "-fx-border-width : 5px");
        } else if (task.getAllTags().contains("LOW")) {
            cardBackground.setStyle("-fx-border-color : yellow; "
            		+ "-fx-border-width : 5px");
        }
	}
}
