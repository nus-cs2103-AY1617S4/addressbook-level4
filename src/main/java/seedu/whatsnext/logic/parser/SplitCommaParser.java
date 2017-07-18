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

public class SplitCommaParser {
    public static String TASK_NAME = "name";
    public static String TASK_DESCRIPTION = "description";
    public static String TASK_START_DATE = "start";
    public static String TASK_END_DATE = "end";
    public static String TASK_DATETIME = "end";
    public static String TASK_TAG = "tag";

    HashMap<String, List<String>> parserMap = new HashMap<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> descriptionList = new ArrayList<>();
    ArrayList<String> dateTimeList = new ArrayList<>();
    ArrayList<String> tagList = new ArrayList<>();

    public HashMap<String, List<String>> tokenize(String inputValue){

        String[] splitInputValue = inputValue.split(",");

        nameList.add(splitInputValue[0]);
        parserMap.put(TASK_NAME, nameList);

        for (String value : splitInputValue) {
            if (isValidDescription(value)){
                descriptionList.add(value);
            } else if (isValidDateTime(value)) {
                System.out.println("DATE" + value);
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

    public String getTaskName() {
        return parserMap.get(TASK_NAME).get(0);
    }

    public Optional<String> getDescription() {
        if (parserMap.get(TASK_DESCRIPTION).isEmpty())
            return Optional.empty();

        return Optional.of(parserMap.get(TASK_DESCRIPTION).get(0));
    }

    public Optional<String> getStartDateTime() {
        if (parserMap.get(TASK_DATETIME).isEmpty())
            return Optional.empty();

        return Optional.of(parserMap.get(TASK_DATETIME).get(0));
    }

    public Optional<String> getEndDateTime() {
        if (parserMap.get(TASK_DATETIME).size() > 1) {
            return Optional.of(parserMap.get(TASK_DATETIME).get(1));
        }
        return Optional.empty();
    }

    private boolean isValidDescription(String args) {
        String inputValue = args.trim();
        return inputValue.startsWith("\"") && inputValue.endsWith("\"");
    }

    private boolean isValidDateTime(String args){
        List<Date> dateInputList = new PrettyTimeParser().parse(args);
        if (dateInputList.isEmpty()) {
            return false;
        }
        return true;
    }

    Set<Tag> parseTags() throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for(String tagName : tagList){
            tagName = tagName.trim();
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    private boolean isValidTag(String args){
        String inputValue = args.trim();
        if (inputValue.length() < 5) {
            return false;
        }
        String tagKey = inputValue.substring(0,5);
        if(tagKey.equals("tags:")) {
            return true;
        }
        return false;
    }

    private ArrayList<String> getTags(String args) {
        String inputValue = args.substring(6);
        ArrayList<String> splitTagList = new ArrayList<>();
        for(String value : inputValue.split(" ")){
            if (!value.isEmpty()){
                splitTagList.add(value.trim());
            }
        }
        return splitTagList;
    }



}
