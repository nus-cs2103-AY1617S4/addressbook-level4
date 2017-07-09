package seedu.whatsnext.logic.parser;

import java.io.File;

import seedu.whatsnext.logic.commands.ChangePathCommand;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.WrongCommand;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses the given {@code String} of arguments
 * ChangePathCommand and returns an ChangeFilePathCommand object
 * for execution.
 */

//@@author A0149894H
public class ChangePathCommandParser {

    public Command parse(String args){

        String stringNewFilePath = args.trim();
        File targetLocation = new File(args.trim());
        String emptyString = "";

        if (emptyString.equals(args)) {
            return new WrongCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePathCommand.MESSAGE_USAGE));
        }
        if (!targetLocation.exists()) {
            targetLocation.mkdir();
        }
        if (!(stringNewFilePath.substring(stringNewFilePath.length() - 1).equals("/"))){
            stringNewFilePath += "/";
        }

        stringNewFilePath += "whatsnext.xml";

        File newFilePath = new File(stringNewFilePath.trim());

        return new ChangePathCommand(newFilePath);
    }
}
