package seedu.ticktask.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ticktask.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.ticktask.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.ticktask.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.ticktask.model.util.SampleDataUtil.getTagSet;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_SECOND_TASK;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_THIRD_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.ticktask.commons.core.EventsCenter;
import seedu.ticktask.commons.core.index.Index;
import seedu.ticktask.commons.events.model.TickTaskChangedEvent;
import seedu.ticktask.commons.events.ui.JumpToListRequestEvent;
import seedu.ticktask.commons.events.ui.ShowHelpRequestEvent;
import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.logic.Logic;
import seedu.ticktask.logic.LogicManager;
import seedu.ticktask.logic.commands.AddCommand;
import seedu.ticktask.logic.commands.ClearCommand;
import seedu.ticktask.logic.commands.Command;
import seedu.ticktask.logic.commands.CommandResult;
import seedu.ticktask.logic.commands.DeleteCommand;
import seedu.ticktask.logic.commands.ExitCommand;
import seedu.ticktask.logic.commands.FindCommand;
import seedu.ticktask.logic.commands.HelpCommand;
import seedu.ticktask.logic.commands.HistoryCommand;
import seedu.ticktask.logic.commands.ListCommand;
import seedu.ticktask.logic.commands.SelectCommand;
import seedu.ticktask.logic.commands.exceptions.CommandException;
import seedu.ticktask.logic.commands.exceptions.WarningException;
import seedu.ticktask.logic.parser.exceptions.ParseException;
import seedu.ticktask.model.Model;
import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.ReadOnlyTickTask;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.UserPrefs;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.model.task.DueDate;
import seedu.ticktask.model.task.TaskType;
import seedu.ticktask.model.task.Name;
import seedu.ticktask.model.task.Task;
import seedu.ticktask.model.task.DueTime;
import seedu.ticktask.testutil.TaskBuilder;

