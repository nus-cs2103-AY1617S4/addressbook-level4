package seedu.whatsnext.model;

import java.util.Objects;

import seedu.whatsnext.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String taskManagerFilePath = "data/taskmanager.xml";
    private String taskManagerName = "MyTaskManager";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
    }

    public GuiSettings getGuiSettings() {
        return guiSettings == null ? new GuiSettings() : guiSettings;
    }

    public void updateLastUsedGuiSetting(GuiSettings guiSettings) {
        this.guiSettings = guiSettings;
    }

    public void setGuiSettings(double width, double height, int x, int y) {
        guiSettings = new GuiSettings(width, height, x, y);
    }

    public String getTaskManagerFilePath() {
        return taskManagerFilePath;
    }

    public void setTaskManagerFilePath(String taskManagerFilePath) {
        this.taskManagerFilePath = taskManagerFilePath;
    }

    public String getTaskManagerName() {
        return taskManagerName;
    }

    public void setTaskManagerName(String taskManagerName) {
        this.taskManagerName = taskManagerName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return Objects.equals(guiSettings, o.guiSettings)
                && Objects.equals(taskManagerFilePath, o.taskManagerFilePath)
                && Objects.equals(taskManagerName, o.taskManagerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, taskManagerFilePath, taskManagerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + taskManagerFilePath);
        sb.append("\nTaskManager name : " + taskManagerName);
        return sb.toString();
    }

}
