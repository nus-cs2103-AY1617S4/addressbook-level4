package seedu.whatsnext.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.whatsnext.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_END_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_MESSAGE;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_START_DATETIME;
import static seedu.whatsnext.logic.parser.CliSyntax.PREFIX_TAG_CLI;
import static seedu.whatsnext.model.util.SampleDataUtil.getTagSet;
import static seedu.whatsnext.testutil.TypicalTasks.INDEX_THIRD_TASK;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.google.common.eventbus.Subscribe;

import seedu.whatsnext.commons.core.EventsCenter;
import seedu.whatsnext.commons.core.index.Index;
import seedu.whatsnext.commons.events.model.TaskManagerChangedEvent;
import seedu.whatsnext.commons.events.ui.JumpToListRequestEvent;
import seedu.whatsnext.commons.events.ui.ShowHelpRequestEvent;
import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.logic.commands.AddCommand;
import seedu.whatsnext.logic.commands.ClearCommand;
import seedu.whatsnext.logic.commands.Command;
import seedu.whatsnext.logic.commands.CommandResult;
import seedu.whatsnext.logic.commands.DeleteCommand;
import seedu.whatsnext.logic.commands.ExitCommand;
import seedu.whatsnext.logic.commands.FindCommand;
import seedu.whatsnext.logic.commands.HelpCommand;
import seedu.whatsnext.logic.commands.HistoryCommand;
import seedu.whatsnext.logic.commands.ListCommand;
import seedu.whatsnext.logic.commands.SelectCommand;
import seedu.whatsnext.logic.commands.exceptions.CommandException;
import seedu.whatsnext.logic.parser.exceptions.ParseException;
import seedu.whatsnext.model.Model;
import seedu.whatsnext.model.ModelManager;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.TagNotFoundException;
import seedu.whatsnext.testutil.TaskBuilder;


public class LogicManagerTest {

    /**
     * See https://github.com/junit-team/junit4/wiki/rules#temporaryfolder-rule
     */
    @Rule
    public TemporaryFolder saveFolder = new TemporaryFolder();

    private Model model;
    private Logic logic;

    //These are for checking the correctness of the events raised
    private ReadOnlyTaskManager latestSavedTaskManager;
    private boolean helpShown;
    private Index targetedJumpIndex;

    @Subscribe
    private void handleLocalModelChangedEvent(TaskManagerChangedEvent abce) {
        latestSavedTaskManager = new TaskManager(abce.data);
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

        latestSavedTaskManager = new TaskManager(model.getTaskManager()); // last saved assumed to be up to date
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
        Model expectedModel = new ModelManager(model.getTaskManager(), new UserPrefs());
        assertCommandBehavior(expectedException, inputCommand, expectedMessage, expectedModel);
    }

    /**
     * Executes the command, confirms that the result message is correct and that the expected exception is thrown,
     * and also confirms that the following two parts of the LogicManager object's state are as expected:<br>
     *      - the internal model manager data are same as those in the {@code expectedModel} <br>
     *      - {@code expectedModel}'s task list was saved to the storage file.
     */
    private <T> void assertCommandBehavior(Class<T> expectedException, String inputCommand,
                                           String expectedMessage, Model expectedModel) {

        try {
            CommandResult result = logic.execute(inputCommand);
            assertEquals(expectedException, null);
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException | TagNotFoundException | IllegalValueException e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }

        assertEquals(expectedModel, model);
        assertEquals(expectedModel.getTaskManager(), latestSavedTaskManager);
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

    //@@author A0156106M
    @Test
    public void execute_clear_all() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        model.addTask(helper.generateTask(1));
        model.addTask(helper.generateTask(2));
        model.addTask(helper.generateTask(3));

        assertCommandSuccess(ClearCommand.COMMAND_WORD + " "
                + ClearCommand.CLEAR_ALL, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }


    //@@author A0156106M
    @Test
    public void execute_add_invalidTaskData() {
        //String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        String invalidNameErrorMessage = TaskName.MESSAGE_NAME_CONSTRAINTS + "\n" + AddCommand.MESSAGE_USAGE;
        String inputNameInvalid = AddCommand.COMMAND_WORD + " Invalid Name with **SPECIAL CHARACTERS**!!!@@#  "
                + PREFIX_MESSAGE + "Valid Description";

        assertParseException(inputNameInvalid, invalidNameErrorMessage);

        String inputDescriptionInvalid = AddCommand.COMMAND_WORD + " Valid Name "
                + PREFIX_MESSAGE + "invalid description with **SPECIAL CHARACTERS**!!!@@# ";
        String invalidDescriptionErrorMessage = TaskDescription.MESSAGE_NAME_CONSTRAINTS
                + "\n" + AddCommand.MESSAGE_USAGE;

        assertParseException(inputDescriptionInvalid, invalidDescriptionErrorMessage);

    }

    /*
    @Test
    public void execute_add_invalidArgsFormat() {
        String invalidNameErrorMessage = TaskName.MESSAGE_NAME_CONSTRAINTS;
        String inputNameInvalid = AddCommand.COMMAND_WORD + " Invalid Name with **SPECIAL CHARACTERS**!!!@@#  "
                + PREFIX_MESSAGE + "Valid Description";
        String invalidCommand = AddCommand.COMMAND_WORD + " " + PREFIX_NAME + "[]\\[;] "
                + PREFIX_MESSAGE + "valid message ";
        System.out.println(invalidCommand);
        assertParseException(AddCommand.COMMAND_WORD + " "
                + PREFIX_NAME + "[]\\[;] "
                + PREFIX_MESSAGE + "valid message ",
                TaskName.MESSAGE_NAME_CONSTRAINTS);
        assertParseException(AddCommand.COMMAND_WORD + " "
                + PREFIX_NAME + "Valid Name "
                + PREFIX_MESSAGE + "not_valid_message ",
                TaskDescription.MESSAGE_NAME_CONSTRAINTS);
    }
    */

    //@@author A0156106M
    @Test
    public void execute_addEventByComma_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        BasicTask toBeAdded = helper.sampleEventTask();
        Model expectedModel = new ModelManager();
        expectedModel.addTask(toBeAdded);
        assertCommandSuccess(helper.generateAddEventByCommaCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedModel);
    }

