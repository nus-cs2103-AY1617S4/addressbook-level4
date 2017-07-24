package seedu.ticktask.model.task;

import static org.junit.Assert.*;
import static seedu.ticktask.testutil.TaskBuilder.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeSet;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.tag.Tag;
import seedu.ticktask.testutil.TaskBuilder;

//@@author A0147928N
public class TaskTest {
    Task task;
    
    @Test
    public void testResetTaskType_success() throws IllegalValueException {
        task = new Task(new Name("random"), new DueTime(""), new TaskType("deadline"), new DueDate(""), new TreeSet<Tag>());
        task.resetTaskType();
        
        assertEquals(task.getTaskType().toString(), "floating");
        
        task = new Task(new Name("random"), new DueTime(""), new TaskType("floating"), new DueDate("12/25/2018"), new TreeSet<Tag>());
        task.resetTaskType();
        
        assertEquals(task.getTaskType().toString(), "deadline");
        
        task = new Task(new Name("random"), new DueTime(""), new TaskType("floating"), new DueDate("12/25/2018 - 12/25/2019"), new TreeSet<Tag>());
        task.resetTaskType();
        
        assertEquals(task.getTaskType().toString(), "event");
    }
    
    @Test
    public void testGetCompleted_success() throws IllegalValueException {
        task = new Task(new Name("random"), new DueTime(""), new TaskType("deadline"), new DueDate(""), new TreeSet<Tag>());

        task.setCompleted(true);
        assertTrue(task.getCompleted());
    }
    //@@author
    
    //@@author A0139964M
    @Test
    public void isChronological_pastTaskYear_false() throws IllegalValueException {
        Task validTask = new TaskBuilder().withDate(PAST_DATE).build();
        assertFalse(validTask.isChronological());
    }
    @Test
    public void isChronological_pastTaskTime_false() throws IllegalValueException {
        Task validTask = new TaskBuilder().withDate(LocalDate.now().toString()).withTime(PAST_TIME).build();
        assertFalse(validTask.isChronological());
    }
    @Test
    public void isChronological_futureTaskYear_true() throws IllegalValueException {
        Task validTask = new TaskBuilder().withDate(FUTURE_DATE).build();
        assertTrue(validTask.isChronological());
    }
    @Test
    public void isChronological_futureTaskTime_true() throws IllegalValueException {
        Task validTask = new TaskBuilder().withTime(FUTURE_TIME).build();
        assertTrue(validTask.isChronological());
    }
    @Test
    public void isDateChronological_PastTaskDate_false() throws IllegalValueException {
        Task validTask = new TaskBuilder().withDate(PAST_DATE).build();
        assertFalse(validTask.isDateChronological());
    }
    @Test
    public void isDateChronological_futureTaskDate_true() throws IllegalValueException {
        Task validTask = new TaskBuilder().withDate(FUTURE_DATE).build();
        assertTrue(validTask.isDateChronological());
    }
    @Test
    public void isDateChronological_PastTaskTime_false() throws IllegalValueException {
        Task validTask = new TaskBuilder().withDate(PAST_TIME).build();
        assertFalse(validTask.isTimeChronological());
    }
    @Test
    public void isDateDue_pastDate_true() throws IllegalValueException {
        Task validTask = new TaskBuilder().withTime(FUTURE_DATE).build();
        assertFalse(validTask.isDateDue());
    }
    @Test
    public void isToday_taskToday_true() throws IllegalValueException {
        Task validTask = new TaskBuilder().withDate(LocalDate.now().toString()).build();
        assertTrue(validTask.isToday());
    }
    @Test
    public void isToday_taskNotToday_false() throws IllegalValueException {
        Task validTask = new TaskBuilder().withDate(PAST_DATE).build();
        assertFalse(validTask.isToday());
    }
}
//@@author
