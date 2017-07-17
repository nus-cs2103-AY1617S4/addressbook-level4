package guitests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;

public class AddCommandTest extends TaskManagerGuiTest {

    // @@author A0154987J
    @Test
    public void add() throws IllegalValueException {

        commandBox.pressEnter();
        //add floating task
        commandBox.runCommand("add Buy a country m/to rule");
        BasicTask floatToAdd = new BasicTask(new TaskName("Buy a country"),
                new TaskDescription("to rule"), getTagSet());
        assertResultMessage(floatToAdd.getTaskDetails());
        BasicTaskFeatures selectedFloatingTask = floatingListPanel.getSelectedTasks().get(0).getKey();
        assertEquals(floatToAdd, selectedFloatingTask);
        //add duplicate floating task
        commandBox.runCommand("add Buy a country m/to rule");
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");

        //add deadline task
        commandBox.runCommand("add Buy Present For Gf m/What She Likes e/next Monday t/gf");
        BasicTask deadlineToAdd = new BasicTask(new TaskName("Buy Present For Gf"),
                new TaskDescription("What She Likes"),
                new DateTime("next Monday"), getTagSet("gf"));
        assertResultMessage(deadlineToAdd.getTaskDetails());
        BasicTaskFeatures selectedDeadlineTask = deadlineListPanel.getSelectedTasks().get(0).getKey();
        assertEquals(deadlineToAdd, selectedDeadlineTask);
        //add duplicate deadline task
        commandBox.runCommand("add Buy Present For Gf m/What She Likes e/next Monday t/gf");
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");

        //add event task
        commandBox.runCommand("add Watch Csgo Major m/Fun stuffs s/4 september e/5 september t/overlapping");
        BasicTask eventToAdd = new BasicTask(new TaskName("Watch Csgo Major"), new TaskDescription("Fun stuffs"),
                new DateTime("4 september"), new DateTime("5 september"), getTagSet("overlapping"));
        assertResultMessage(eventToAdd.getTaskDetails());
        BasicTaskFeatures selectedEventTask = eventListPanel.getSelectedTasks().get(0).getKey();
        assertEquals(eventToAdd, selectedEventTask);
        //add duplicate event task
        commandBox.runCommand("add Watch Csgo Major m/Fun stuffs s/4 september e/5 september t/overlapping");
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
        commandBox.runCommand("undo");
        commandBox.runCommand("undo");
    }



    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }
}
