package seedu.whatsnext.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.model.task.BasicTaskFeatures;

/**
 * Panel containing the list of persons.
 */
public class TaskListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    @FXML
    private ListView<BasicTaskFeatures> taskListView;

    public TaskListPanel(ObservableList<BasicTaskFeatures> personList) {
        super(FXML);
        setConnections(personList);
    }

    private void setConnections(ObservableList<BasicTaskFeatures> personList) {
//        taskListView.setItems(personList);
//        taskListView.setCellFactory(listView -> new TaskListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
//        taskListView.getSelectionModel().selectedItemProperty()
//                .addListener((observable, oldValue, newValue) -> {
//                    if (newValue != null) {
//                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
//                        raise(new TaskPanelSelectionChangedEvent(newValue));
//                    }
//                });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            taskListView.scrollTo(index);
            taskListView.getSelectionModel().clearAndSelect(index);
        });
    }

    class TaskListViewCell extends ListCell<BasicTaskFeatures> {

        @Override
        protected void updateItem(BasicTaskFeatures person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TaskCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
