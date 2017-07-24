package seedu.ticktask.logic.parser;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.EditCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.tag.Tag;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@@author A0139964M
/**
 * Parses input arguments and creates a new EditCommand object
 */
public class ParseEditCommand {
	/**
	 * Parses the given {@code String} of arguments in the context of the EditCommand
	 * and returns an EditCommand object for execution.
	 * @throws ParseException if the user input does not conform the expected format
	 */
	public static final String COMMAND_ARGUMENTS_REGEX = "(?=(?<index>\\d+))"
			                                           + "(?:(?=.*name (?:(?<name>.+?)(?:,|$|\\R|date|time))?))?"
			                                           + "(?:(?=.*type (?:(?<type>.+?)(?:,|$|\\R|date|time))?))?"
			                                           + "(?:(?=.*date(?:(?<date>.+?)(?:,|$|\\R|time))?))?"
			                                           + "(?:(?=.*time(?:(?<time>.+?)(?:,|$|\\R|date))?))?"
			                                           + "((?=.*#(?<tags>.+)))?"
			                                           + ".+";
	public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);
	private Set<Tag> tagList;
	
	public EditCommand parse(String args) throws IllegalValueException {
		Matcher matcher = COMMAND_ARGUMENTS_PATTERN.matcher(args.trim());
		if (!matcher.matches()) {
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
		int index = Integer.parseInt(matcher.group("index"));
		
		Optional<String> name = Optional.ofNullable(matcher.group("name"));
		Optional<String> time = Optional.ofNullable(matcher.group("time"));
		Optional<String> date = Optional.ofNullable(matcher.group("date"));
		Optional<String> tags = Optional.ofNullable(matcher.group("tags"));
		Optional<String> type = Optional.ofNullable(matcher.group("type"));
		
		if(!name.isPresent() && !time.isPresent() && !date.isPresent() && !tags.isPresent() && !type.isPresent()){
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
		EditCommand.EditTaskDescriptor editTaskDescriptor = new EditCommand.EditTaskDescriptor();
		try {
			ParserUtil.parseName(name).ifPresent(editTaskDescriptor::setName);
			ParserUtil.parseTaskType(type).ifPresent(editTaskDescriptor::setTaskType);
			ParserUtil.parseTime(time).ifPresent(editTaskDescriptor::setTime);
			ParserUtil.parseDate(date).ifPresent(editTaskDescriptor::setDate);
			tagList = createTagList(tags);
			editTaskDescriptor.setTags(tagList);
		} catch (IllegalValueException ive) {
			throw new ParseException(ive.getMessage(), ive);
		}
		try {
			Index index1 = Index.fromOneBased(index);
			return new EditCommand(index1, editTaskDescriptor);
		} catch (IndexOutOfBoundsException iobe){
			throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
		}
	}
	
	/**
	 *Parse tag into a set<tag>. If parseTag is empty, returns an empty Set
	 * @throws IllegalValueException
	 */
	public Set<Tag> createTagList(Optional<String> parseTag) throws IllegalValueException {
		if (parseTag.isPresent()) {
			String[] stringArray = parseTag.get().split(" ");
			Collection<String> tagCollection = Arrays.asList(stringArray);
			HashSet<String> tagsList = new HashSet<>(tagCollection);
			tagList = ParserUtil.parseTags(tagsList);
		}
		return tagList;
	}
}
//@@author
