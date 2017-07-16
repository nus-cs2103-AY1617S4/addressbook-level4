package seedu.whatsnext.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_DESCRIPTION_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_DESCRIPTION_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_DESCRIPTION_READBORNACRIME;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_ENDDATETIME_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_ENDDATETIME_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_NAME_PROJECTDEMO;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_NAME_PROJECTMEETING;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_NAME_READBORNACRIME;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_STARTDATETIME_PROJECTMEETING;

import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_CS2010;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_CS2103;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_HIGH;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_LOW;
import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_MEDIUM;

import static seedu.whatsnext.testutil.TypicalTasks.INDEX_FIRST_TASK;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_SECOND_TASK;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.EditCommand;
import seedu.whatsnext.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.whatsnext.logic.parser.exceptions.ParseException;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.testutil.EditTaskDescriptorBuilder;

public class EditCommandParserTest {

    private static final String NAME_DESC_PROJECTMEETING = " " + PREFIX_NAME + VALID_NAME_PROJECTMEETING;
    private static final String STARTDATETIME_DESC_PROJECTMEETING =
                            " " + PREFIX_START_DATETIME + VALID_STARTDATETIME_PROJECTMEETING;
    private static final String ENDDATETIME_DESC_PROJECTMEETING =
                            " " + PREFIX_END_DATETIME + VALID_ENDDATETIME_PROJECTMEETING;
    private static final String DESCRIPTION_DESC_PROJECTMEETING =
                            " " + PREFIX_MESSAGE + VALID_DESCRIPTION_PROJECTMEETING;

    private static final String NAME_DESC_PROJECTDEMO = " " + PREFIX_NAME + VALID_NAME_PROJECTDEMO;
    private static final String ENDDATETIME_DESC_PROJECTDEMO =
                                " " + PREFIX_END_DATETIME + VALID_ENDDATETIME_PROJECTDEMO;
    private static final String DESCRIPTION_DESC_PROJECTDEMO = " " + PREFIX_MESSAGE + VALID_DESCRIPTION_PROJECTDEMO;

    private static final String NAME_DESC_READBORNACRIME = " " + PREFIX_NAME + VALID_NAME_READBORNACRIME;
    private static final String DESCRIPTION_DESC_READBORNACRIME =
                                        " " + PREFIX_MESSAGE + VALID_DESCRIPTION_READBORNACRIME;

    private static final String NEWTAG_DESC_CS2010 = " " + PREFIX_NEW_TAG + VALID_TAG_CS2010;
    private static final String NEWTAG_DESC_CS2103 = " " +  PREFIX_NEW_TAG + VALID_TAG_CS2103;
    private static final String NEWTAG_DESC_HIGH = " " + PREFIX_NEW_TAG + VALID_TAG_HIGH;
    private static final String NEWTAG_DESC_MEDIUM = " " + PREFIX_NEW_TAG + VALID_TAG_MEDIUM;
    private static final String NEWTAG_DESC_LOW = " " + PREFIX_NEW_TAG + VALID_TAG_LOW;

    private static final String REMOVETAG_DESC_CS2010 = " " + PREFIX_DELETE_TAG + VALID_TAG_CS2010;
    private static final String REMOVETAG_DESC_CS2103 = " " +  PREFIX_DELETE_TAG + VALID_TAG_CS2103;
    private static final String REMOVETAG_DESC_HIGH = " " + PREFIX_DELETE_TAG + VALID_TAG_HIGH;
    private static final String REMOVETAG_DESC_MEDIUM = " " + PREFIX_DELETE_TAG + VALID_TAG_MEDIUM;
    private static final String REMOVETAG_DESC_LOW = " " + PREFIX_DELETE_TAG + VALID_TAG_LOW;

