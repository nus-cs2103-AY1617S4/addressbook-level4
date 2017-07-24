package seedu.whatsnext.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.whatsnext.MainApp;
import seedu.whatsnext.commons.core.ComponentManager;
import seedu.whatsnext.commons.core.Config;
import seedu.whatsnext.commons.core.LogsCenter;
import seedu.whatsnext.commons.events.model.TaskManagerChangedEvent;
import seedu.whatsnext.commons.events.storage.DataSavingExceptionEvent;
import seedu.whatsnext.commons.events.ui.JumpToListRequestEvent;
import seedu.whatsnext.commons.events.ui.ShowHelpRequestEvent;
import seedu.whatsnext.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.whatsnext.commons.util.StringUtil;
import seedu.whatsnext.logic.Logic;
import seedu.whatsnext.model.UserPrefs;
import seedu.whatsnext.model.task.BasicTaskFeatures;

/**
 * The manager of the UI component.
 */
public class UiManager extends ComponentManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/calendaricon.png";

    private static MainWindow mainWindow;
    private static Logic logic;
    private Config config;
    private UserPrefs prefs;


    public UiManager(Logic logic, Config config, UserPrefs prefs) {
        super();
        this.logic = logic;
        this.config = config;
        this.prefs = prefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        primaryStage.setTitle(config.getAppTitle());

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            showReminderAlert();
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    public static void showReminderAlert() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reminder");
        alert.setHeaderText("The following tasks are nearing deadline");
        alert.setContentText(everyReminderTasks());
        alert.show();
    }

    public static String everyReminderTasks() {
        StringBuilder allText = new StringBuilder("Events:\n");
        int index = 0;
        for (int i = 0; i < logic.getReminderList().size(); i++) {
            BasicTaskFeatures task = logic.getReminderList().get(i);
            if (task.getTaskType().equals("event")) {
                allText.append(index + 1 + ". " + task.getName().toString() + "\n");
                index++;
            }
        }
        index = 0;
        allText.append("\nDeadlines:\n");
        for (int i = 0; i < logic.getReminderList().size(); i++) {
            BasicTaskFeatures task = logic.getReminderList().get(i);
            if (task.getTaskType().equals("deadline")) {
                allText.append(index + 1 + ". " + task.getName().toString() + "\n");
                index++;
            }
        }
        return allText.toString();
    }

    @Override
    public void stop() {
        prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
        prefs.updateLastUsedReminderSetting(prefs.getReminderSetting());
        mainWindow.hide();
    }

    private void showFileOperationAlertAndWait(String description, String details, Throwable cause) {
        final String content = details + ":\n" + cause.toString();
        showAlertDialogAndWait(AlertType.ERROR, "File Op Error", description, content);
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
            String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    //==================== Event Handling Code ===============================================================

    @Subscribe
    private void handleDataSavingExceptionEvent(DataSavingExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showFileOperationAlertAndWait("Could not save data", "Could not save data to file", event.exception);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        mainWindow.handleHelp();
    }

    //@@author A0154987J
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        clearSelect();
        findAndScroll(event);
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    private void findAndScroll(JumpToListRequestEvent event) {
        if (mainWindow.getEventListPanel().getEventListView().getItems().size()
                > event.targetIndex) {
            mainWindow.getEventListPanel().scrollTo(event.targetIndex);
            mainWindow.getDeadlineListPanel().getDeadlineListView().getSelectionModel().clearSelection();
            mainWindow.getFloatingListPanel().getFloatingListView().getSelectionModel().clearSelection();
        } else {
            if ((mainWindow.getEventListPanel().getEventListView().getItems().size() + mainWindow
                    .getDeadlineListPanel().getDeadlineListView().getItems().size()) > event.targetIndex) {
                mainWindow.getDeadlineListPanel().scrollTo(event.targetIndex
                        - mainWindow.getEventListPanel().getEventListView().getItems().size());
                mainWindow.getEventListPanel().getEventListView().getSelectionModel().clearSelection();
                mainWindow.getFloatingListPanel().getFloatingListView().getSelectionModel().clearSelection();
            } else {
                mainWindow.getFloatingListPanel().scrollTo(event.targetIndex
                        - ((mainWindow.getEventListPanel().getEventListView().getItems().size()
                                + mainWindow.getDeadlineListPanel().getDeadlineListView().getItems().size())));
                mainWindow.getEventListPanel().getEventListView().getSelectionModel().clearSelection();
                mainWindow.getDeadlineListPanel().getDeadlineListView().getSelectionModel().clearSelection();
            }
        }
    }

    public static void clearSelect() {
        mainWindow.getEventListPanel().getEventListView().getSelectionModel().clearSelection();
        mainWindow.getDeadlineListPanel().getDeadlineListView().getSelectionModel().clearSelection();
        mainWindow.getFloatingListPanel().getFloatingListView().getSelectionModel().clearSelection();
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent abce) {
        mainWindow.getEventListPanel().setConnections(logic.getFilteredTaskList());
        mainWindow.getDeadlineListPanel().setConnections(logic.getFilteredTaskList());
        mainWindow.getFloatingListPanel().setConnections(logic.getFilteredTaskList());
        logger.info(LogsCenter.getEventHandlingLogMessage(abce, "Updating Task List Panels"
                + Integer.toString(mainWindow.getEventListPanel().getEventListView().getItems().size())));
    }

    @Subscribe
    public void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (event.getNewSelection().getTaskType().equals("event")) {
            mainWindow.getDeadlineListPanel().getDeadlineListView().getSelectionModel().clearSelection();
            mainWindow.getFloatingListPanel().getFloatingListView().getSelectionModel().clearSelection();
        } else if (event.getNewSelection().getTaskType().equals("deadline")) {
            mainWindow.getEventListPanel().getEventListView().getSelectionModel().clearSelection();
            mainWindow.getFloatingListPanel().getFloatingListView().getSelectionModel().clearSelection();
        } else if (event.getNewSelection().getTaskType().equals("floating")) {
            mainWindow.getDeadlineListPanel().getDeadlineListView().getSelectionModel().clearSelection();
            mainWindow.getEventListPanel().getEventListView().getSelectionModel().clearSelection();
        }
        ResultDisplay.showSelectedTask(event.getNewSelection());
    }
}
