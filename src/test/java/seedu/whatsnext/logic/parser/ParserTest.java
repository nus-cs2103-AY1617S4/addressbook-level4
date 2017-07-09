//package seedu.whatsnext.logic.parser;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//import static seedu.whatsnext.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
//
//import org.junit.Test;
//
//import seedu.whatsnext.logic.commands.HistoryCommand;
//import seedu.whatsnext.logic.parser.exceptions.ParseException;
//
//public class ParserTest {
//    private final Parser parser = new Parser();
//
//    @Test
//    public void parseCommand_history() throws Exception {
//        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
//        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
//
//        try {
//            parser.parseCommand("histories");
//            fail("The expected ParseException was not thrown.");
//        } catch (ParseException pe) {
//            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
//        }
//    }
//}
