package seedu.ticktask.logic.parser;

import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_COMPLETE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_ACTIVE;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.commands.DeleteFindCommand;
import seedu.ticktask.logic.commands.DeleteIndexCommand;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.logic.parser.exceptions.ParseException;


//@@author A0131884B
/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser {

    private ArgumentMultimap argMultimap;

        public ArgumentMultimap getArgMultimapDelete() {
                return argMultimap;
            }

    /**
    * Parses the given {@code String} of arguments in the context of the DeleteCommand and returns an
    * DeleteCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public DeleteCommand parse(String args) throws ParseException {
                argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_COMPLETE, PREFIX_ACTIVE);
        if (args.trim().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));
         }

        /**
        * delete by index
        */
        if (hasIndexFlag(argMultimap)) {
              try {
                  Prefix listIndicatorPrefix = ParserUtil.getListPrefix(argMultimap,  PREFIX_COMPLETE, PREFIX_ACTIVE);
                  Index index = ParserUtil.parseIndex(argMultimap.getValue(listIndicatorPrefix).get());
                  return new DeleteIndexCommand(index, listIndicatorPrefix);
              } catch (IllegalValueException ive) {
                  throw new ParseException(ive.getMessage(), ive);
              }
        /**
        * delete by task name
        */
        } else {
              String trimmedArgs = argMultimap.getPreamble();
              final String[] keywords = new String[]{""};
              keywords [0] = trimmedArgs;
              final Set<String> keywordSet = new HashSet<>(Arrays.asList(keywords));
              return new DeleteFindCommand(keywordSet);
        }
    }

       /**
          * A method that returns true if flags in given ArgumentMultimap has at least one index-indicating
          * Prefix mapped to some arguments.
          * Index-indicating : /active or /complete
          */
    private boolean hasIndexFlag(ArgumentMultimap argMultimap) {
        assert argMultimap != null;
        return ParserUtil.isPrefixPresent(argMultimap,  PREFIX_COMPLETE, PREFIX_ACTIVE);
    }
}

 //@@author


