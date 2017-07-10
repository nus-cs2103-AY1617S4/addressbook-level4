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
public class FloatingListPanel extends UiPart<Region> {
    private static final String FXML = "FloatingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FloatingListPanel.class);

    @FXML
    private ListView<BasicTaskFeatures> floatingListView;

    public FloatingListPanel(ObservableList<BasicTaskFeatures> taskList) {
        super(FXML);
        setConnections(taskList);
    }

    private void setConnections(ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<BasicTaskFeatures> floatingTaskList = extractFloatingTasks(taskList);
        floatingListView.setItems(floatingTaskList);
        floatingListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private ObservableList<BasicTaskFeatures> extractFloatingTasks(ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<BasicTaskFeatures> floatingTaskList = FXCollections.observableArrayList();
        for (int index = 0; taskList.size() != index; index++) {
            BasicTaskFeatures taskToConsider = taskList.get(index);
            if (taskToConsider.getTaskType().equals("floating")) {
                floatingTaskList.add(taskToConsider);
            }
        }
        return floatingTaskList;
    }

    private void setEventHandlerForSelectionChangeEvent() {
        floatingListView.getSelectionModel().selectedItemProperty()
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
            floatingListView.getSelectionModel().clearSelection();
            floatingListView.scrollTo(index);
            floatingListView.getSelectionModel().clearAndSelect(index);
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
