package seedu.whatsnext.commons.core;
import java.util.Objects;
import java.util.logging.Level;

import seedu.whatsnext.commons.exceptions.IllegalValueException;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";
    private static String taskManagerFilePath = "";
    private static String taskManagerName = "MyTaskManager";
    // Config values customizable through config file
    private String appTitle = "WhatsNext App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";


    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public static String getTaskManagerFilePath() {
        return taskManagerFilePath;
    }

    public void setTaskManagerFilePath(String newTaskManagerFilePath) throws RepeatTaskManagerFilePathException {
        if (taskManagerFilePath.equals(newTaskManagerFilePath)) {
            throw new RepeatTaskManagerFilePathException();
        } else {
            taskManagerFilePath = newTaskManagerFilePath;
        }
    }

    public static class RepeatTaskManagerFilePathException extends IllegalValueException {
        public RepeatTaskManagerFilePathException() {
            super("Function will result in duplicate tasks");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { //this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        return sb.toString();
    }

    public String getTaskManagerName() {
        return taskManagerName;
    }

    public void setTaskManagerName(String taskManagerName) {
        this.taskManagerName = taskManagerName;
    }

}
