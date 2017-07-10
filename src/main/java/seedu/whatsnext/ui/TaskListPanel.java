package seedu.whatsnext.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
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
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "TaskListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<BasicTaskFeatures> taskListView;

    public TaskListPanel(ObservableList<BasicTaskFeatures> taskList) {
        super(FXML);
        setConnections(taskList);
    }

    private void setConnections(ObservableList<BasicTaskFeatures> taskList) {

        taskListView.setItems(taskList);
        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        taskListView.getSelectionModel().selectedItemProperty()
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
            taskListView.getSelectionModel().clearSelection();
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    public void clearSelection() {
        taskListView.getSelectionModel().clearSelection();
    }

    class TaskListViewCell extends ListCell<BasicTaskFeatures> {

        @Override
        protected void updateItem(BasicTaskFeatures task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(task, getIndex() + 1).getRoot());
            }
        }
    }
}
