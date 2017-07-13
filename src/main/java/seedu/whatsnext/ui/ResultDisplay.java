package seedu.whatsnext.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.events.ui.NewResultAvailableEvent;
import seedu.whatsnext.model.task.BasicTaskFeatures;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private static final StringProperty displayed = new SimpleStringProperty("Hello!");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        //@@author A0154987J
        resultDisplay.setWrapText(true);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayed.setValue(event.message);
    }

    //displays details of selected task inside resultDisplay box
    public static void showSelectedTask(BasicTaskFeatures task) {
        if (task.getTaskType().equals("event")) {
            displayed.setValue("Task name: " + task.getName() + "\n"
                + "Tags: " + task.getAllTags() + "\n"
                + "Status: " + task.getStatusString() + "\n"
                + "From: " + task.getStartDateTime().toString() + " "
                + "To: " + task.getEndDateTime().toString() + "\n"
                + "Description: " + task.getDescription().toString());
        } else if (task.getTaskType().equals("deadline")) {
            displayed.setValue("Task name: " + task.getName() + "\n"
                    + "Tags: " + task.getAllTags() + "\n"
                    + "Status: " + task.getStatusString() + "\n"
                    + "Due by: " + task.getEndDateTime().toString() + "\n"
                    + "Description: " + task.getDescription().toString());
        } else if (task.getTaskType().equals("floating")) {
            displayed.setValue("Task name: " + task.getName() + "\n"
                    + "Tags: " + task.getAllTags() + "\n"
                    + "Status: " + task.getStatusString() + "\n"
                    + "Description: " + task.getDescription().toString());
        }
    }
}
