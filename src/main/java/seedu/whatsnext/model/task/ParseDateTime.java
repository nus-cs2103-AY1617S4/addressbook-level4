package seedu.whatsnext.model.task;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
public class ParseDateTime {

    public static void testDateTime(){
      //Use the Parser class to parse your input string into a list of DateGroup's.
        //Each DateGroup contains a list of Date's, the original value and location of the matching text,
        //granular parse and abstract syntax tree information, and simple recurrence information.
        List<Date> dates = new PrettyTimeParser().parse("Monday 7pm");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(dateFormat.format(dates.get(0))); //2016/11/16 12:08:43
        System.out.println(dates.get(0).getTime());
        System.out.println(dates.get(0).getTime());
        System.out.println(dates);


    }



}
