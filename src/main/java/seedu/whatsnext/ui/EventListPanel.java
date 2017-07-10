package seedu.whatsnext.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.ui.DeadlineListPanel.TaskListViewCell;

/**
 * Panel containing the list of Tasks.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FloatingListPanel.class);

    @FXML
    private ListView<BasicTaskFeatures> eventListView;

    public EventListPanel(ObservableList<BasicTaskFeatures> taskList) {
        super(FXML);
        setConnections(taskList);
    }

    private void setConnections(ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<BasicTaskFeatures> eventTaskList = extractEventTasks(taskList);
        eventListView.setItems(eventTaskList);
        eventListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private ObservableList<BasicTaskFeatures> extractEventTasks(ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<BasicTaskFeatures> eventTaskList = FXCollections.observableArrayList();
        for (int index = 0; taskList.size() != index; index++) {
            BasicTaskFeatures taskToConsider = taskList.get(index);
            if (taskToConsider.getTaskType().equals("event")) {
                eventTaskList.add(taskToConsider);
            }
        }
        return eventTaskList;
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observablse, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    //@@author A0154987J
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            //scrolls to task with index and selects it
            eventListView.getSelectionModel().clearSelection();
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    public void clearSelection() {
        eventListView.getSelectionModel().clearSelection();
    }

    class TaskListViewCell extends ListCell<BasicTaskFeatures> {

        @Override
        protected void updateItem(BasicTaskFeatures task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FloatingTaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }
}

