package seedu.ticktask.model.tag;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;

import seedu.ticktask.commons.exceptions.IllegalValueException;
import seedu.ticktask.model.TickTask;
import seedu.ticktask.model.tag.UniqueTagList.DuplicateTagException;
import seedu.ticktask.testutil.TypicalTasks;

//@@author A0147928N
public class UniqueTagListTest {
    UniqueTagList tagList;
    TickTask tickTask;
    
    public UniqueTagListTest() throws DuplicateTagException {
        tickTask = new TypicalTasks().getTypicalTickTask();
        tagList = new UniqueTagList(tickTask.getTagList());
    }
    
    @Test
    public void testStringConstructor_success() throws DuplicateTagException, IllegalValueException {
        UniqueTagList tagListCopy = new UniqueTagList(tagList);
        tagList = new UniqueTagList("a b c");
        
        assertFalse(tagListCopy.equals(tagList));
    }
    
    @Test
    public void testTagConstructor_success() throws DuplicateTagException, IllegalValueException {
        UniqueTagList tagListCopy = new UniqueTagList(tagList);

        Tag tag1 = new Tag("a");
        Tag tag2 = new Tag("b");
        tagList = new UniqueTagList(tag1, tag2);
        
        assertFalse(tagListCopy.equals(tagList));
    }
    
    @Test
    public void testTagConstructor_DuplicateTagException() throws IllegalValueException {        
        Tag tag1 = new Tag("a");
        Tag tag2 = new Tag("b");
        
        try {
            tagList = new UniqueTagList(tag1, tag2, tag1);

        } catch (DuplicateTagException e) {
            return;
        }
        fail();     
    }
    
    @Test
    public void testToSet_success() {
        Set<Tag> hashSetList = tagList.toSet();
        assertFalse(hashSetList.isEmpty());
    }
    
    @Test
    public void testSetTags_success() {
        UniqueTagList newTagList = new UniqueTagList();
        
        UniqueTagList tagListCopy = new UniqueTagList(tagList);
        assertEquals(tagList, tagListCopy);
        
        tagList.setTags(newTagList);
        
        assertFalse(tagList.equals(tagListCopy));
        assertEquals(tagList, newTagList); 
    }
    
    @Test
    public void testContains_success() throws IllegalValueException {
        assertTrue(tagList.contains(new Tag("cleaning")));
        assertFalse(tagList.contains(new Tag("clean")));
    }
    
    @Test
    public void testAdd_success() throws DuplicateTagException, IllegalValueException {
        tagList.add(new Tag("dog"));
        assertTrue(tagList.contains(new Tag("dog")));
    }
    
    @Test
    public void testAddTags_throwsException() throws IllegalValueException {
        try {
            tagList.add(new Tag("cleaning"));
        } catch (DuplicateTagException e) {
            return;
        }
        fail();
    }       
}