package seedu.whatsnext.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.EditCommand;
import seedu.whatsnext.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.whatsnext.logic.parser.exceptions.ParseException;
import seedu.whatsnext.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    //@@author A0142675B
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                                           PREFIX_START_DATETIME,
                                           PREFIX_END_DATETIME,
                                           PREFIX_NEW_TAG,
                                           PREFIX_DELETE_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        try {
            ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME))
                                                                .ifPresent(editTaskDescriptor::setName);

            ParserUtil.parseStartDateTime(argMultimap.getValue(PREFIX_START_DATETIME))
                                                                .ifPresent(editTaskDescriptor::setStartDateTime);

            ParserUtil.parseEndDateTime(argMultimap.getValue(PREFIX_END_DATETIME))
                                                                .ifPresent(editTaskDescriptor::setEndDateTime);

            parseTagsForEdit(argMultimap.getAllValues(PREFIX_NEW_TAG))
                                                                .ifPresent(editTaskDescriptor::addTags);

            parseTagsForEdit(argMultimap.getAllValues(PREFIX_DELETE_TAG))
                                                                .ifPresent(editTaskDescriptor::setRemoveTags);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
