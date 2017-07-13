package seedu.whatsnext.ui;

import java.util.HashMap;
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
 * Panel containing the list of tasks.
 */
public class EventListPanel extends UiPart<Region> {
    private static final String FXML = "EventListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EventListPanel.class);

    @FXML
    private ListView<Pair<BasicTaskFeatures, Integer>> eventListView;

    private HashMap<Integer, Integer> eventMap = new HashMap<Integer, Integer>();
    private int scrollIndex = 0;

    public EventListPanel(ObservableList<BasicTaskFeatures> taskList) {
        super(FXML);
        setConnections(taskList);
        registerAsAnEventHandler(this);
    }

    public void setConnections(ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<Pair<BasicTaskFeatures, Integer>> eventList = extracteventTasks(taskList);
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        eventListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in task list panel changed to : '" + newValue + "'");
                        raise(new TaskPanelSelectionChangedEvent(newValue.getKey()));
                    }
                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            eventListView.scrollTo(index);
            eventListView.getSelectionModel().clearAndSelect(index);
        });
    }

    public ListView<Pair<BasicTaskFeatures, Integer>> getEventListView() {
        return this.eventListView;
    }

    class TaskListViewCell extends ListCell<Pair<BasicTaskFeatures, Integer>> {

        @Override
        protected void updateItem(Pair<BasicTaskFeatures, Integer> task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventTaskCard(task.getKey(), task.getValue() + 1).getRoot());
            }
        }
    }

    /**
     * @param taskList
     * @return ObserableList containing only event tasks with their respective index from original list
     */
    //@@author A0154987J
    private ObservableList<Pair<BasicTaskFeatures, Integer>> extracteventTasks(
            ObservableList<BasicTaskFeatures> taskList) {
        ObservableList<Pair<BasicTaskFeatures, Integer>> eventList = FXCollections.observableArrayList();
        for (int index = 0; taskList.size() != index; index++) {
            BasicTaskFeatures taskToConsider = taskList.get(index);
            if (taskToConsider.getTaskType().equals("event")) {
                Pair<BasicTaskFeatures, Integer> eventTask = new Pair<BasicTaskFeatures, Integer>(taskToConsider, index);
                eventList.add(eventTask);
                eventMap.put(index, scrollIndex);
                scrollIndex++;
            }
        }
        return eventList;
    }

    public HashMap<Integer, Integer> getMap() {
        return eventMap;
    }
}
