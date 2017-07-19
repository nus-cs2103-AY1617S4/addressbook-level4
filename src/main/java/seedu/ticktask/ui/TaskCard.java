package seedu.ticktask.ui;

import java.time.LocalDate;

import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
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
import seedu.ticktask.model.task.ReadOnlyTask;

public class TaskCard extends UiPart<Region> {

    private static final String FXML = "TaskListCard.fxml";
    private static final String TICK = "/images/TICK.png";
    private static final String NO_TICK = "/images/Transparent_Background.png";
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
   
    public TaskCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        Image tick = new Image(TICK);
        Image no_tick = new Image(NO_TICK);
        if(task.getCompleted()){
          ticksymbol.setFill(new ImagePattern(tick));
       }
        else{
          ticksymbol.setFill(new ImagePattern(no_tick));
        }
        
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
            cardPane.setStyle("-fx-background-color: #ffe3b5;");
            taskType.setStyle("-fx-font-size: 10pt;-fx-text-fill: #ba7503;");
        } else if (task.getTaskType().getValue().equals("deadline")){
            cardPane.setStyle("-fx-background-color: #deffc4;");
            taskType.setStyle("-fx-font-size: 10pt;-fx-text-fill: #3a8202;");
        } else if (task.getTaskType().getValue().equals("floating")){
            cardPane.setStyle("-fx-background-color: #ccecff;");
            taskType.setStyle("-fx-font-size: 10pt;-fx-text-fill: #0372b5;");
        }
       
            
          
      if(!task.isChornological())
            setTextColor(id,name,time,date,taskType,Color.RED); 
            
        initTags(task);
    }
    private void setTextColor(Label id, Label name, Label time, Label date, 
            Label taskType, Color color) {
        id.setStyle("-fx-text-fill: #ff0000;");
        name.setStyle("-fx-text-fill: #ff0000;");
        time.setStyle("-fx-text-fill: #ff0000;");
        date.setStyle("-fx-text-fill: #ff0000;");
        taskType.setStyle("-fx-text-fill: #ff0000;-fx-font-size: 10pt;");
    }

    private void initTags(ReadOnlyTask task) {
        task.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
