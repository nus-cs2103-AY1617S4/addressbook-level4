//package seedu.whatsnext.logic.parser;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TIME;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DATE;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_ADDRESS_AMY;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_EMAIL_AMY;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_EMAIL_BOB;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_NAME_AMY;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_DATE_AMY;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_DATE_BOB;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_FRIEND;
//import static seedu.whatsnext.testutil.EditCommandTestUtil.VALID_TAG_HUSBAND;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_FIRST_PERSON;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_SECOND_PERSON;
//import static seedu.whatsnext.testutil.TypicalPersons.INDEX_THIRD_PERSON;
//
//import org.junit.Test;
//
//import seedu.whatsnext.commons.core.index.Index;
//import seedu.whatsnext.logic.commands.Command;
//import seedu.whatsnext.logic.commands.EditCommand;
//import seedu.whatsnext.logic.commands.EditCommand.EditPersonDescriptor;
//import seedu.whatsnext.logic.parser.exceptions.ParseException;
//import seedu.whatsnext.model.person.Address;
//import seedu.whatsnext.model.person.Email;
//import seedu.whatsnext.model.person.Name;
//import seedu.whatsnext.model.person.Phone;
//import seedu.whatsnext.model.tag.Tag;
//import seedu.whatsnext.testutil.EditPersonDescriptorBuilder;
//
//public class EditCommandParserTest {
//
//    private static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
//    private static final String DATE_DESC_AMY = " " + PREFIX_DATE + VALID_DATE_AMY;
//    private static final String DATE_DESC_BOB = " " + PREFIX_DATE + VALID_DATE_BOB;
//    private static final String EMAIL_DESC_AMY = " " + PREFIX_TIME + VALID_EMAIL_AMY;
//    private static final String EMAIL_DESC_BOB = " " + PREFIX_TIME + VALID_EMAIL_BOB;
//    private static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
//    private static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
//    private static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
//    private static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
//    private static final String TAG_EMPTY = " " + PREFIX_TAG;
//
//    private static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
//    private static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "911a"; // 'a' not allowed in phones
//    private static final String INVALID_EMAIL_DESC = " " + PREFIX_TIME + "bob!yahoo"; // missing '@' symbol
//    private static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
//    private static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
//
//    private static final String MESSAGE_INVALID_FORMAT =
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);
//
//    private EditCommandParser parser = new EditCommandParser();
//
//    @Test
//    public void parse_missingParts_failure() {
//        // no index specified
//        assertParseFailure(VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
//
//        // no field specified
//        assertParseFailure("1", EditCommand.MESSAGE_NOT_EDITED);
//
//        // no index and no field specified
//        assertParseFailure("", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidPreamble_failure() {
//        // negative index
//        assertParseFailure("-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
//
//        // zero index
//        assertParseFailure("0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);
//
//        // invalid arguments being parsed as preamble
//        assertParseFailure("1 some random string", MESSAGE_INVALID_FORMAT);
//
//        // invalid prefix being parsed as preamble
//        assertParseFailure("1 i/ string", MESSAGE_INVALID_FORMAT);
//    }
//
//    @Test
//    public void parse_invalidValue_failure() {
//        assertParseFailure("1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
//        assertParseFailure("1" + INVALID_DATE_DESC, Phone.MESSAGE_DATE_CONSTRAINTS); // invalid phone
//        assertParseFailure("1" + INVALID_EMAIL_DESC, Email.MESSAGE_TIME_CONSTRAINTS); // invalid email
//        assertParseFailure("1" + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS); // invalid address
//        assertParseFailure("1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag
//
//        // invalid phone followed by valid email
//        assertParseFailure("1" + INVALID_DATE_DESC + EMAIL_DESC_AMY, Phone.MESSAGE_DATE_CONSTRAINTS);
//
//        // valid phone followed by invalid phone. The test case for invalid phone followed by valid phone
//        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
//        assertParseFailure("1" + DATE_DESC_BOB + INVALID_DATE_DESC, Phone.MESSAGE_DATE_CONSTRAINTS);
//
//        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
//        // parsing it together with a valid tag results in error
//        assertParseFailure("1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
//        assertParseFailure("1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
//        assertParseFailure("1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
//
//        // multiple invalid values, but only the first invalid value is captured
//        assertParseFailure("1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_AMY + VALID_DATE_AMY,
//                Name.MESSAGE_NAME_CONSTRAINTS);
//    }
//
//    @Test
//    public void parse_allFieldsSpecified_success() throws Exception {
//        Index targetIndex = INDEX_SECOND_PERSON;
//        String userInput = targetIndex.getOneBased() + DATE_DESC_BOB + TAG_DESC_HUSBAND
//                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;
//
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
//                .withPhone(VALID_DATE_BOB).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
//                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_someFieldsSpecified_success() throws Exception {
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = targetIndex.getOneBased() + DATE_DESC_BOB + EMAIL_DESC_AMY;
//
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_DATE_BOB)
//                .withEmail(VALID_EMAIL_AMY).build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_oneFieldSpecified_success() throws Exception {
//        // name
//        Index targetIndex = INDEX_THIRD_PERSON;
//        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(userInput, expectedCommand);
//
//        // phone
//        userInput = targetIndex.getOneBased() + DATE_DESC_AMY;
//        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_DATE_AMY).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(userInput, expectedCommand);
//
//        // email
//        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
//        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(userInput, expectedCommand);
//
//        // address
//        userInput = targetIndex.getOneBased() + ADDRESS_DESC_AMY;
//        descriptor = new EditPersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(userInput, expectedCommand);
//
//        // tags
//        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
//        descriptor = new EditPersonDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_multipleRepeatedFields_acceptsLast() throws Exception {
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = targetIndex.getOneBased()  + DATE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY
//                + TAG_DESC_FRIEND + DATE_DESC_AMY + ADDRESS_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
//                + DATE_DESC_BOB + ADDRESS_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_HUSBAND;
//
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_DATE_BOB)
//                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
//                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
//                .build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_invalidValueFollowedByValidValue_success() throws Exception {
//        // no other valid values specified
//        Index targetIndex = INDEX_FIRST_PERSON;
//        String userInput = targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_BOB;
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_DATE_BOB).build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(userInput, expectedCommand);
//
//        // other valid values specified
//        userInput = targetIndex.getOneBased() + EMAIL_DESC_BOB + INVALID_DATE_DESC + ADDRESS_DESC_BOB
//                + DATE_DESC_BOB;
//        descriptor = new EditPersonDescriptorBuilder().withPhone(VALID_DATE_BOB).withEmail(VALID_EMAIL_BOB)
//                .withAddress(VALID_ADDRESS_BOB).build();
//        expectedCommand = new EditCommand(targetIndex, descriptor);
//        assertParseSuccess(userInput, expectedCommand);
//    }
//
//    @Test
//    public void parse_resetTags_success() throws Exception {
//        Index targetIndex = INDEX_THIRD_PERSON;
//        String userInput = targetIndex.getOneBased() + TAG_EMPTY;
//
//        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
//        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
//
//        assertParseSuccess(userInput, expectedCommand);
//    }
//
//    /**
//     * Asserts the parsing of {@code userInput} is unsuccessful and the error message
//     * equals to {@code expectedMessage}
//     */
//    private void assertParseFailure(String userInput, String expectedMessage) {
//        try {
//            parser.parse(userInput);
//            fail("An exception should have been thrown.");
//        } catch (ParseException pe) {
//            assertEquals(expectedMessage, pe.getMessage());
//        }
//    }
//
//    /**
//     * Asserts the parsing of {@code userInput} is successful and the result matches {@code expectedCommand}
//     */
//    private void assertParseSuccess(String userInput, EditCommand expectedCommand) throws Exception {
//        Command command = parser.parse(userInput);
//        assert expectedCommand.equals(command);
//    }
//}
