package seedu.whatsnext.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.whatsnext.commons.exceptions.IllegalValueException;
import seedu.whatsnext.model.ReadOnlyTaskManager;
import seedu.whatsnext.model.TaskManager;
import seedu.whatsnext.model.tag.Tag;
import seedu.whatsnext.model.task.BasicTask;
import seedu.whatsnext.model.task.DateTime;
import seedu.whatsnext.model.task.TaskDescription;
import seedu.whatsnext.model.task.TaskName;
import seedu.whatsnext.model.task.exceptions.DuplicateTaskException;

public class SampleDataUtil {
    public static BasicTask[] getSamplePersons() {
        try {
            return getSampleData();
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyTaskManager getSampleTaskManager() {
        try {
            TaskManager sampleAb = new TaskManager();
            for (BasicTask samplePerson : getSamplePersons()) {
                sampleAb.addTask(samplePerson);
            }
            return sampleAb;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) throws IllegalValueException {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

    //@@author A0156106M
    /**
     * Returns an array of 50 BasicTask sample tasks
     * */
    public static BasicTask[] getSampleData() throws IllegalValueException {
        BasicTask[] sampleData = {
            // Floating Tasks
            new BasicTask(new TaskName("Buy Ice Cream"),
                    new TaskDescription("Buy 2 tubs of chocolate ice cream"), getTagSet("groceries")),
            new BasicTask(new TaskName("Buy some beer"),
                    new TaskDescription(), getTagSet("groceries")),
            new BasicTask(new TaskName("Buy Coffee"),
                    new TaskDescription("Buy coffee to complete CS2103 project"), getTagSet("groceries")),
            new BasicTask(new TaskName("Buy a new TV"),
                    new TaskDescription(), getTagSet("TV")),
            new BasicTask(new TaskName("Buy new Chair"),
                    new TaskDescription("Buy a new chair from ikea"), getTagSet("chair")),
            new BasicTask(new TaskName("Build a new Chair"),
                    new TaskDescription("Build a new Chair from scratch"), getTagSet("chair")),
            new BasicTask(new TaskName("BTO"), new TaskDescription("Build To Order"), getTagSet("house")),
            new BasicTask(new TaskName("Try again for BTO"),
                    new TaskDescription(), getTagSet("house")),
            new BasicTask(new TaskName("Give up on BTO"),
                    new TaskDescription(), getTagSet("house")),
            new BasicTask(new TaskName("Rent a house"),
                    new TaskDescription("Rent a house for life"), getTagSet("house")),
            new BasicTask(new TaskName("Build a house"), new TaskDescription("Build own house"), getTagSet("house")),
            new BasicTask(new TaskName("Buy a car"), new TaskDescription("Get a new car"), getTagSet("car")),
            new BasicTask(new TaskName("Buy a boat"), new TaskDescription("Get a new boat"), getTagSet("boat")),
            new BasicTask(new TaskName("Buy a bicycle"), new TaskDescription("Get a new bicycle"), getTagSet()),
            new BasicTask(new TaskName("Buy a tricycle"),
                    new TaskDescription("No money get a tricycle instead"), getTagSet()),
            // Deadline Task
            new BasicTask(new TaskName("CS2103 assignment"),
                    new TaskDescription("Complete CS2103 assignment"),
                    new DateTime("next Monday"), getTagSet("CS2103")),
            new BasicTask(new TaskName("Go to the Gym"),
                    new TaskDescription(), new DateTime("next Monday"), getTagSet("gym")),
            new BasicTask(new TaskName("Basketball tournament"),
                    new TaskDescription(), new DateTime("next tuesday, 6pm"), getTagSet("basketball")),
            new BasicTask(new TaskName("Soccer competition"),
                    new TaskDescription(), new DateTime("following monday, 7am"), getTagSet("soccer")),
            new BasicTask(new TaskName("Samuel Birthday"),
                    new TaskDescription(), new DateTime("following friday"), getTagSet("birthday")),
            new BasicTask(new TaskName("Buy beer for BBQ"), new TaskDescription("Buy beer for Jacks birthday party"),
                    new DateTime("next friday"), getTagSet("BBQ")),
            new BasicTask(new TaskName("BBQ at Jacks house"),
                    new TaskDescription("Jacks birthday party"), new DateTime("next friday"), getTagSet("BBQ")),
            new BasicTask(new TaskName("Wedding Anniversary"),
                    new TaskDescription(), new DateTime("next Sunday"), getTagSet("annivesary")),
            new BasicTask(new TaskName("Dentist Appointment"), new TaskDescription("Clean teeth"),
                    new DateTime("next sunday, 1am"), getTagSet("dental", "doctor")),
            new BasicTask(new TaskName("Doctor Appointment"),
                    new TaskDescription("physiotherapy"), new DateTime("following sunday, 1pm"), getTagSet("doctor")),
            new BasicTask(new TaskName("Lunch with John"),
                    new TaskDescription("lunch at some restaurant with john"),
                    new DateTime("following monday, 12pm"), getTagSet("lunch", "food")),
            new BasicTask(new TaskName("Dinner with Jack"),
                    new TaskDescription("dinner at some restaurant with jack"),
                    new DateTime("following tuesday, 6pm"), getTagSet("dinner", "food")),
            new BasicTask(new TaskName("Project Meeting"),
                    new TaskDescription("CS2103 project meeting with teammates"),
                    new DateTime("following weds, 6pm"), getTagSet("CS2103", "NUS")),
            new BasicTask(new TaskName("Get Ready for School"),
                    new TaskDescription(), new DateTime("following weds, 12pm"), getTagSet("CS2103", "NUS")),
            new BasicTask(new TaskName("FYP selection"), new TaskDescription("Select FYP project"),
                    new DateTime("following friday, 12pm"), getTagSet("FYP", "NUS")),
            new BasicTask(new TaskName("Bid for modules"), new TaskDescription("Module bidding"),
                    new DateTime("3 weeks from now, 8am"), getTagSet("modules", "NUS")),
            new BasicTask(new TaskName("Bid for tutorials"), new TaskDescription("Tutorial bidding"),
                    new DateTime("4 weeks from now, 8am"), getTagSet("tutorials", "NUS")),
            new BasicTask(new TaskName("Withdraw from school"),
                    new TaskDescription("Select FYP project"), new DateTime("5 weeks from now, 8am"), getTagSet("NUS")),
            new BasicTask(new TaskName("Christmas"),
                    new TaskDescription(), new DateTime("25 December"), getTagSet("holiday")),
            new BasicTask(new TaskName("Christmas Party at work"),
                    new TaskDescription(), new DateTime("23 December"), getTagSet("holiday")),
            // Event Task
            new BasicTask(new TaskName("Rest for the day"), new TaskDescription("Sleep the entire day away"),
                    new DateTime("next monday 12am"), new DateTime("next monday 1159pm"), getTagSet("rest")),
            new BasicTask(new TaskName("Meet prof"), new TaskDescription("Project stuff"),
                    new DateTime("1 week from now, 12pm"), new DateTime("1 week from now, 1pm"), getTagSet("project")),
            new BasicTask(new TaskName("Revise for midterms"), new TaskDescription("Revision for midterms"),
                    new DateTime("20 August"), new DateTime("25 August"), getTagSet("midterms")),
            new BasicTask(new TaskName("John getting married"), new TaskDescription("Wedding dinner at some hotel"),
                    new DateTime("1 week from now, 6pm"), new DateTime("1 week from now, 9pm"), getTagSet("wedding")),
            new BasicTask(new TaskName("John bachelor party"), new TaskDescription("Bachelor party at some place"),
                    new DateTime("next monday, 6pm"), new DateTime("next tuesday, 4am"), getTagSet("wedding")),
            new BasicTask(new TaskName("John getting divorce"), new TaskDescription("Go to court for settlement"),
                    new DateTime("following friday, 8am"),
                    new DateTime("following friday, 10am"), getTagSet("wedding")),
            new BasicTask(new TaskName("John getting remarried"), new TaskDescription("Wedding dinner at some hotel"),
                    new DateTime("11 August 2018, 6pm"), new DateTime("11 August 2018, 9pm"), getTagSet("wedding")),
            new BasicTask(new TaskName("CS2103 Project meeting"), new TaskDescription("Project meeting for CS2103"),
                    new DateTime("next tuesday 8am"), new DateTime("next tues 10am"), getTagSet("nus", "CS2103")),
            new BasicTask(new TaskName("Exam Period"), new TaskDescription("Final Exam Period"),
                    new DateTime("11 Dec 2017"),
                    new DateTime("20 Dec 2017"), getTagSet("nus", "exam")),
            new BasicTask(new TaskName("Study Break"),
                    new TaskDescription("Study break before Exams"), new DateTime("1 Dec 2017"),
                    new DateTime("10 Dec 2017"), getTagSet("nus", "exam")),
            new BasicTask(new TaskName("Winter Vacation"),
                    new TaskDescription("Post Exam vacation to Europe"), new DateTime("11 Dec 2017"),
                    new DateTime("30 Dec 2017"), getTagSet("holiday")),
            new BasicTask(new TaskName("Internship"),
                    new TaskDescription(), new DateTime("1 Jan 2018"),
                    new DateTime("1 March 2018"), getTagSet("intern")),
            new BasicTask(new TaskName("Meet some frieds"), new TaskDescription(),
                    new DateTime("next friday 2pm"), new DateTime("next friday 4pm"), getTagSet("meeting")),
            new BasicTask(new TaskName("Watch movie"), new TaskDescription(),
                    new DateTime("next friday 5pm"), new DateTime("next friday 7pm"), getTagSet("movie")),
            new BasicTask(new TaskName("Drinks with friends"), new TaskDescription(),
                    new DateTime("next friday 11pm"), new DateTime("next friday 2am"), getTagSet("meeting")),

        };
        return sampleData;
    }



}