    private static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "Problem Set 4:SubTask D";
    private static final String INVALID_STARTDATETIME_DESC = " " + PREFIX_START_DATETIME + "neqp";
    private static final String INVALID_ENDDATETIME_DESC = " " + PREFIX_END_DATETIME + "qwfpmpa";
    private static final String INVALID_NEWTAG_DESC = " " + PREFIX_NEW_TAG + "@Noon";
    private static final String INVALID_REMOVETAG_DESC = " " + PREFIX_DELETE_TAG + "tom's tag";
    private static final String INVALID_DESCRIPTION_DESC =
                                        " " + PREFIX_MESSAGE + "NOTE: MUST DO"; // ":" not allowed in messages

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(VALID_NAME_PROJECTMEETING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure("1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure("", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure("-5" + NAME_DESC_PROJECTMEETING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure("0" + NAME_DESC_PROJECTMEETING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure("1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure("1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure("1" + INVALID_NAME_DESC, TaskName.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure("1" + INVALID_STARTDATETIME_DESC, DateTime.MESSAGE_DATE_CONSTRAINT); // invalid startDateTime
        assertParseFailure("1" + INVALID_ENDDATETIME_DESC, DateTime.MESSAGE_DATE_CONSTRAINT); // invalid endDateTime
        assertParseFailure("1" + INVALID_NEWTAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag
        assertParseFailure("1" + INVALID_REMOVETAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag
        assertParseFailure("1" + INVALID_DESCRIPTION_DESC,
                TaskDescription.MESSAGE_NAME_CONSTRAINTS); // invalid description

        // invalid DateTime followed by valid tags
        assertParseFailure("1" + INVALID_STARTDATETIME_DESC + NEWTAG_DESC_HIGH, DateTime.MESSAGE_DATE_CONSTRAINT);

        // valid DateTime followed by invalid DateTime. The test case for invalid DateTime followed by valid DateTime
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure("1" + STARTDATETIME_DESC_PROJECTMEETING + INVALID_STARTDATETIME_DESC,
                DateTime.MESSAGE_DATE_CONSTRAINT);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure("1" + INVALID_NAME_DESC + INVALID_STARTDATETIME_DESC + NEWTAG_DESC_LOW,
                TaskName.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() throws Exception {
        Index targetIndex = INDEX_SECOND_TASK;

        String userInput = targetIndex.getOneBased() + NAME_DESC_PROJECTMEETING + STARTDATETIME_DESC_PROJECTMEETING
                + ENDDATETIME_DESC_PROJECTMEETING + NEWTAG_DESC_CS2103 + NEWTAG_DESC_MEDIUM
                + REMOVETAG_DESC_CS2010 + DESCRIPTION_DESC_PROJECTMEETING;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECTMEETING)
                .withStartDateTime(VALID_STARTDATETIME_PROJECTMEETING).withEndDateTime(VALID_ENDDATETIME_PROJECTMEETING)
                .withNewTags(VALID_TAG_MEDIUM, VALID_TAG_CS2103).withRemoveTags(VALID_TAG_CS2010)
                .withDescription(VALID_DESCRIPTION_PROJECTMEETING).build();

        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws Exception {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased()
                           + NEWTAG_DESC_CS2103
                           + REMOVETAG_DESC_LOW
                           + DESCRIPTION_DESC_PROJECTMEETING;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withNewTags(VALID_TAG_CS2103)
                .withRemoveTags(VALID_TAG_LOW).withDescription(VALID_DESCRIPTION_PROJECTMEETING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws Exception {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_PROJECTDEMO;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECTDEMO).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(userInput, expectedCommand);

        // startDateTime
        userInput = targetIndex.getOneBased() + STARTDATETIME_DESC_PROJECTMEETING;
        descriptor = new EditTaskDescriptorBuilder().withStartDateTime(VALID_STARTDATETIME_PROJECTMEETING).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(userInput, expectedCommand);

        // endDateTime
        userInput = targetIndex.getOneBased() + ENDDATETIME_DESC_PROJECTDEMO;
        descriptor = new EditTaskDescriptorBuilder().withEndDateTime(VALID_ENDDATETIME_PROJECTDEMO).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_PROJECTDEMO;
        descriptor = new EditTaskDescriptorBuilder().withDescription(VALID_DESCRIPTION_PROJECTDEMO).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(userInput, expectedCommand);

        // newtags
        userInput = targetIndex.getOneBased() + NEWTAG_DESC_CS2010;
        descriptor = new EditTaskDescriptorBuilder().withNewTags(VALID_TAG_CS2010).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(userInput, expectedCommand);

        // removetags
        userInput = targetIndex.getOneBased() + REMOVETAG_DESC_CS2103 + REMOVETAG_DESC_MEDIUM;
        descriptor = new EditTaskDescriptorBuilder().withRemoveTags(VALID_TAG_CS2103, VALID_TAG_MEDIUM).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws Exception {
        Index targetIndex = INDEX_FIRST_TASK;

        String userInput = targetIndex.getOneBased() + NAME_DESC_READBORNACRIME + DESCRIPTION_DESC_READBORNACRIME
                + NAME_DESC_PROJECTDEMO + ENDDATETIME_DESC_PROJECTDEMO + DESCRIPTION_DESC_PROJECTDEMO
                + NAME_DESC_PROJECTMEETING + ENDDATETIME_DESC_PROJECTMEETING + DESCRIPTION_DESC_PROJECTMEETING;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_PROJECTMEETING)
                .withEndDateTime(VALID_ENDDATETIME_PROJECTMEETING).withDescription(VALID_DESCRIPTION_PROJECTMEETING)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws Exception {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_ENDDATETIME_DESC + ENDDATETIME_DESC_PROJECTMEETING;
        EditTaskDescriptor descriptor =
                new EditTaskDescriptorBuilder().withEndDateTime(VALID_ENDDATETIME_PROJECTMEETING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased()
                + NAME_DESC_PROJECTDEMO
                + INVALID_DESCRIPTION_DESC
                + ENDDATETIME_DESC_PROJECTDEMO
                + DESCRIPTION_DESC_PROJECTDEMO;
        descriptor = new EditTaskDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_PROJECTDEMO).withEndDateTime(VALID_ENDDATETIME_PROJECTDEMO)
                .withName(VALID_NAME_PROJECTDEMO).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(userInput, expectedCommand);
    }

    @Test
    public void parse_multiplePriorityTags_acceptsFirst() throws Exception {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + NEWTAG_DESC_HIGH + NEWTAG_DESC_MEDIUM + NEWTAG_DESC_LOW;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withNewTags(VALID_TAG_HIGH).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(userInput, expectedCommand);
    }

    /**
     * Asserts the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}
     */
    private void assertParseFailure(String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            fail("An exception should have been thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }

    /**
     * Asserts the parsing of {@code userInput} is successful and the result matches {@code expectedCommand}
     */
    private void assertParseSuccess(String userInput, EditCommand expectedCommand) throws Exception {
        Command command = parser.parse(userInput);
        assert expectedCommand.equals(command);
    }
}
