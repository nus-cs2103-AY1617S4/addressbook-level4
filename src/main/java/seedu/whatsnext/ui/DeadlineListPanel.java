package seedu.whatsnext.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Pair;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.whatsnext.model.task.BasicTaskFeatures;

/**
 * Panel containing the list of deadline tasks.
 */

//@@author A0154987J
public class DeadlineListPanel extends UiPart<Region> {
    private static final String FXML = "DeadlineListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(DeadlineListPanel.class);

    @FXML
    private ListView<Pair<BasicTaskFeatures, Integer>> deadlineListView;

    public DeadlineListPanel(ObservableList<BasicTaskFeatures> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    public void setConnections(ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<Pair<BasicTaskFeatures, Integer>> deadlineList = extractDeadlineTasks(taskList);
        deadlineListView.setItems(deadlineList);
        deadlineListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        deadlineListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue.getKey()));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            deadlineListView.scrollTo(index);
            deadlineListView.getSelectionModel().clearAndSelect(index);
        });
    }

    public ListView<Pair<BasicTaskFeatures, Integer>> getDeadlineListView() {
        return this.deadlineListView;
    }

    class TaskListViewCell extends ListCell<Pair<BasicTaskFeatures, Integer>> {

        @Override
        protected void updateItem(Pair<BasicTaskFeatures, Integer> task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DeadlineTaskCard(task.getKey(), task.getValue() + 1).getRoot());
            }
        }
    }

    /**
     * @param taskList
     * @return ObserableList containing only deadline tasks with their respective index from original list
     */
    private ObservableList<Pair<BasicTaskFeatures, Integer>> extractDeadlineTasks(
            ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<Pair<BasicTaskFeatures, Integer>> deadlineList = FXCollections.observableArrayList();
        for (int index = 0; taskList.size() != index; index++) {
            BasicTaskFeatures taskToConsider = taskList.get(index);
            if (taskToConsider.getTaskType().equals("deadline")) {
                Pair<BasicTaskFeatures, Integer> deadlineTask =
                        new Pair<BasicTaskFeatures, Integer>(taskToConsider, index);
                deadlineList.add(deadlineTask);
            }
        }
        return deadlineList;
    }
}
