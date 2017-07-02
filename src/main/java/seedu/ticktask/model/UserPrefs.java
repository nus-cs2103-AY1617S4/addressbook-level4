package seedu.ticktask.model;

import java.util.Objects;

import seedu.ticktask.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs {

    private GuiSettings guiSettings;
    private String tickTaskFilePath = "data/ticktask.xml";
    private String tickTaskName = "MyTickTask";

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

    public String getTickTaskFilePath() {
        return tickTaskFilePath;
    }

    public void setTickTaskFilePath(String tickTaskFilePath) {
        this.tickTaskFilePath = tickTaskFilePath;
    }

    public String getTickTaskName() {
        return tickTaskName;
    }

    public void setTickTaskName(String tickTaskName) {
        this.tickTaskName = tickTaskName;
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
                && Objects.equals(tickTaskFilePath, o.tickTaskFilePath)
                && Objects.equals(tickTaskName, o.tickTaskName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, tickTaskFilePath, tickTaskName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings.toString());
        sb.append("\nLocal data file location : " + tickTaskFilePath);
        sb.append("\nTickTask name : " + tickTaskName);
        return sb.toString();
    }

}