    @Test
    public void execute_addFloatingByComma_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        BasicTask toBeAdded = helper.sampleFloatingTask();
        Model expectedModel = new ModelManager();
        expectedModel.addTask(toBeAdded);
        assertCommandSuccess(helper.generateAddFloatingByCommaCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedModel);
    }

    @Test
    public void execute_addDeadlineByComma_successful() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        BasicTask toBeAdded = helper.sampleDeadlineTask();
        Model expectedModel = new ModelManager();
        expectedModel.addTask(toBeAdded);
        assertCommandSuccess(helper.generateAddDeadlineByCommaCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedModel);
    }


    //@@author A0156106M
    @Test
    public void execute_addEvent_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        BasicTask toBeAdded = helper.sampleEventTask();
        Model expectedModel = new ModelManager();
        expectedModel.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedModel);
    }

    //@@author A0156106M
    @Test
    public void execute_addDeadline_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        BasicTask toBeAdded = helper.sampleDeadlineTask();
        Model expectedModel = new ModelManager();
        expectedModel.addTask(toBeAdded);

        // execute command and verify result
        assertCommandSuccess(helper.generateAddCommand(toBeAdded),
                String.format(AddCommand.MESSAGE_SUCCESS, toBeAdded), expectedModel);
    }

    //@@author A0156106M
    @Test
    public void execute_addFloating_successful() throws Exception {
        // setup expectations
        TestDataHelper helper = new TestDataHelper();
        BasicTask toBeAdded = helper.sampleFloatingTask();
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
        BasicTask toBeAdded = helper.sampleEventTask();
        // setup starting state
        model.addTask(toBeAdded); // task already in internal task manager
        // execute command and verify result
        assertCommandException(helper.generateAddCommand(toBeAdded), AddCommand.MESSAGE_DUPLICATE_TASK);

    }

    //@@author A0154986L
    @Test
    public void execute_list_showsAllTasks() throws Exception {
        // prepare expectations
        TestDataHelper helper = new TestDataHelper();
        Model expectedModel = new ModelManager(helper.generateTaskManager(7), new UserPrefs());

        // prepare task manager state
        helper.addToModel(model, 7);

        // Command: "list" - list all upcoming incomplete tasks
        expectedModel.updateFilteredTaskListToShowUpcomingTasks();
        assertCommandSuccess(ListCommand.COMMAND_WORD, ListCommand.MESSAGE_SUCCESS_UPCOMING, expectedModel);

        // Command: "list completed" - to list all completed tasks
        expectedModel.updateFilteredTaskListToShowByCompletion(true);
        assertCommandSuccess(ListCommand.COMMAND_WORD + " " + ListCommand.LIST_COMPLETED,
                ListCommand.MESSAGE_SUCCESS_COMPLETED, expectedModel);

        // Command: "list incomplete" - to list all incomplete tasks
        expectedModel.updateFilteredTaskListToShowByCompletion(false);
        assertCommandSuccess(ListCommand.COMMAND_WORD + " " + ListCommand.LIST_INCOMPLETE,
                ListCommand.MESSAGE_SUCCESS_INCOMPLETE, expectedModel);

        // Command: "list expired" - to list all expired tasks
        expectedModel.updateFilteredTaskListToShowByExpiry();
        assertCommandSuccess(ListCommand.COMMAND_WORD + " " + ListCommand.LIST_EXPIRED,
                ListCommand.MESSAGE_SUCCESS_EXPIRED, expectedModel);

        // Command: "list all" - to list all tasks
        expectedModel.updateFilteredListToShowAll();
        assertCommandSuccess(ListCommand.COMMAND_WORD + " " + ListCommand.LIST_ALL,
                ListCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    //@@author
    /**
     * Confirms the 'invalid argument index number behaviour' for the given command
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list
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
     * targeting a single task in the shown list, using visible index.
     * @param commandWord to test assuming it targets a single task in the last shown list
     *                    based on visible index.
     */
    private void assertIndexNotFoundBehaviorForCommand(String commandWord) throws Exception {
        String expectedMessage = MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
        TestDataHelper helper = new TestDataHelper();
        List<BasicTask> basicTaskList = helper.generateTaskList(2);

        // set TM state to 2 tasks
        model.resetData(new TaskManager());
        for (BasicTask basicTask : basicTaskList) {
            model.addTask(basicTask);
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

    //@@author A0156106M
    @Test
    public void execute_select_jumpsToCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<BasicTask> threeBasicTasks = helper.generateTaskList(3);

        Model expectedModel = new ModelManager(helper.generateTaskManger(threeBasicTasks), new UserPrefs());
        helper.addToModel(model, threeBasicTasks);
        assertCommandSuccess(SelectCommand.COMMAND_WORD + " 2",
                String.format(SelectCommand.MESSAGE_SELECT_TASK_SUCCESS, 2), expectedModel);
        //assertEquals(INDEX_SECOND_TASK, targetedJumpIndex);
        assertEquals(model.getFilteredTaskList().get(1), threeBasicTasks.get(1));

    }

    //@@author
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
    public void execute_delete_removesCorrectTask() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        List<BasicTask> threeTasks = helper.generateTaskList(3);

        Model expectedModel = new ModelManager(helper.generateTaskManger(threeTasks), new UserPrefs());
        expectedModel.deleteTask(threeTasks.get(1));
        helper.addToModel(model, threeTasks);

        assertCommandSuccess(DeleteCommand.COMMAND_WORD + " 2",
                String.format(DeleteCommand.MESSAGE_DELETE_TASK_SUCCESS, threeTasks.get(1)), expectedModel);
    }


    @Test
    public void execute_find_invalidArgsFormat() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);
        assertParseException(FindCommand.COMMAND_WORD + " ", expectedMessage);
    }

    @Test
    public void execute_find_onlyMatchesFullWordsInNames() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        BasicTask pTarget1 = new TaskBuilder().withName("bla bla KEY bla").build();
        BasicTask pTarget2 = new TaskBuilder().withName("bla KEY bla bceofeia").build();
        BasicTask p1 = new TaskBuilder().withName("KE Y").build();
        BasicTask p2 = new TaskBuilder().withName("KEYKEYKEY sduauo").build();

        List<BasicTask> fourTasks = helper.generateTaskList(p1, pTarget1, p2, pTarget2);
        Model expectedModel = new ModelManager(helper.generateTaskManger(fourTasks), new UserPrefs());
        expectedModel.updateFilteredTaskList(new HashSet<>(Collections.singletonList("KEY")));
        helper.addToModel(model, fourTasks);
        assertCommandSuccess(FindCommand.COMMAND_WORD + " KEY",
                Command.getMessageForTaskListShownSummary(expectedModel.getFilteredTaskList().size()),
                expectedModel);
    }

    @Test
    public void execute_find_isNotCaseSensitive() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        BasicTask p1 = new TaskBuilder().withName("bla bla KEY bla").build();
        BasicTask p2 = new TaskBuilder().withName("bla KEY bla bceofeia").build();
        BasicTask p3 = new TaskBuilder().withName("key key").build();
        BasicTask p4 = new TaskBuilder().withName("KEy sduauo").build();

        List<BasicTask> fourTasks = helper.generateTaskList(p3, p1, p4, p2);
        Model expectedModel = new ModelManager(helper.generateTaskManger(fourTasks), new UserPrefs());
        helper.addToModel(model, fourTasks);

        assertCommandSuccess(FindCommand.COMMAND_WORD + " KEY",
                Command.getMessageForTaskListShownSummary(expectedModel.getFilteredTaskList().size()),
                expectedModel);
    }

    @Test
    public void execute_find_matchesIfAnyKeywordPresent() throws Exception {
        TestDataHelper helper = new TestDataHelper();
        BasicTask pTarget1 = new TaskBuilder().withName("bla bla KEY bla").build();
        BasicTask pTarget2 = new TaskBuilder().withName("bla rAnDoM bla bceofeia").build();
        BasicTask pTarget3 = new TaskBuilder().withName("key key").build();
        BasicTask p1 = new TaskBuilder().withName("sduauo").build();

        List<BasicTask> fourTasks = helper.generateTaskList(pTarget1, p1, pTarget2, pTarget3);
        Model expectedModel = new ModelManager(helper.generateTaskManger(fourTasks), new UserPrefs());
        expectedModel.updateFilteredTaskList(new HashSet<>(Arrays.asList("key", "rAnDoM")));
        helper.addToModel(model, fourTasks);

        assertCommandSuccess(FindCommand.COMMAND_WORD + " key rAnDoM",
                Command.getMessageForTaskListShownSummary(expectedModel.getFilteredTaskList().size()),
                expectedModel);
    }

    @Test
    public void execute_verifyHistory_success() throws Exception {
        String validCommand = "clear all";
        logic.execute(validCommand);

        String invalidCommandParse = "   adds   Bob   ";
        try {
            logic.execute(invalidCommandParse);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }

        String invalidCommandExecute = "delete 1"; // task list is of size 0; index out of bounds
        try {
            logic.execute(invalidCommandExecute);
            fail("The expected CommandException was not thrown.");
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

        BasicTask sampleEventTask() throws Exception {
            TaskName taskName = new TaskName("Sample Event Task");
            TaskDescription taskDescription = new TaskDescription("More information on Sample Event Task");
            boolean isCompleted = false;
            DateTime startDateTime = new DateTime("25th September 2017");
            DateTime startEndDate = new DateTime("27th September 2017");
            Set<Tag> tags = getTagSet("high", "event");
            return new BasicTask(taskName, taskDescription, isCompleted, startDateTime, startEndDate, tags);
        }

        BasicTask sampleFloatingTask() throws Exception {
            TaskName taskName = new TaskName("Sample Floating Task");
            TaskDescription taskDescription = new TaskDescription("More information on Sample Floating Task");
            boolean isCompleted = false;
            DateTime startDateTime = new DateTime();
            DateTime startEndDate = new DateTime();
            Set<Tag> tags = getTagSet("high");
            return new BasicTask(taskName, taskDescription, isCompleted, startDateTime, startEndDate, tags);
        }

        BasicTask sampleDeadlineTask() throws Exception {
            TaskName taskName = new TaskName("Sample Deadline Task");
            TaskDescription taskDescription = new TaskDescription("More information on Sample Deadline Task");
            boolean isCompleted = false;
            DateTime startDateTime = new DateTime();
            DateTime startEndDate = new DateTime("27th September 2017");
            Set<Tag> tags = getTagSet("high", "event");
            return new BasicTask(taskName, taskDescription, isCompleted, startDateTime, startEndDate, tags);
        }

        //@@author A0154986L
        /**
         * Generates a valid task using the given seed.
         * Running this function with the same parameter values
         * guarantees the returned task will have the same state.
         * Each unique seed will generate a unique BasicTask object.
         *
         * @param seed used to generate the task data field values
         */
        BasicTask generateTask(int seed) throws Exception {
            // to ensure that task descriptions are at least 3 digits long, when seed is less than 3 digits
            String taskDescription = String.join("", Collections.nCopies(3, String.valueOf(Math.abs(seed))));
            boolean isComplete = (seed % 2) > 0;
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, -2 + seed);
            date = cal.getTime();
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d");

            String startDate = dateFormatter.format(date);

            cal.add(Calendar.DATE, seed);
            date = cal.getTime();
            String endDate = dateFormatter.format(date);

            System.out.println(isComplete + " " + startDate + endDate);

            return new BasicTask(
                    new TaskName("BasicTask " + seed),
                    new TaskDescription(taskDescription),
                    isComplete,
                    new DateTime(startDate),
                    new DateTime(endDate),
                    getTagSet("tag" + Math.abs(seed), "tag" + Math.abs(seed + 1)));
        }

        //@@author
        /** Generates the correct add command based on the task given */
        String generateAddCommand(BasicTask basicTask) {
            StringBuffer cmd = new StringBuffer();

            cmd.append(AddCommand.COMMAND_WORD);

            cmd.append(" " + basicTask.getName());
            cmd.append(" " + PREFIX_MESSAGE.getPrefix()).append(basicTask.getDescription());
            if (basicTask.getTaskType().equals(BasicTask.TASK_TYPE_EVENT)) {
                cmd.append(" " + PREFIX_START_DATETIME.getPrefix())
                    .append(basicTask.getStartDateTime().displayDateTime());
            }
            if (basicTask.getTaskType().equals(BasicTask.TASK_TYPE_EVENT)
                    || basicTask.getTaskType().equals(BasicTask.TASK_TYPE_DEADLINE)) {
                cmd.append(" " + PREFIX_END_DATETIME.getPrefix()).append(basicTask.getEndDateTime().displayDateTime());
            }
            Set<Tag> tags = basicTask.getTags();
            for (Tag t: tags) {
                cmd.append(" " + PREFIX_TAG_CLI.getPrefix()).append(t.tagName);
            }
            return cmd.toString();
        }

        //@@author A0156106M
        String generateAddEventByCommaCommand(BasicTask basicTask) {
            StringBuffer command = new StringBuffer();

            command.append(AddCommand.COMMAND_WORD);
            command.append(" " + basicTask.getName());
            command.append(", " + "\"" + basicTask.getDescription() + "\"");
            command.append(", " + basicTask.getStartDateTime().displayDateTime());
            command.append(", " + basicTask.getEndDateTime().displayDateTime());
            command.append(", tags: ");
            Set<Tag> tags = basicTask.getTags();
            for (Tag t: tags) {
                command.append(t.tagName + " ");
            }
            return command.toString();
        }

        //@@author A0156106M
        String generateAddFloatingByCommaCommand(BasicTask basicTask) {
            StringBuffer command = new StringBuffer();
            command.append(AddCommand.COMMAND_WORD);
            command.append(" " + basicTask.getName());
            command.append(", " + "\"" + basicTask.getDescription() + "\"");
            command.append(", " + basicTask.getEndDateTime().displayDateTime());
            command.append(", tags: ");
            Set<Tag> tags = basicTask.getTags();
            for (Tag t: tags) {
                command.append(t.tagName + " ");
            }
            System.out.println("COMMAND:" + command.toString());
            return command.toString();
        }

        //@@author A0156106M
        String generateAddDeadlineByCommaCommand(BasicTask basicTask) {
            StringBuffer command = new StringBuffer();

            command.append(AddCommand.COMMAND_WORD);
            command.append(" " + basicTask.getName());
            command.append(", " + "\"" + basicTask.getDescription() + "\"");
            command.append(", tags: ");
            Set<Tag> tags = basicTask.getTags();
            for (Tag t: tags) {
                command.append(t.tagName + " ");
            }
            return command.toString();
        }

        /**
         * Generates a TaskManager with auto-generated BasicTasks.
         */
        TaskManager generateTaskManager(int numGenerated) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, numGenerated);
            return taskManager;
        }

        /**
         * Generates an TaskManager based on the list of BasicTask given.
         */
        TaskManager generateTaskManger(List<BasicTask> basicTask) throws Exception {
            TaskManager taskManager = new TaskManager();
            addToTaskManager(taskManager, basicTask);
            return taskManager;
        }

        /**
         * Adds auto-generated BasicTask objects to the given TaskManager
         * @param taskManager The TaskManager to which the Tasks will be added
         */
        void addToTaskManager(TaskManager taskManager, int numGenerated) throws Exception {
            addToTaskManager(taskManager, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of BasicTask to the given TaskManager
         */
        void addToTaskManager(TaskManager taskManager, List<BasicTask> taskToAdd) throws Exception {
            for (BasicTask task: taskToAdd) {
                taskManager.addTask(task);
            }
        }

        /**
         * Adds auto-generated BasicTask objects to the given model
         * @param model The model to which the BasicTask will be added
         */
        void addToModel(Model model, int numGenerated) throws Exception {
            addToModel(model, generateTaskList(numGenerated));
        }

        /**
         * Adds the given list of Tasks to the given model
         */
        void addToModel(Model model, List<BasicTask> tasksToAdd) throws Exception {
            for (BasicTask task: tasksToAdd) {
                model.addTask(task);
            }
        }

        /**
         * Generates a list of BasicTasks based on the flags.
         */
        List<BasicTask> generateTaskList(int numGenerated) throws Exception {
            List<BasicTask> basicTaskList = new ArrayList<>();
            for (int i = 1; i <= numGenerated; i++) {
                basicTaskList.add(generateTask(i));
            }
            return basicTaskList;
        }

        List<BasicTask> generateTaskList(BasicTask... basicTasks) {
            return Arrays.asList(basicTasks);
        }
    }
}
