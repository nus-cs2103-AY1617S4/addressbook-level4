package seedu.ticktask.testutil;

import static org.junit.Assert.*;



import org.junit.Test;


import seedu.ticktask.model.ModelManager;
import seedu.ticktask.model.ReadOnlyTickTask;

import seedu.ticktask.model.UserPrefs;


import seedu.ticktask.model.task.exceptions.DuplicateTaskException;
import seedu.ticktask.model.util.SampleDataUtil;

//@@author A0139819N
/**
 *A class used to test the SampleDataUtil.java class 
 */
public class SampleDataUtilTest {
    @Test
    public void getSampleTickTaskSuccess() throws DuplicateTaskException{
        ModelStub modelStub = new ModelStub(SampleDataUtil.getSampleTickTask());
        assertEquals(modelStub.getCurrentProgramInstance(),SampleDataUtil.getSampleTickTask());

    }
    
    /*
     * An empty TickTask instance stub that will succeed in accepting a task
     */
    public class ModelStub extends ModelManager {

        public ModelStub(ReadOnlyTickTask instance){
            super(instance, new UserPrefs());
        }

    }
    
    
}
//@@author
