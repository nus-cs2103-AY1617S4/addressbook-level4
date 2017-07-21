package seedu.ticktask.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.ticktask.testutil.TypicalTasks.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.task.ReadOnlyTask;
import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.task.exceptions.TaskNotFoundException;
import seedu.ticktask.testutil.TaskBuilder;
import seedu.ticktask.testutil.TypicalTasks;
import seedu.ticktask.testutil.TypicalTasksCompleted;

//@@author A0147928N
public class ModelManagerTest {
    
    TickTask tickTask = new TickTask();
    TickTask otherTickTask = new TypicalTasks().getTypicalTickTask();

    UserPrefs userPrefs = new UserPrefs();
    
    ModelManager emptyModelManager = new ModelManager(tickTask, userPrefs);
    ModelManager emptyModelManagerCopy = new ModelManager(tickTask, userPrefs);
    
    ModelManager modelManager = new ModelManager(otherTickTask, userPrefs);
    ModelManager modelManagerCopy = new ModelManager(otherTickTask, userPrefs);
    
    @Test
    public void testResetData() {
        
        //Should be true as they both contain the exact same data
        assertTrue(emptyModelManager.equals(emptyModelManagerCopy));
        
        emptyModelManager.resetData(otherTickTask);
        
        //Should be false now that resetData() has been called
        assertFalse(emptyModelManager.equals(emptyModelManagerCopy));
        
        //Should be true now that they contain the same data
        assertTrue(emptyModelManager.equals(modelManager));
    }
    
    @Test
    public void testGetTickTask() {
        assertEquals(emptyModelManager.getTickTask(), tickTask);
    }
    
    @Test
    public void testUndoPreviousCommand() {
        emptyModelManager.resetData(otherTickTask);
        emptyModelManager.undoPreviousCommand();
        
        assertTrue(emptyModelManager.equals(emptyModelManagerCopy));
    }
    
    @Test 
    public void testRedoUndoneCommand() {
        emptyModelManager.resetData(otherTickTask);
        emptyModelManager.undoPreviousCommand();
        emptyModelManager.redoUndoneCommand();
        
        assertFalse(emptyModelManager.equals(emptyModelManagerCopy));
    }
    
    @Test
    public void testDeleteFindTask() throws DuplicateTaskException, TaskNotFoundException {
        ReadOnlyTask taskToDelete = modelManager.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        modelManager.deleteFindTask(taskToDelete);
        
        assertFalse(modelManager.equals(modelManagerCopy));
    }
    
    @Test
    public void testDeleteIndexActiveTask() throws DuplicateTaskException, TaskNotFoundException {
        ReadOnlyTask taskToDelete = modelManager.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        modelManager.deleteIndexActiveTask(taskToDelete);
        
        assertFalse(modelManager.equals(modelManagerCopy));
    }
    
    @Test
    public void testDeleteIndexCompleteTask() throws TaskNotFoundException {
        ReadOnlyTask taskToComplete = modelManager.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        
        modelManager.completeTask(taskToComplete);
        modelManagerCopy.completeTask(taskToComplete);
        
        modelManager.deleteIndexCompleteTask(taskToComplete);
        assertFalse(modelManager.equals(modelManagerCopy));
        
        modelManagerCopy.deleteIndexCompleteTask(taskToComplete);
        assertTrue(modelManager.equals(modelManagerCopy));
    }
    
    @Test
    public void testAddTask() throws IllegalValueException {
        ReadOnlyTask taskToAdd = new TaskBuilder().build();
        
        modelManager.addTask(taskToAdd);
        assertFalse(modelManager.equals(modelManagerCopy));
    }
    
    @Test
    public void testUpdateTask() throws IllegalValueException, TaskNotFoundException {
        ReadOnlyTask taskToEdit = modelManager.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        
        ReadOnlyTask editedTask = new TaskBuilder().withName("Random").build();
        
        modelManager.updateTask(taskToEdit, editedTask);
        assertFalse(modelManager.equals(modelManagerCopy));      
    }  
    
    @Test
    public void testEventClash() throws IllegalValueException, TaskNotFoundException {
        ReadOnlyTask taskToEdit = modelManager.getFilteredActiveTaskList().get(INDEX_FIRST_TASK.getZeroBased());
    
        ReadOnlyTask editedTask = new TaskBuilder().withName("Random").build();
        
        assertTrue(modelManager.eventClash(editedTask) == null);
    }
    
    @Test
    public void testGetCurrentProgramInstance() throws DuplicateTaskException, IllegalValueException {
        TickTask currentProgramInstance = modelManager.getCurrentProgramInstance();
        TickTask otherCurrentProgramInstance = modelManagerCopy.getCurrentProgramInstance();
        assertEquals(currentProgramInstance, otherCurrentProgramInstance);
        
        currentProgramInstance.addTask(new TaskBuilder().withName("Random").build());
        
        assertFalse(currentProgramInstance.equals(otherCurrentProgramInstance));
    }
    
