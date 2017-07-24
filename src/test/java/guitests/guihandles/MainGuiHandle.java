package guitests.guihandles;

import guitests.GuiRobot;
import javafx.stage.Stage;
import seedu.whatsnext.TestApp;
//author A0154987J
/**
  * Provides a handle for the main GUI.
  */
public class MainGuiHandle extends GuiHandle {

    public MainGuiHandle(GuiRobot guiRobot, Stage primaryStage) {
         super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public EventListPanelHandle getEventListPanel() {
        return new EventListPanelHandle(guiRobot, primaryStage);
    }

    public DeadlineListPanelHandle getDeadlineListPanel() {
        return new DeadlineListPanelHandle(guiRobot, primaryStage);
    }

    public FloatingListPanelHandle getFloatingListPanel() {
        return new FloatingListPanelHandle(guiRobot, primaryStage);
    }

    public ResultDisplayHandle getResultDisplay() {
        return new ResultDisplayHandle(guiRobot, primaryStage);
    }

    public CommandBoxHandle getCommandBox() {
        return new CommandBoxHandle(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public MainMenuHandle getMainMenu() {
        return new MainMenuHandle(guiRobot, primaryStage);
    }

    public AlertDialogHandle getAlertDialog(String title) {
        guiRobot.sleep(1000);
        return new AlertDialogHandle(guiRobot, primaryStage, title);
    }
}
