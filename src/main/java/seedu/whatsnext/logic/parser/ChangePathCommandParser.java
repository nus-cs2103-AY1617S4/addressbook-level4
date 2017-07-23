package seedu.whatsnext.logic.parser;

import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;

import seedu.whatsnext.logic.commands.ChangePathCommand;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.WrongCommand;



/**
 * Parses the given {@code String} of arguments
 * ChangePathCommandParser and returns an ChangePathCommand object
 * for execution.
 */

//@@author A0149894H
public class ChangePathCommandParser {

    public Command parse(String args) {

        String stringSaveLocation = args.trim();
        File targetLocation = new File(args.trim());
        String emptyString = "";

        if (emptyString.equals(args)) {
            return new WrongCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePathCommand.MESSAGE_USAGE));
        }
        if (!targetLocation.exists()) {
            targetLocation.mkdir();
        }
        if (!(stringSaveLocation.substring(stringSaveLocation.length() - 1).equals("/"))) {
            stringSaveLocation += "/";
        }

        stringSaveLocation += "whatsnext.xml";

        File newLocation = new File(stringSaveLocation.trim());

        return new ChangePathCommand(newLocation);
    }
}
