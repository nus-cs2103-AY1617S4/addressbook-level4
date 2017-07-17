package seedu.whatsnext.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTaskFeatures;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     * @throws IllegalValueException
     * @throws TagNotFoundException
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) throws CommandException, TagNotFoundException, IllegalValueException {
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(expectedModel, actualModel);
    }

    //@@author A0156106M
    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged
     * @throws IllegalValueException
     * @throws TagNotFoundException
     */
    public static void assertCommandFailure(Command command, Model actualModel,
            String expectedMessage) throws TagNotFoundException, IllegalValueException {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TaskManager expectedTaskManager = new TaskManager(actualModel.getTaskManager());
        List<BasicTaskFeatures> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        assertEquals(expectedTaskManager, actualModel.getTaskManager());
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            ArrayList<Tag> tagList = new ArrayList<>();
            // Removes overlap tag
            for (Tag tag : actualModel.getTaskManager().getTagList()) {
                if (!tag.tagName.equals(Tag.RESERVED_TAG_OVERLAP)) {
                    tagList.add(tag);
                }
            }

            TaskManager actualTaskManager = new TaskManager(actualModel.getTaskManager());
            actualTaskManager.setTags(tagList);
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTaskManager, actualTaskManager);
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
        }
    }
}