    @Test
    public void testSetCurrentProgramInstance() throws DuplicateTaskException, IllegalValueException {
        TickTask currentProgramInstance = modelManager.getCurrentProgramInstance();
        TickTask otherCurrentProgramInstance = modelManagerCopy.getCurrentProgramInstance();
        currentProgramInstance.addTask(new TaskBuilder().withName("Random").build());
        
        modelManager.setCurrentProgramInstance(otherCurrentProgramInstance);
        assertEquals(modelManager.getCurrentProgramInstance(), modelManagerCopy.getCurrentProgramInstance());
    }
    
    @Test
    public void testGetPreviousProgramInstance() throws DuplicateTaskException, IllegalValueException {
        modelManager.addTask(new TaskBuilder().withName("Random").build());
        
        TickTask previousProgramInstance = modelManager.getPreviousProgramInstances().pop();
        
        assertEquals(modelManagerCopy.getCurrentProgramInstance(), previousProgramInstance);
    }
    
    @Test
    public void testGetFutureProgramInstance() throws DuplicateTaskException, IllegalValueException {
        modelManager.addTask(new TaskBuilder().withName("Random").build());
        modelManager.undoPreviousCommand();
        
        TickTask futureProgramInstance = modelManager.getFutureProgramInstances().pop();
        
        modelManagerCopy.addTask(new TaskBuilder().withName("Random1").build());
        modelManagerCopy.undoPreviousCommand();
        
        assertFalse(modelManagerCopy.getFutureProgramInstances().pop().equals(futureProgramInstance));
    }
    
    @Test
    public void testSetPreviousProgramInstance() throws DuplicateTaskException, IllegalValueException {
        modelManager.addTask(new TaskBuilder().withName("Random").build());
        modelManager.addTask(new TaskBuilder().withName("Random1").build());
        
        assertFalse(modelManagerCopy.getPreviousProgramInstances().containsAll(modelManager.getPreviousProgramInstances()));
        
        modelManagerCopy.setPreviousProgramInstances(modelManager.getPreviousProgramInstances());
        
        assertTrue(modelManagerCopy.getPreviousProgramInstances().containsAll(modelManager.getPreviousProgramInstances()));
    }
    
    @Test
    public void testSetFutureProgramInstance() throws DuplicateTaskException, IllegalValueException {
        modelManager.addTask(new TaskBuilder().withName("Random").build());
        modelManager.addTask(new TaskBuilder().withName("Random1").build());
        modelManager.undoPreviousCommand();
        modelManager.undoPreviousCommand();
        
        assertFalse(modelManagerCopy.getFutureProgramInstances().containsAll(modelManager.getFutureProgramInstances()));
        
        modelManagerCopy.setFutureProgramInstances(modelManager.getFutureProgramInstances());
        
        assertTrue(modelManagerCopy.getFutureProgramInstances().containsAll(modelManager.getFutureProgramInstances()));
    }
    
    @Test
    public void testGetFilteredTaskList() {
        assertEquals(modelManager.getFilteredActiveTaskList(), modelManagerCopy.getFilteredActiveTaskList());
    }
    
    @Test
    public void testGetFilteredCompletedTaskList() {
        assertEquals(modelManager.getFilteredCompletedTaskList(), modelManagerCopy.getFilteredCompletedTaskList());
    }
    
    @Test
    public void testUpdateFilteredListToShowEvent() {
        modelManager.updateFilteredListToShowEvent();
        assertFalse(modelManager.getFilteredActiveTaskList().equals(modelManagerCopy.getFilteredActiveTaskList()));
        
        modelManager = new ModelManager(new TypicalTasksCompleted().getTypicalTickTask(), new UserPrefs());
        assertFalse(modelManager.getFilteredCompletedTaskList().equals(modelManagerCopy.getFilteredCompletedTaskList()));
    }
    
    @Test
    public void testUpdateFilteredListToShowDeadline() {
        modelManager.updateFilteredListToShowDeadline();
        assertTrue(modelManager.getFilteredActiveTaskList().equals(modelManagerCopy.getFilteredActiveTaskList()));
    }
    
    @Test
    public void testUpdateFilteredListToShowToday() {
        modelManager.updateFilteredListToShowToday();
        assertFalse(modelManager.getFilteredActiveTaskList().equals(modelManagerCopy.getFilteredActiveTaskList()));
        
        modelManager = new ModelManager(new TypicalTasksCompleted().getTypicalTickTask(), new UserPrefs());
        assertFalse(modelManager.getFilteredCompletedTaskList().equals(modelManagerCopy.getFilteredCompletedTaskList()));
    }
}
//@@author