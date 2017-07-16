package seedu.ticktask.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.ticktask.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.TaskType;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "waskdf**t";
    private static final String INVALID_TIME = "10000";
    private static final String INVALID_DATE = "230989";
    private static final String INVALID_TASK_TYPE = "invalid";
    private static final String INVALID_TAG = "t/invalid";

    private static final String VALID_NAME = "wash dog";
    private static final String VALID_TIME = "10pm";
    private static final String VALID_DATE = "christmas";
    private static final String VALID_TASK_TYPE = "deadline";
    private static final String VALID_TAG_1 = "chore";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of(INVALID_NAME));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValue_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Optional<Name> actualName = ParserUtil.parseName(Optional.of(VALID_NAME));

        assertEquals(expectedName, actualName.get());
    }

    @Test
    public void parseTime_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTime(null);
    }

    @Test
    public void parseTime_validValue_returnsDueTime() throws Exception {
        DueTime expectedDueTime = new DueTime(VALID_TIME);
        Optional<DueTime> actualDueTime = ParserUtil.parseTime(Optional.of(VALID_TIME));

        assertEquals(expectedDueTime, actualDueTime.get());
    }

    @Test
    public void parseDate_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDate(null);
    }

    @Test
    public void parseDate_validValue_returnsDueDate() throws Exception {
        DueDate expectedDueDate = new DueDate(VALID_DATE);
        Optional<DueDate> actualDueDate = ParserUtil.parseDate(Optional.of(VALID_DATE));

        assertEquals(expectedDueDate, actualDueDate.get());
    }

    @Test
    public void parseTaskType_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTaskType(null);
    }

    @Test
    public void parseTaskType_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTaskType(Optional.of(INVALID_TASK_TYPE));
    }


    @Test
    public void parseTaskType_validValue_returnsTaskType() throws Exception {
        TaskType expectedTaskType = new TaskType(VALID_TASK_TYPE);
        Optional<TaskType> actualTaskType = ParserUtil.parseTaskType(Optional.of(VALID_TASK_TYPE));

        assertEquals(expectedTaskType, actualTaskType.get());
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}