//@@author A0138471A
public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTickTask latestSavedAddressBook;
    private boolean helpShown;
    private Index targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TickTaskChangedEvent abce) {
        latestSavedAddressBook = new TickTask(abce.data);
    }

    @Subscribe
    private void handleShowHelpRequestEvent(ShowHelpRequestEvent she) {
        helpShown = true;
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent je) {
        targetedJumpIndex = Index.fromZeroBased(je.targetIndex);
    }

    @Before
    public void setUp() {
        model = new ModelManager();
        logic = new LogicManager(model);
        EventsCenter.getInstance().registerHandler(this);

        latestSavedAddressBook = new TickTask(model.getTickTask()); // last saved assumed to be up to date
        helpShown = false;
        targetedJumpIndex = null;
    }

    @After
    public void tearDown() {
        EventsCenter.clearSubscribers();
    }

    @Test
    public void execute_invalid() {
        String invalidCommand = "       ";
        assertParseException(invalidCommand,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }

    /**
     * Executes the command, confirms that no exceptions are thrown and that the result message is correct.
     * Also confirms that {@code expectedModel} is as specified.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel) {
        assertCommandBehavior(null, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandBehavior(Class, String, String, Model)
     */
    private <T> void assertCommandFailure(String inputCommand, Class<T> expectedException, String expectedMessage) {
        Model expectedModel = new ModelManager(model.getTickTask(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s address book was saved to the storage file.
     */
    private <T> void assertCommandBehavior(Class<T> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | IllegalValueException | WarningException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
        assertEquals(expectedModel.getTickTask(), latestSavedAddressBook);
    }

    @Test
    public void execute_unknownCommandWord() {
        String unknownCommand = "uicfhmowqewca";
        assertParseException(unknownCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_help() {
        assertCommandSuccess(HelpCommand.COMMAND_WORD, HelpCommand.SHOWING_HELP_MESSAGE, new ModelManager());
        assertTrue(helpShown);
    }

    @Test
    public void execute_exit() {
        assertCommandSuccess(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, new ModelManager());
    }
  /*  
    @Test
    public void execute_exit_failure() {
        assertCommandFailure(ExitCommand.COMMAND_WORD, ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT, new ModelManager());
    }*/

    @Test
    public void execute_clear() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess(ClearCommand.COMMAND_WORD, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }


    @Test
    public void execute_add_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_UNKNOWN_COMMAND, AddCommand.MESSAGE_USAGE);
        assertParseException("addd test", expectedMessage);
    }

    @Test
    public void execute_add_invalidPersonData() {
        assertParseException(AddCommand.COMMAND_WORD + " " + "@^&*(()",
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void execute_add_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.test_study_task();
        Model expectedModel = new ModelManager();
        expectedModel.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedModel);

    }

    @Test
    public void execute_addDuplicate_notAllowed() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        Task toBeAdded = helper.test_study_task();

        // setup starting state
        model.addTask(toBeAdded); // task already in internal tickTask
        
        // execute command and verify result
        assertCommandException(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);

    }


    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Model expectedModel = new ModelManager(helper.generateTickTask(2), new UserPrefs());

        // prepare address book state
        helper.addToModel(model, 2);

        assertCommandSuccess(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }


    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list
     *                    based on visible index.
     */
    private void assertIncorrectIndexFormatBehaviorForCommand(String commandWord, String expectedMessage)
            throws Exception {
        assertParseException(commandWord , expectedMessage); //index missing
        assertParseException(commandWord + " +1", expectedMessage); //index should be unsigned
        assertParseException(commandWord + " -1", expectedMessage); //index should be unsigned
        assertParseException(commandWord + " 0", expectedMessage); //index cannot be 0
        assertParseException(commandWord + " not_a_number", expectedMessage);
    }

    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single person in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single person in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<Task> taskList = helper.generateTaskList(2);

        // set AB state to 2 persons
        model.resetData(new TickTask());
        for (Task p : taskList) {
            model.addTask(p);
        }

        assertCommandException(commandWord + " " + INDEX_THIRD_TASK.getOneBased(), expectedMessage);
    }

    @Test
    public void execute_selectInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand(SelectCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void execute_selectIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand(SelectCommand.COMMAND_WORD);
    }

    @Test
    public void execute_select_jumpsToCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        Model expectedModel = new ModelManager(helper.generateTickTask(threeTasks), new UserPrefs());
        helper.addToModel(model, threeTasks);

        assertCommandSuccess(SelectCommand.COMMAND_WORD + " 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedModel);
        assertEquals(INDEX_SECOND_TASK, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeTasks.get(1));
    }


    @Test
    public void execute_deleteInvalidArgsFormat_errorMessageShown() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        assertIncorrectIndexFormatBehaviorForCommand(DeleteCommand.COMMAND_WORD, expectedMessage);
    }
    

    @Test
    public void execute_deleteIndexNotFound_errorMessageShown() throws Exception {
        assertIndexNotFoundBehaviorForCommand(DeleteCommand.COMMAND_WORD);
    }

    @Test
    public void execute_delete_removesCorrectPerson() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<Task> threeTasks = helper.generateTaskList(3);

        Model expectedModel = new ModelManager(helper.generateTickTask(threeTasks), new UserPrefs());
        expectedModel.deleteIndexActiveTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess(DeleteCommand.COMMAND_WORD + " 2",
                String.format(DeleteCommand.MESSAGE_SUCCESS, threeTasks.get(1)), expectedModel);
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertParseException(FindCommand.COMMAND_WORD + " ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = new TaskBuilder().withName("bla bla KEY bla").build();
        Task pTarget2 = new TaskBuilder().withName("bla KEY bla bceofeia").build();
        Task p1 = new TaskBuilder().withName("KE Y").build();
        Task p2 = new TaskBuilder().withName("KEYKEYKEY sduauo").build();

        List<Task> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        Model expectedModel = new ModelManager(helper.generateTickTask(fourTasks), new UserPrefs());
        expectedModel.updateFilteredTaskList(new HashSet<>(Collections.singletonList("KEY")));
        helper.addToModel(model, fourTasks);
        assertCommandSuccess(FindCommand.COMMAND_WORD + " KEY",
                Command.getMessageForTaskListShownSummary(expectedModel.getFilteredTaskList().size()),
                expectedModel);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task p1 = new TaskBuilder().withName("bla bla KEY bla").build();
        Task p2 = new TaskBuilder().withName("bla KEY bla bceofeia").build();
        Task p3 = new TaskBuilder().withName("key key").build();
        Task p4 = new TaskBuilder().withName("KEy sduauo").build();

        List<Task> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        Model expectedModel = new ModelManager(helper.generateTickTask(fourTasks), new UserPrefs());
        helper.addToModel(model, fourTasks);

        assertCommandSuccess(FindCommand.COMMAND_WORD + " KEY",
                Command.getMessageForTaskListShownSummary(expectedModel.getFilteredTaskList().size()),
                expectedModel);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        Task pTarget1 = new TaskBuilder().withName("bla bla KEY bla").build();
        Task pTarget2 = new TaskBuilder().withName("bla rAnDoM bla bceofeia").build();
        Task pTarget3 = new TaskBuilder().withName("key key").build();
        Task p1 = new TaskBuilder().withName("sduauo").build();

        List<Task> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        Model expectedModel = new ModelManager(helper.generateTickTask(fourTasks), new UserPrefs());
        expectedModel.updateFilteredTaskList(new HashSet<>(Arrays.asList("key", "rAnDoM")));
        helper.addToModel(model, fourTasks);

        assertCommandSuccess(FindCommand.COMMAND_WORD + " key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedModel.getFilteredTaskList().size()),
                expectedModel);
    }

    @Test
    public void execute_verifyHistory_success() throws Exception {
        String validCommand = "clear";
        logic.execute(validCommand);

        String invalidCommandParse = "   adds   Bob   ";
        try {
            logic.execute(invalidCommandParse);
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }

        String invalidCommandExecute = "delete 1"; // TickTask is of size 0; index out of bounds
        try {
            logic.execute(invalidCommandExecute);
        } catch (CommandException ce) {
            assertEquals(MESSAGE_INVALID_TASK_DISPLAYED_INDEX, ce.getMessage());
        }

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", validCommand, invalidCommandParse, invalidCommandExecute));
        assertCommandSuccess("history", expectedMessage, model);
    }

    /**
     * A utility class to generate test data.
     */
    class TestDataHelper {

        Task test_study_task() throws Exception {
            Name name = new Name("Study CS2103");
            DueDate date = new DueDate("10/10/2017");
            DueTime time = new DueTime("12:30");
            TaskType taskType = new TaskType("deadline");

            return new Task(name, time, taskType, date,
                    getTagSet("tag1"));
        }

        /**
         * Generates a valid person using the given seed.
         * Running this function with the same parameter values guarantees the returned person will have the same state.
         * Each unique seed will generate a unique Person object.
         *
         * @param seed used to generate the person data field values
         */
        Task generateTask(int seed) throws Exception {
            // to ensure that phone numbers are at least 3 digits long, when seed is less than 3 digits
            String phoneNumber = String.join("", Collections.nCopies(3, String.valueOf(Math.abs(seed))));

            return new Task(
                    new Name("Person " + seed),
                    new DueTime("12:30"),
                    new TaskType("deadline"),
                    new DueDate("12/12/2017"),
                    getTagSet("tag" + Math.abs(seed), "tag" + Math.abs(seed + 1)));
        }

        /** Generates the correct add command based on the task given */
        String generateAddCommand(Task t) {
            StringBuffer cmd = new StringBuffer();

            cmd.append(AddCommand.COMMAND_WORD);

            cmd.append(" " + t.getName());
            cmd.append(" at " + t.getTime());
            cmd.append(" by " + t.getDate());

            Set<Tag> tags = t.getTags();
            for (Tag tag: tags) {
                cmd.append(" " + PREFIX_TAG.getPrefix().trim()).append(tag.tagName);
            }

            return cmd.toString();
        }

        /**
         * Generates an AddressBook with auto-generated persons.
         */
        TickTask generateTickTask(int numGenerated) throws Exception {
            TickTask tickTask = new TickTask();
            addToTickTask(tickTask, numGenerated);
            return tickTask;
        }

        /**
         * Generates an AddressBook based on the list of Persons given.
         */
        TickTask generateTickTask(List<Task> tasks) throws Exception {
            TickTask tickTask = new TickTask();
            addToTickTask(tickTask, tasks);
            return tickTask;
        }

        /**
         * Adds auto-generated Person objects to the given AddressBook
         * @param tickTask The AddressBook to which the Persons will be added
         */
        void addToTickTask(TickTask tickTask, int numGenerated) throws Exception {
            addToTickTask(tickTask, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given AddressBook
         */
        void addToTickTask(TickTask tickTask, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                tickTask.addTask(p);
            }
        }

        /**
         * Adds auto-generated Person objects to the given model
         * @param model The model to which the Persons will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Persons to the given model
         */
        void addToModel(Model model, List<Task> tasksToAdd) throws Exception {
            for (Task p: tasksToAdd) {
                model.addTask(p);
            }
        }

        /**
         * Generates a list of Persons based on the flags.
         */
        List<Task> generateTaskList(int numGenerated) throws Exception {
            List<Task> tasks = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                tasks.add(generateTask(i));
            }
            return tasks;
        }

        List<Task> generateTaskList(Task... tasks) {
            return Arrays.asList(tasks);
        }
    }
}