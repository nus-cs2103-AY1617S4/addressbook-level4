package guitests;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import guitests.guihandles.DeadlineTaskCardHandle;
import guitests.guihandles.EventTaskCardHandle;
import guitests.guihandles.FloatingTaskCardHandle;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;

public class AddCommandTest extends TaskManagerGuiTest {

    //@@author A0154987J
    @Test
    public void add() throws IllegalValueException {
        commandBox.pressEnter();
        commandBox.runCommand("list all");
        //add one floating task
        BasicTask taskToAdd = new BasicTask(new TaskName("Buy a country"),
                new TaskDescription("to rule"), getTagSet());
        commandBox.runCommand("add Buy a country m/to rule");
        assertAddSuccess(taskToAdd);
        //add duplicate floating task
        commandBox.runCommand("add Buy a country m/to rule");
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);

        //add one deadline task
        taskToAdd = new BasicTask(new TaskName("Buy Present For Gf"),
                new TaskDescription("What She Likes"),
                new DateTime("next Monday"), getTagSet("medium"));
        commandBox.runCommand("add Buy Present For Gf m/What She Likes e/next Monday t/medium");
        assertAddSuccess(taskToAdd);
        //add duplicate deadline task
        commandBox.runCommand("add Buy Present For Gf m/What She Likes e/next Monday t/medium");
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);

        //add event task
        commandBox.runCommand("add Watch Csgo Major m/Fun stuffs s/4 september e/5 september t/low");
        taskToAdd = new BasicTask(new TaskName("Watch Csgo Major"), new TaskDescription("Fun stuffs"),
                new DateTime("4 september"), new DateTime("5 september"), getTagSet("low"));
        assertAddSuccess(taskToAdd);
        //add duplicate event task
        commandBox.runCommand("add Watch Csgo Major m/Fun stuffs s/4 september e/5 september t/low");
        assertResultMessage(AddCommand.MESSAGE_DUPLICATE_TASK);
    }

    private void assertAddSuccess(BasicTask taskToAdd) {
        // confirm the new card contains the right data
        if (taskToAdd.getTaskType().equals("event")) {
            EventTaskCardHandle addedCard = eventListPanel.navigateToEventTask(taskToAdd.getName().toString());
            assertMatching(taskToAdd, addedCard);
        } else {
            if (taskToAdd.getTaskType().equals("deadline")) {
                DeadlineTaskCardHandle addedCard = deadlineListPanel
                        .navigateToDeadlineTask(taskToAdd.getName().toString());
                assertMatching(taskToAdd, addedCard);
            } else {
                if (taskToAdd.getTaskType().equals("floating")) {
                    FloatingTaskCardHandle addedCard = floatingListPanel
                            .navigateToFloatingTask(taskToAdd.getName().toString());
                    assertMatching(taskToAdd, addedCard);
                }
            }
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }
}
