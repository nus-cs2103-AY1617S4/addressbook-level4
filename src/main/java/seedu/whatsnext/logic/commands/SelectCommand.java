package seedu.whatsnext.logic.commands;

import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.core.Messages;
import seedu.whatsnext.commons.core.UnmodifiableObservableList;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.events.ui.JumpToListRequestEvent;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    public final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        UnmodifiableObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

}
