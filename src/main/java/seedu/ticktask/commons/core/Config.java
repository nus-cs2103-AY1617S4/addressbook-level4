package seedu.ticktask.commons.core;

import java.util.Objects;
import java.util.logging.Level;

import seedu.ticktask.commons.exceptions.DuplicateDataException;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "TickTask App";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String tickTaskFilePath = "data/ticktask.xml";

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
    
    //@@author A0138471A
    public String getTickTaskFilePath() {
    	return tickTaskFilePath;
    }
    
    public void setTickTaskFilePath(String tickTaskFilePath) throws DuplicateTickTaskFilePathException{
    	this.tickTaskFilePath = tickTaskFilePath;
    }
    
    public static class DuplicateTickTaskFilePathException extends DuplicateDataException{
    	protected DuplicateTickTaskFilePathException() {
    		super("Duplicate File Path");
    	}
    }
    //@@author
    
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

}
