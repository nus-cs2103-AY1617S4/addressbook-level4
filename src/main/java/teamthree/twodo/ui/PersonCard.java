package teamthree.twodo.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import teamthree.twodo.model.task.ReadOnlyTask;
import teamthree.twodo.model.task.TaskWithDeadline;

public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Description: Certain keywords such as "location" and "resources" are
     * reserved keywords in JavaFX. As a consequence, UI elements' variable
     * names cannot be set to such keywords or an exception will be thrown by
     * JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">
     *      The issue on TaskBook level 4</a>
     */

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label deadline;
    @FXML
    private Label description;

    @FXML
    private FlowPane tags;

    public PersonCard(ReadOnlyTask task, int displayedIndex) {
        super(FXML);
        name.setText(task.getName().fullName);
        id.setText(displayedIndex + ". ");
        if (task instanceof TaskWithDeadline) {
            deadline.setText(task.getDeadline().get().toString());
        } else {
            deadline.setText("OT,OT");
        }
        if (task.getDescription().toString().isEmpty()) {
            description.setText("No description");
        } else {
            description.setText(task.getDescription().toString());
        }
        //email.setText(person.getEmail().value);
        initTags(task);
    }

    private void initTags(ReadOnlyTask person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
