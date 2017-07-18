package seedu.whatsnext.model;

import java.util.Objects;

import seedu.whatsnext.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String reminderSetting;
    private String taskManagerFilePath;

    private String taskManagerName = "MyTaskManager";

    private final String defaultReminderSetting = "3 day";
    private final String defaultTaskManagerFilePath = "data/whatsnext.xml";

    public UserPrefs() {
        this.setGuiSettings(500, 500, 0, 0);
        this.setReminderSetting(defaultReminderSetting);
        this.setTaskManagerFilePath(defaultTaskManagerFilePath);
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

    //@@author A0154986L
    public String getReminderSetting() {
        return reminderSetting;
    }

    //@@author A0154986L
    public void updateLastUsedReminderSetting(String reminderSetting) {
        this.reminderSetting = reminderSetting;
    }

    //@@author A0154986L
    public void setReminderSetting(String newReminderSetting) {
        this.reminderSetting = newReminderSetting;
    }
    //@@author A0149894H
    public String getTaskManagerFilePath() {
        return taskManagerFilePath;
    }
    //@@author A0149894H
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

        return Objects.equals(guiSettings, o.guiSettings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        return sb.toString();
    }

}
