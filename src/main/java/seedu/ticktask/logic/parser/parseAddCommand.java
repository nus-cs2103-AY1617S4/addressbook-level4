package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.Date;
import seedu.ticktask.model.task.Email;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.Time;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class parseAddCommand {
	
	private static final Pattern ADD_COMMAND_FORMAT = Pattern.compile("(?<name>(.(?!by|from|#|at))+)" //.(?!by)+ will keep matching until by.
            + "(?=.*(by|from)\\s(?<dates>(.(?!.*'|#|at))+)?)?"
            + "(?=.*(at)\\s(?<time>(.(?!.*'|#))+)?)?"
            + "((?=.*#(?<tags>.+)))?"
            + ".*");
	
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
    	Matcher matcher = ADD_COMMAND_FORMAT.matcher(args.trim());
    	
    	if (!matcher.matches()) {
    		throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    	}
        try {
        	
        	Optional<String> parsename = Optional.ofNullable(matcher.group("name"));
        	Optional<String> parsedate = Optional.ofNullable(matcher.group("dates"));
        	Optional<String> parsetime = Optional.ofNullable(matcher.group("time"));
        	
            Name name = ParserUtil.parseName(parsename).get();
            Time time = ParserUtil.parseTime(parsetime).get();
            Email email = ParserUtil.parseEmail(Optional.of(" ")).get();
            Date date = ParserUtil.parseDate(parsedate).get();
            HashSet<String> parsetag = new HashSet<String>();
            Set<Tag> tagList = ParserUtil.parseTags(parsetag);

            ReadOnlyTask task = new Task(name, time, email, date, tagList);

            return new AddCommand(task);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}