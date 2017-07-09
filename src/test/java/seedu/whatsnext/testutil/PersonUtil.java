//package seedu.whatsnext.testutil;
//
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TIME;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_DATE;
//import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG;
//
//import seedu.whatsnext.logic.commands.AddCommand;
//import seedu.whatsnext.model.task.Person;
//
///**
// * A utility class for Person.
// */
//public class PersonUtil {
//
//    /**
//     * Returns an add command string for adding the {@code person}.
//     */
//    public static String getAddCommand(Person person) {
//        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
//    }
//
//    /**
//     * Returns the part of command string for the given {@code person}'s details.
//     */
//    private static String getPersonDetails(Person person) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(PREFIX_NAME + person.getName().fullName + " ");
//        sb.append(PREFIX_DATE + person.getPhone().value + " ");
//        sb.append(PREFIX_TIME + person.getEmail().value + " ");
//        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
//        person.getTags().stream().forEach(
//            s -> sb.append(PREFIX_TAG + s.tagName + " ")
//        );
//        return sb.toString();
//    }
//}
