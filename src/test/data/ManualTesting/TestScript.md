# Manual Testing : Test Script
##Steps to load the sample data
1. Navigate to TickTask folder in file explorer
2. Ensure that both the TickTask.jar and SampleData.xml are in the same folder
3. Rename the current  SampleData.xml into ticktask.xml
4. Launch TickTask.jar

###Add Command
 Command: `add Buy flowers for Rachel #love` <br>
 Expected: New task added: Buy flowers for Rachel Time:  Task Type: floating Date:  Tags: [love] <br> 
 
 Command: `add Call OCBC Bank by tomorrow at 3pm` <br>
 Expected: New task added: Call OCBC Bank Time: 15:00 Task Type: deadline Date: 07/25/2017 Tags: <br>
 
 Command: `add Fix computer by tmr` <br>
 Expected: New task added: Fix computer Time: 23:59 Task Type: deadline Date: 07/25/2017 Tags: "  <br>
 
 Command: `add Fix bicycle by tmr` <br>
 Expected: New task added: Fix bicycle Time: 23:59 Task Type: deadline Date: 07/25/2017 Tags: "  <br>
 
 Command: `add Family chalet from friday to sunday at 2pm to 5pm`<br>
 Expected: New task added: Family chalet Time: 14:00 - 17:00 Task Type: event Date: 08/03/2017 - 08/07/2017 Tags: <br>
 
 Command: `add inv@l!d ` <br>
 Expected: Task names should only contain alphanumeric characters and spaces, and it should not be blank  <br>
 
###History Command
 Command: `history` <br>
 Expected: <br>
 add Buy flowers for Rachel #love <br>
 add Call OCBC Bank by tomorrow at 3pm <br>
 add Fix computer by tmr <br>
 add Fix bicycle by tmr <br>
 add Family chalet from friday to sunday at 2pm to 5pm <br>
 add inv@l!d<br>

###Delete Command
 Command: `delete active 3` <br>
 Expected: Task deleted:
Description: CS2103 Final Submission Deadline Time: 20:00 Task Type: deadline Date: 07/24/2017 Tags:   <br>
 
 Command: `delete Buy flowers for Rachel` <br>
 Expected: Task deleted:
Description: Buy flowers for Rachel Time:  Task Type: floating Date:  Tags: [love]  <br>

 Command: `delete CALL ocbc BANK` <br>
 Expected: The deleted task name is a subset of the stored task name.
Please use 'undo' command to restore if the deletion in not desired.
Task deleted:
Description: Call OCBC Bank Time: 15:00 Task Type: deadline Date: 07/25/2017 Tags: <br>

Command: `delete finalise` <br>
 Expected: More than one task found! 
Use delete [ complete ] or delete [ active ] INDEX to specify which task to delete. 
Use 'list' command to go back after finishing deletion. <br>
 
 Command: `delete complete 1` <br>
 Expected: Task deleted:
Description: Finalise CS2103 code Time:  Task Type: floating Date:  Tags: <br>

Command: `delete complete 2` <br>
 Expected: Task deleted:
Description: Finalise CS2103 documentation Time:  Task Type: floating Date:  Tags:  <br>

###List Command
 
 
 Command: `list event` <br>
 Expected: List all Event Tasks <br>
 
 Command: `list deadline` <br>
 Expected: List all Deadline Tasks <br>
 
  Command: `list floating` <br>
 Expected: List all Floating Tasks <br>
 
 Command: `list` <br>
 Expected: Listed all tasks <br>
 
###Complete Command
 Command: `complete 2` <br>
 Expected: Completed task: Rehearse for CS2103 presentation Time:  Task Type: floating Date:  Tags:   <br> 

###Restore Command
 Command: `restore 25` <br>
 Expected: Restored task: Rehearse for CS2103 presentation Time:  Task Type: floating Date:  Tags:  <br>
 
###Undo Command
 Command: `undo` <br>
 Expected: Undo successful! <br>
 
###Redo Command
 Command: `redo` <br>
 Expected: Redo Successfully <br>	
 


###Edit command
 Command: `edit 1 date thursday` <br>
 Expected: Edited Task: Study for CS2103 Finals Time: 23:59 Task Type: deadline Date: 07/27/2017 Tags: <br>
 
 Command: `edit 1 type floating` <br>
 Expected: Edited Task: Study for CS2103 Finals Time:  Task Type: floating Date:  Tags:<br>

###Save Command

 Command: `save /Users/Richard/Desktop` <br>
 Expected: Location changed to: /Users/Richard/Desktop/ticktask.xml <br>

 Command: `save /Users/Richard/main/data ` <br>
 Expected: Location changed to: /Users/Richard/main/data/ticktask.xml <br>

###Find Command
 Command: `find buy` <br>
 Expected: 5 tasks listed! <br>
 
###Clear Command
 Command: `clear active` <br>
 Expected: The target list has been cleared! <br>

  Command: `clear complete` <br>
  Expected: The target list has been cleared! <br>
  
  Command: `clear all` <br>
  Expected: The target list has been cleared! <br>

###Help Command
 Command: `help add` <br>
 Expected: add: Adds a task to the TickTask. Parameters: add [TASKNAME] by [DUE DATE] at [DUE TIME] #[TAG1 TAG2 TAG3]
Examples: "add Submit final report by 08/23/17 at 2359 #CAP5  " or " add Upload presentation slides by 24 August at 11pm #CAP5 "<br>

Command: `help edit` <br>
 Expected: edit: Edits the details of the task identifiedby the index number used in the last task listing.Existing values will be overwritten by the input values.
Parameters: INDEX (must be a positive integer) \[name NAME] \[at TIME] \[by DATE] \[#TAG]...
Example: edit 1 name Final report submission by 08/26/17<br>

Command: `help complete` <br>
 Expected: complete: Marks the task identified by the index number as being completed. Task is moved to completed list. 
Parameters: INDEX (must be a positive integer)
Example: complete 1<br>

Command: `help delete` <br>
 Expected: delete : Delete the task identified by keywords if it is the only task found, 
or delete the task identified by the index number of the last task list.
Format: delete [task name]
Example: delete wash clothes
or delete active INDEX (must be a positive integer) or delete complete INDEX (must be a positive integer)
Example: delete complete 1<br>

Command: `help find` <br>
 find: Performs power search on all tasks. Ignores case.Parameters: KEYWORD [MORE_KEYWORDS]...
Example: find tutorial<br>

Command: `help list` <br>
 Expected: list: List a type of specified tasks.
The task types are as follows: event, deadline, floating, today and all.
Example: list deadline<br>

Command: `help redo` <br>
 Expected: redo: Redo an existing command that was previously undone by the user.<br>

Command: `help undo` <br>
 Expected: undo: Undo a previously completed action on TickTask.<br>
 
Command: `help save` <br>
 Expected: save: Changes the location of the Tick Task app doc.
Example: save doc<br>
 
 Command: `help restore` <br>
 Expected: restore: Restores the task identified by the index number back into the active list.
Parameters: INDEX (must be a positive integer)
Example: restore 1<br>
 
###Exit Command
 Command: `exit` <br>
 Expected: Exit from TickTask
