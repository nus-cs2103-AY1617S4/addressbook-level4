package seedu.whatsnext.model.task;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
public class ParseDateTime {

    public static void testDateTime(){
      //Use the Parser class to parse your input string into a list of DateGroup's.
        //Each DateGroup contains a list of Date's, the original value and location of the matching text,
        //granular parse and abstract syntax tree information, and simple recurrence information.
        List<Date> dates = new PrettyTimeParser().parse("Monday 7pm");
        System.out.println(dates);


    }



}
