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

/**
 * Panel containing the list of Tasks.
 */
public class DeadlineListPanel extends UiPart<Region> {
    private static final String FXML = "DeadlineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FloatingListPanel.class);

    @FXML
    private ListView<BasicTaskFeatures> deadlineListView;

    public DeadlineListPanel(ObservableList<BasicTaskFeatures> taskList) {
        super(FXML);
        setConnections(taskList);
    }

    private void setConnections(ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<BasicTaskFeatures> deadlineTaskList = extractDeadlineTasks(taskList);
        deadlineListView.setItems(deadlineTaskList);
        deadlineListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private ObservableList<BasicTaskFeatures> extractDeadlineTasks(ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<BasicTaskFeatures> deadlineTaskList = FXCollections.observableArrayList();
        for (int index = 0; taskList.size() != index; index++) {
            BasicTaskFeatures taskToConsider = taskList.get(index);
            if (taskToConsider.getTaskType().equals("deadline")) {
                deadlineTaskList.add(taskToConsider);
            }
        }
        return deadlineTaskList;
    }

    private void setEventHandlerForSelectionChangeEvent() {
        deadlineListView.getSelectionModel().selectedItemProperty()
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
            deadlineListView.getSelectionModel().clearSelection();
            deadlineListView.scrollTo(index);
            deadlineListView.getSelectionModel().clearAndSelect(index);
        });
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
