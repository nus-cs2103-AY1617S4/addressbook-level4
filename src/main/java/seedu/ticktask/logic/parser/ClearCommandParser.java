package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ALL;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.ticktask.logic.parser.ArgumentMultimap;
import seedu.ticktask.logic.parser.ParserUtil;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.logic.commands.DeleteIndexCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;

//@@author A0131884B
/**
 * Parses input arguments and creates a new Clear Command object
 */
public class ClearCommandParser {

    private ArgumentMultimap argMultimap;

        public ArgumentMultimap getArgMultimapClear() {
                return argMultimap;
            }
        
        /**
         * Parses the given {@code String} of arguments in the context of the ClearCommand and returns an
         * ClearCommand object for execution.
         * @param args containing keywords by user indicating list to clear: 'clear all', 'clear complete', or 'clear active'
         * @throws ParseException if the user input does not conform the expected format
         */
        public ClearCommand parse(String args) throws ParseException, IllegalValueException {
            argMultimap = ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_COMPLETE, CliSyntax.PREFIX_ACTIVE, CliSyntax.PREFIX_ALL);

            if (!isValid(args.trim())){
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ClearCommand.MESSAGE_USAGE));
            }

            Prefix listIndicatorPrefix = ParserUtil.getListPrefix(argMultimap,  CliSyntax.PREFIX_COMPLETE, CliSyntax.PREFIX_ACTIVE, CliSyntax.PREFIX_ALL);
			return new ClearCommand(listIndicatorPrefix);

        }   
            

        /**
         * @param listType string representing the list the user intends to clear
         * @return true only if the listtpye string after the "clear" keyword is: "all", "active" or "complete".
         */
        private boolean isValid(String listType) {
            if(listType.equals(CliSyntax.PREFIX_ALL.toString()) || listType.equals(CliSyntax.PREFIX_ACTIVE.toString()) || listType.equals(CliSyntax.PREFIX_COMPLETE.toString())){
                return true;
            }
            return false;

        }
}
