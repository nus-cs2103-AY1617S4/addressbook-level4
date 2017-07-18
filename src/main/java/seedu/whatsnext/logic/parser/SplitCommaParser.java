package seedu.whatsnext.logic.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.tag.Tag;

//@@author A0156106M
/**
 * Tokenizes arguments string of the form:
 * {@code taskName, taskDescription, taskStartDate, taskEndDate, tags: taskTags}
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *    in the above example.<br>
 *
 * */

public class SplitCommaParser {
    private static final String TASK_NAME = "name";
    private static final String TASK_DESCRIPTION = "description";
    private static final String TASK_DATETIME = "end";
    private static final String TASK_TAG = "tag";

    private HashMap<String, List<String>> parserMap = new HashMap<>();
    private ArrayList<String> nameList = new ArrayList<>();
    private ArrayList<String> descriptionList = new ArrayList<>();
    private ArrayList<String> dateTimeList = new ArrayList<>();
    private ArrayList<String> tagList = new ArrayList<>();

    /**
     * Splits the argument by comma "," and maps each tokenized argument by their attributes
     * @param: inputValue Arguments string of the form
     * {@code taskName, taskDescription, taskStartDate, taskEndDate, tags: taskTags}
     * @return {@code HashMap<String, List<String>> object}
     * */
    public HashMap<String, List<String>> tokenize(String inputValue) {

        String[] splitInputValue = inputValue.split(",");
        nameList.add(splitInputValue[0]);
        parserMap.put(TASK_NAME, nameList);

        for (int i = 1; i < splitInputValue.length; i++) {
            String value = splitInputValue[i];
            if (isValidDescription(value)) {
                descriptionList.add(value);
            } else if (isValidDateTime(value)) {
                dateTimeList.add(value);
            } else if (isValidTag(value)) {
                tagList = getTags(value);
            }
        }
        parserMap.put(TASK_DESCRIPTION, descriptionList);
        parserMap.put(TASK_DATETIME, dateTimeList);
        parserMap.put(TASK_TAG, tagList);

        return parserMap;
    }

    /**
     * Returns first taskName recorded
     * */
    public String getTaskName() {
        return parserMap.get(TASK_NAME).get(0);
    }

    /**
     * Returns first taskDescription recorded
     * */
    public Optional<String> getDescription() {
        if (parserMap.get(TASK_DESCRIPTION).isEmpty()) {
            return Optional.empty();
        }
        String descriptionValue = parserMap.get(TASK_DESCRIPTION).get(0);
        descriptionValue = descriptionValue.trim();
        descriptionValue = descriptionValue.substring(1);
        descriptionValue = descriptionValue.substring(0, descriptionValue.length() - 1);
        return Optional.of(descriptionValue);
    }

    /**
     * Returns first task DateTime recorded
     * */
    public Optional<String> getStartDateTime() {
        if (parserMap.get(TASK_DATETIME).isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(parserMap.get(TASK_DATETIME).get(0));
    }

    /**
     * Returns second task DateTime recorded
     * */
    public Optional<String> getEndDateTime() {
        if (parserMap.get(TASK_DATETIME).size() > 1) {
            return Optional.of(parserMap.get(TASK_DATETIME).get(1));
        }
        return Optional.empty();
    }

    /**
     * Checks if argument is a valid taskDescription argument
     * descriptions are enclosed in ""
     * @return true if arguments starts and ends with open inverted commas
     * */
    private boolean isValidDescription(String args) {
        String inputValue = args.trim();
        return inputValue.startsWith("\"") && inputValue.endsWith("\"");
    }

    /**
     * Checks if argument is valid Date Time argument
     * */
    private boolean isValidDateTime(String args) {
        List<Date> dateInputList = new PrettyTimeParser().parse(args);
        if (dateInputList.isEmpty()) {
            return false;
        }
        return true;
    }


    Set<Tag> parseTags() throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagList) {
            tagName = tagName.trim();
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    /**
     * Checks if argument is a valid taskDescription argument
     * @return true if argument starts with "tags:"
     * */
    private boolean isValidTag(String args) {
        String inputValue = args.trim();
        if (inputValue.length() < 5) {
            return false;
        }
        String tagKey = inputValue.substring(0, 5);
        if (tagKey.equals("tags:")) {
            return true;
        }
        return false;
    }

    /**
     * @param Valid tag argument
     * @return ArrayList of possible tag names
     * */
    private ArrayList<String> getTags(String args) {
        String inputValue = args.substring(6);
        ArrayList<String> splitTagList = new ArrayList<>();
        for (String value : inputValue.split(" ")) {
            if (!value.isEmpty()) {
                splitTagList.add(value.trim());
            }
        }
        return splitTagList;
    }

}
