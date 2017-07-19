package seedu.ticktask.ui;

import java.time.LocalDate;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import seedu.ticktask.model.TickTask;
import org.apache.commons.lang.time.DurationFormatUtils;
import seedu.ticktask.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {
    //@@author A0138471A
    private static final String FXML = "TaskListCard.fxml";
    private static final String TICK = "/images/TICK.png";
    private static final String NO_TICK = "/images/Transparent_Background.png";
  //@@author
    //@@author A0139964M
    private static String MESSAGE_DISPLAY_DUE_IN = "Due in: ";
    private static String MESSAGE_DISPLAY_HAPPENING= "Happening";
    private static String MESSAGE_DISPLAY_OVER_DUE = "Over Due";
    private static String MESSAGE_DISPLAY_PASSED = "Passed";
    private static String MESSAGE_DISPLAY_STARTING_IN = "Starting in: ";
    private static String TASKTYPE_EVENT ="event";
    private static String TASKTYPE_DEADLINE ="deadline";
    private static String TASKTYPE_FLOATING ="floating";
    //@@author


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
    private Rectangle ticksymbol; 
    @FXML
    private Button button;
  
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
      
      //@@author A0138471A
        Image tick = new Image(TICK);
        Image no_tick = new Image(NO_TICK);
        if(task.getCompleted()){
          ticksymbol.setFill(new ImagePattern(tick));
       }
        else{
          ticksymbol.setFill(new ImagePattern(no_tick));
        }
      //@@author
        
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        if(task.getTime().isRange()){
            time.setText(task.getTime().toString());
        }
        else{
            time.setText(task.getTime().toString());
        }
        date.setText(task.getDate().toString());
  
 
        
        //@@author A0139964M
        taskType.setText(task.getTaskType().value.toUpperCase());
        if (task.getTaskType().getValue().equals(TASKTYPE_EVENT)){
            cardPane.setStyle("-fx-background-color: #ffe3b5;");
          taskType.setStyle("-fx-font-size: 10pt;-fx-text-fill: #ba7503;");
            if(!task.getCompleted()) {
                initializeEstimatedTimeForEvent(task);
            }
            else{
                button.setStyle("visibility: hidden;");
            }

        } else if (task.getTaskType().getValue().equals(TASKTYPE_DEADLINE)){
            cardPane.setStyle("-fx-background-color: #deffc4;");
          taskType.setStyle("-fx-font-size: 10pt;-fx-text-fill: #3a8202;");
            if(!task.getCompleted()) {
                initializeEstimatedTimeForDeadline(task);
            }
            else {
                button.setStyle("visibility: hidden;");
            }

        } else if (task.getTaskType().getValue().equals(TASKTYPE_FLOATING)){
            cardPane.setStyle("-fx-background-color: #ccecff;");
          taskType.setStyle("-fx-font-size: 10pt;-fx-text-fill: #0372b5;");
            button.setStyle("visibility: hidden;");
        }
        initTags(task);
    }
    //@@author
//@@author A0138471A
  private void setTextColor(Label id, Label name, Label time, Label date, 
            Label taskType, Color color) {
        id.setStyle("-fx-text-fill: #ff0000;");
        name.setStyle("-fx-text-fill: #ff0000;");
        time.setStyle("-fx-text-fill: #ff0000;");
        date.setStyle("-fx-text-fill: #ff0000;");
        taskType.setStyle("-fx-text-fill: #ff0000;-fx-font-size: 10pt;");
    }
//@@author
    //@@author A0139964M
    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    public void initializeEstimatedTimeForEvent(ReadOnlyTask task){
        if(task.isHappening()){
            button.setId("buttonHappening");
            button.setText(MESSAGE_DISPLAY_HAPPENING);
        } else if(task.isDateDue() || task.isTimeDue()){
            button.setId("buttonOverDue");
            setTextColor(id,name,time,date,taskType,Color.RED);
            button.setText(MESSAGE_DISPLAY_PASSED);
        } else if(task.getDueDateDuration() >= 1){
            button.setText(MESSAGE_DISPLAY_STARTING_IN + task.getDueDateDuration() + " days");
        }
        else{
            button.setText(MESSAGE_DISPLAY_STARTING_IN + DurationFormatUtils.formatDurationWords(task.getDueDurationTime().toMillis(),
                    true, true));
        }
    }

    public void initializeEstimatedTimeForDeadline(ReadOnlyTask task) {
        if (task.isDateDue() || task.isTimeDue()) {
            button.setId("buttonOverDue");
            setTextColor(id,name,time,date,taskType,Color.RED);
            button.setText(MESSAGE_DISPLAY_OVER_DUE);
        } else if (task.getDueDateDuration() >= 1) {
            button.setText(MESSAGE_DISPLAY_DUE_IN + task.getDueDateDuration() + " days");
        } else {
            button.setText(MESSAGE_DISPLAY_DUE_IN + DurationFormatUtils.formatDurationWords(task.getDueDurationTime().toMillis(),
                    true, true));
        }
    }
}
//@@author
