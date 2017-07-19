package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;

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
 * Parses input arguments and creates a new DeleteCommand object
 */
public class ClearCommandParser {

    private ArgumentMultimap argMultimap;

        public ArgumentMultimap getArgMultimapDelete() {
                return argMultimap;
            }
        /**
         * Parses the given {@code String} of arguments in the context of the DeleteCommand and returns an
         * DeleteCommand object for execution.
         * @throws ParseException if the user input does not conform the expected format
         */
        
        public ClearCommand parse(String args) throws ParseException, IllegalValueException {
            argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COMPLETE, PREFIX_ACTIVE);
            if (args.trim().isEmpty()) {
            	throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            			ClearCommand.MESSAGE_USAGE));
            }
            
            if (haveInvalidPrefixCombination(argMultimap)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
        	} 
            Prefix listIndicatorPrefix = ParserUtil.getListPrefix(argMultimap,  PREFIX_COMPLETE, PREFIX_ACTIVE);
			return new ClearCommand(listIndicatorPrefix);
        }   
            
        private boolean haveInvalidPrefixCombination(ArgumentMultimap argMultimap) {
            assert argMultimap != null;
            return ParserUtil.areAllPrefixesPresent(argMultimap, PREFIX_ACTIVE, PREFIX_COMPLETE);                  
        }
}
