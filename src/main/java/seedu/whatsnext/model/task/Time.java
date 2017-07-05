package seedu.whatsnext.model.task;

public class Time {
    private String timeIn24Hours;

    public Time(){
        timeIn24Hours = "2359";
    }

    /**
     * Time format in HH:MM
     * */
    public Time(String timeInInt){
        timeIn24Hours = timeInInt;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(timeIn24Hours);
        return null;

    }
}
