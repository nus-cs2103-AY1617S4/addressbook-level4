package seedu.ticktask.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import org.apache.commons.lang.time.DurationFormatUtils;
import seedu.ticktask.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label time;
    @FXML
    private Label date;
    @FXML
    private Label taskType;
    @FXML
    private FlowPane tags;
    @FXML
    private Button button;

    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        if(task.getTime().isRange()){
        	time.setText(task.getTime().toString());
        }
        else{
            time.setText(task.getTime().toString());
        }

        date.setText(task.getDate().toString());


        taskType.setText(task.getTaskType().value.toUpperCase());
        if (task.getTaskType().getValue().equals("event")){
            cardPane.setStyle("-fx-background-color: #ffe3b5;-fx-font-size: 9pt;-fx-text-fill: #010504;");
            if(!task.getCompleted()) {

                initializeEstimatedTimeForEvent(task);
            }
            else{
                System.out.println("This happened");
                button.setText("");
            }

        } else if (task.getTaskType().getValue().equals("deadline")){
            cardPane.setStyle("-fx-background-color: #deffc4;-fx-font-size: 9pt;-fx-text-fill: #010504;");
            if(!task.getCompleted()) {
                initializeEstimatedTimeForDeadline(task);
            }
            else {
                button.setText("");
            }

        } else if (task.getTaskType().getValue().equals("floating")){
            cardPane.setStyle("-fx-background-color: #ccecff; -fx-font-size: 9pt;-fx-text-fill: #0083d1;");
            button.setText("");
        }
        initTags(task);
    }

    //@@author A0139964M
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    public void initializeEstimatedTimeForEvent(ReadOnlyTask task){
        if(task.isHappening()){
            button.setText("HAPPENING");
        } else if(task.isDateDue() || task.isTimeDue()){
            button.setText("Over due");
        } else if(task.getDueDateDuration() >= 1){
            button.setText("Starting in: " + task.getDueDateDuration() + " days");
        }
        else{
            button.setText("Starting in " + DurationFormatUtils.formatDurationWords(task.getDueDurationTime().toMillis(), true, true));
        }
    }

    public void initializeEstimatedTimeForDeadline(ReadOnlyTask task) {
        if (task.isDateDue() || task.isTimeDue()) {
            button.setText("Over Due");
        } else if (task.getDueDateDuration() >= 1) {
            button.setText("DUE IN: " + task.getDueDateDuration() + " days");
        } else {
            button.setText("DUE IN " + DurationFormatUtils.formatDurationWords(task.getDueDurationTime().toMillis(), true, true));
        }
    }
}
//@@author
