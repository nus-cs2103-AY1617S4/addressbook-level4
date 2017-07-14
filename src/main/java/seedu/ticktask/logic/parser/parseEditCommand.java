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
public class parseEditCommand {

    public static final String COMMAND_ARGUMENTS_REGEX = "(?=(?<index>\\d+))"
            + "(?:(?=.*name (?:(?<name>.+?)(?:,|$|\\R|start|end))?))?"
            + "(?:(?=.*date(?:(?<date>.+?)(?:,|$|\\R|start|end))?))?"
            + "(?:(?=.*start date(?:(?<startDate>.+?)(?:,|$|\\R|end))?))?"
            + "(?:(?=.*end date(?:(?<endDate>.+?)(?:,|$|\\R))?))?"
            + "(?:(?=.*time(?:(?<time>.+?)(?:,|$|\\R|start|end))?))?"
            + "(?:(?=.*start time(?:(?<startTime>.+?)(?:,|$|\\R|end))?))?"
            + "(?:(?=.*end time(?:(?<endTime>.+?)(?:,|$|\\R|start|end))?))?"
            + "((?=.*#(?<tags>.+)))?"
            + ".+";

    public static final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);

    private Set<Tag> tagList;

    public EditCommand parse(String args) throws IllegalValueException {
        Matcher matcher = COMMAND_ARGUMENTS_PATTERN.matcher(args.trim());

        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        // mandatory
        final int index = Integer.parseInt(matcher.group("index"));
        //optional
        Optional<String> name = Optional.ofNullable(matcher.group("name"));
        Optional<String> time = Optional.ofNullable(matcher.group("time"));
        Optional<String> startTime = Optional.ofNullable(matcher.group("startTime"));
        Optional<String> endTime = Optional.ofNullable(matcher.group("endTime"));
        Optional<String> date = Optional.ofNullable(matcher.group("date"));
        Optional<String> startDate = Optional.ofNullable(matcher.group("startDate"));
        Optional<String> endDate = Optional.ofNullable(matcher.group("endDate"));
        Optional<String> tags = Optional.ofNullable(matcher.group("tags"));


        //Optional<Set<String>> tagSet = Optional.empty();
        //if (tags.isPresent()) {
        //    tagSet = Optional.ofNullable(getTagsFromArgs(tags.get()));
        //}

        EditCommand.EditTaskDescriptor editTaskDescriptor = new EditCommand.EditTaskDescriptor();
        try {
            ParserUtil.parseName(name).ifPresent(editTaskDescriptor::setName);
    
            if (startTime.isPresent() || endDate.isPresent()) {
                if (startTime.isPresent()) {
                    startTime = Optional.of("start time " + startTime);
                    ParserUtil.parseTime(startTime).ifPresent(editTaskDescriptor::setTime);
                    System.out.println("Start time is edited to: " + startTime);
                }
                if (endTime.isPresent()) {
                    endTime = Optional.of("end time " + endTime);
                    ParserUtil.parseTime(endTime).ifPresent(editTaskDescriptor::setTime);
                    System.out.println("End time is edited to: " + endTime);
                }
            }
            if (time.isPresent()) {
                ParserUtil.parseTime(time).ifPresent(editTaskDescriptor::setTime);
            }
    
            if (startDate.isPresent() || endDate.isPresent()) {
                if (startDate.isPresent()) {
                    startDate = Optional.of("start date " + startDate);
                    ParserUtil.parseDate(startDate).ifPresent(editTaskDescriptor::setDate);
                    System.out.println("Start date is edited to: " + startDate);
                }
                if (endDate.isPresent()) {
                    endDate = Optional.of("end date " + endDate);
                    ParserUtil.parseDate(endDate).ifPresent(editTaskDescriptor::setDate);
                    System.out.println("End date is edited to: " + endDate);
                }
            }
            if (date.isPresent()) {
                ParserUtil.parseDate(date).ifPresent(editTaskDescriptor::setDate);
            }

            
            ParserUtil.parseTaskType(Optional.of(" ")).ifPresent(editTaskDescriptor::setTaskType);
            //ParserUtil.parseDate(startDate).ifPresent(editTaskDescriptor::setDate);
            tagList = createTagList(tags);
            editTaskDescriptor.setTags(tagList);


        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        Index index1 = Index.fromOneBased(index);
        return new EditCommand(index1, editTaskDescriptor);

    }

    public Set<Tag> createTagList(Optional<String> parsetag) throws IllegalValueException {
        if (parsetag.isPresent()) {
            String[] stringArray = parsetag.get().split(" ");
            Collection<String> tagCollection = Arrays.asList(stringArray);
            HashSet<String> tagsList = new HashSet<>(tagCollection);
            tagList = ParserUtil.parseTags(tagsList);
        }
        else {
            HashSet<String> emptyTagList = new HashSet<String>();
            tagList = ParserUtil.parseTags(emptyTagList);
        }
        return tagList;
    }
//@@author A0139964M
}
