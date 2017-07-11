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

public class parseEditCommand {
    Set<Tag> tagList;
    
    final String COMMAND_ARGUMENTS_REGEX = "(?=(?<index>\\d+))"
            + "(?:(?=.*name (?:(?<name>.+?)(?:,|$|\\R|start|end))?))?"
            + "(?:(?=.*time(?:(?<time>.+?)(?:,|$|\\R))?))?"
            + "(?:(?=.*start time(?:(?<startTime>.+?)(?:,|$|\\R))?))?"
            + "(?:(?=.*end time(?:(?<endTime>.+?)(?:,|$|\\R|start|end))?))?"
            + "(?:(?=.*date(?:(?<date>.+?)(?:,|$|\\R))?))?"
            + "(?:(?=.*start date(?:(?<startDate>.+?)(?:,|$|\\R))?))?"
            + "(?:(?=.*end date(?:(?<endDate>.+?)(?:,|$|\\R))?))?"
            + "(?:(?=.*tags to #(?:(?<tags>.+?)(?:\\s|,\\s|$|,$|\\R))?))?"
            + ".+";
    
    
    final Pattern COMMAND_ARGUMENTS_PATTERN = Pattern.compile(COMMAND_ARGUMENTS_REGEX);
    
    public EditCommand parse(String args) throws IllegalValueException {
        Matcher matcher = COMMAND_ARGUMENTS_PATTERN.matcher(args.trim());
        
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        // mandatory
        final int index = Integer.parseInt(matcher.group("index"));
        //optional
        final Optional<String> name = Optional.ofNullable(matcher.group("name"));
        final Optional<String> time = Optional.ofNullable(matcher.group("time"));
        Optional<String> startTime = Optional.ofNullable(matcher.group("startTime"));
        Optional<String> endTime = Optional.ofNullable(matcher.group("endTime"));
        final Optional<String> date = Optional.ofNullable(matcher.group("date"));
        Optional<String> startDate = Optional.ofNullable(matcher.group("startDate"));
        Optional<String> endDate = Optional.ofNullable(matcher.group("endDate"));
        final Optional<String> tags = Optional.ofNullable(matcher.group("tags"));
        
        
        //Optional<Set<String>> tagSet = Optional.empty();
        //if (tags.isPresent()) {
        //    tagSet = Optional.ofNullable(getTagsFromArgs(tags.get()));
        //}
        tagList = createTagList(tags);
    
        EditCommand.EditTaskDescriptor editTaskDescriptor = new EditCommand.EditTaskDescriptor();
        try {
            ParserUtil.parseName(name).ifPresent(editTaskDescriptor::setName);
            if(time.isPresent()) {
                ParserUtil.parseTime(time).ifPresent(editTaskDescriptor::setTime);
            }
            if(!time.isPresent() && (startTime.isPresent() || endDate.isPresent())){
                
                if(startTime.isPresent()){
                    startTime = Optional.of("start time " + startTime);
                    ParserUtil.parseTime(startTime).ifPresent(editTaskDescriptor::setTime);
                }
                if(endTime.isPresent()){
                    endTime = Optional.of("end time " + startTime);
                    ParserUtil.parseTime(endTime).ifPresent(editTaskDescriptor::setTime);
                }
            }
    
            if(date.isPresent()) {
                ParserUtil.parseDate(date).ifPresent(editTaskDescriptor::setDate);
            }
            if(!date.isPresent() && (date.isPresent() || endDate.isPresent())){
        
                if(startDate.isPresent()){
                    startDate = Optional.of("start date " + startDate);
                    ParserUtil.parseDate(startDate).ifPresent(editTaskDescriptor::setDate);
                }
                if(endDate.isPresent()){
                    endDate = Optional.of("end date " + endTime);
                    ParserUtil.parseDate(endDate).ifPresent(editTaskDescriptor::setDate);
                }
            }
            ParserUtil.parseEmail(Optional.of(" ")).ifPresent(editTaskDescriptor::setEmail);
            //ParserUtil.parseDate(startDate).ifPresent(editTaskDescriptor::setDate);

        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
        Index index1 = Index.fromOneBased(index);
        return new EditCommand(index1, editTaskDescriptor);
    
    }
    
    public Set<Tag> createTagList(Optional<String> parsetag) throws IllegalValueException {
        if(parsetag.isPresent()){
            String[] stringArray = parsetag.get().split(" ");
            Collection<String> tagCollection = Arrays.asList(stringArray);
            HashSet<String> tagsList= new HashSet<>(tagCollection);
            tagList = ParserUtil.parseTags(tagsList);
        }
        else {
            HashSet<String> emptyTagList = new HashSet<String>();
            tagList = ParserUtil.parseTags(emptyTagList);
        }
        return tagList;
    }
    
}