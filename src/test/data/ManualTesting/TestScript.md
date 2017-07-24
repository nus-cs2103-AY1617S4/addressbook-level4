# Test Script for Manual Testing

## Loading WhatsNext with sample data

Ensure that you have the file `SampleData.xml` inside the `/src/test/data/ManualTesting` folder.
Open the SampleData.xml file and copy the contents into the whatsnext.xml file in the `/data folder`.

If the whatsnext.xml file does not exist, create a new xml file with the file name `whatsnext` and
copy the content from SampleData.xml over.

Run the MainApp.java file as a Java application. This will prompt the application to run and the WhatsNext
UI will appear.

If the sample data will copied over correctly, the task manager will have a total of 50 tasks inside. <br>
Index 1 - 14 will be in the Events column. <br>
Index 15 - 38 will be in the Deadlines column. <br>
Index 39 - 51 will be in the Basic Task column.


## Testing the help command:

### 1. Help command
1. Input: `help` <br>
Result: A help window appears, listing out all the commands available, together with the command description and format.
2. Input: `help command_name`<br>
Example: `help add` <br>
Result: Display command description and format.

### 2. Wrong command
Input: `add`<br>
Result: "Task names should only contain alphanumeric characters and spaces, and it should not be blank
add: Adds a task to the task manager..." <br>
Note: Use `help command_name` described in `1. Help command` to display command description and format.

## Testing the add command:

### 1. Add an event
Input: `add doing testing s/ today 1200 e/ next monday 1400` or <br>
`add doing testing, today 1200, next monday 1400`

Result:<br>
"Task name: doing testing <br>
Tags: <br>
Status: Incomplete <br>
From: Sun 23 Jul 2017 12:00 PM To: Mon 31 Jul 2017  02:00 PM <br>
Description: Empty" <br>

### 2. Add a duplicate task
Input: `add doing testing s/ today 1200 e/ next monday 1400`

Result: "This task already exists in the task manager"

### 3. Add an overlapping event
Input: `add doing another testing s/ today 1200 e/ 1400`

Result:<br>
"Task name: doing another testing<br>
Tags: [OVERLAP] <br>
Status: Incomplete<br>
From: Sun 23 Jul 2017 12:00 PM To: Sun 23 Jul 2017 02:00 PM<br>
Description: Empty"

### 4. Add an invalid event
Input: `add deadline s/10 august e/9 august` or `add invalid event task, 10 august, 9 august`

Result: <br>
"Invalid Task Format<br>
add: Adds a task to the task manager.<br>
Parameters: TASKNAME [m/DESCRIPTIONS] [s/START_DATETIME] [e/END_DATETIME] [t/TAG]  <br>
Example: add Task1 m/this is an example s/today e/tomorrow 10pm t/tag " <br>

### 5. Add a deadline
Input: `add deadline e/ 11 August` or `add deadline, 11 August`

Result: <br>
"Task name: deadline <br>
Tags: <br>
Status: Incomplete <br>
Due by: Fri 11 Aug 2017 11:59 PM <br>
Description: Empty" <br>

### 6. Add a floating task
Input: `add floatingTask`

Result: "Task name: floatingTask <br>
Tags: <br>
Status: Incomplete <br>
Description: Empty"


## Testing out the edit command:

### 1. Update task name

Input:
1. `find nus`
2. `edit 1 n/new task name`

Result: <br>
1. 8 tasks listed!
2. "Task name: new task name <br>
Tags: [NUS] [CS2103] <br>
Status: Incomplete<br>
From: Thu 20 Jul 2017 08:00 AM To: Thu 20 Jul 2017 10:00 AM<br>
Description: Project meeting for CS2103"

### 2. Update task end time
Input:
1. `find nus`
2. `edit 1 e/10 august 9 am`

Result: <br>
1. 8 tasks listed!
2. "Task name: new task name <br>
Tags: [OVERLAP] [NUS] [CS2103] <br>
Status: Incomplete <br>
From: Thu 20 Jul 2017 08:00 AM To: Thu 10 Aug 2017 09:00 AM <br>
Description: Project meeting for CS2103"


## Testing out the find command:

### 1. Search by task name and tag
Input: `find nus`

Result: "8 tasks listed!" <br>
The tasks whose task name and tags contain the word "nus" will be shown.


## Testing out the clear command:
### 1. Clear all tasks
Input:
1. `list all`
2. `clear all`
3. `undo`

Result:
1. "List all tasks"
2. "Task List has been cleared!"
3. "Previous action has been undone."

### 2. Clear expired tasks
Input:
1. `list expired`
2. `clear expired`
3. `undo`

Result:
1. "List all expired tasks"
2. "Expired tasks have been cleared!"
3. "Previous action has been undone."

### 3. Clear completed tasks
Input:
1. `list all`
2. `mark 1`
3. `list completed`
4. `clear completed`
5. `undo`
6. `undo`

Result:
1. "List all tasks"
2. "Marked Task: Rest for the day Tags: [REST] "
3. "List all completed tasks"
4. "Completed tasks have been cleared!"
5. "Previous action has been undone."
6. "Previous action has been undone."


### 4. Clear incomplete tasks
Input:
1. `list incomplete`
2. `clear incomplete`
3. `undo`

Result:
1. "List all incomplete tasks"
2. "Incomplete tasks have been cleared!"
3. "Previous action has been undone."


## Testing out the undo command:
Input: <br>
1. `clear all`
2. `undo`

Result:
1. "Task List has been cleared!"
2. "Previous action has been undone."
All the task has been brought back from the previous `clear all` function.


## Testing out the redo command:

Input: `redo`

Result: "Previous action has been redone." <br>
All the task has been cleared once again.

## Testing out the mark command:
Input:
1.`undo`
2.`mark 1`

Result:
1. Previous action has been undone.
2. "Marked Task: new task name Tags: [OVERLAP] [NUS] [CS2103] " <br>


## Testing out the unmark command:
Input `unmark 1`

Result: "Unmarked Task: new task name Tags: [OVERLAP] [NUS] [CS2103] " <br>


## Testing out the list command:

Input <br>
1. `list`
2. `list all`
3. `list completed`
// add mark command

4. `list incomplete`
5. `list expired`

Result: <br>
1. "List all upcoming incomplete tasks" <br>
Notice how there is no completed task at the moment
2. "List all tasks" <br>
3. "List all completed tasks"<br>
Notice that the previously marked task is now in the completed list
4. "List all incomplete tasks"
5. "List all expired tasks"<br>
Notice that all task before `today` is listed


## Testing out other commands:

### Delete task

Input:
1. `find nus`
2. `delete 1`
3. `find nus`

Result <br>
1. 8 tasks listed!
2. "Deleted Task: new task name Tags: [OVERLAP] [NUS] [CS2103]"
3. 7 tasks listed!

### Add tags to existing task

Input
1. `undo`
2. `edit 1 +t/ newTag`

Result: <br>
1. Previous action has been undone.
2. "Task name: new task name <br>
Tags: [NEWTAG] [OVERLAP] [NUS] [CS2103]<br>
Status: Incomplete<br>
From: Thu 20 Jul 2017 08:00 AM To: Thu 10 Aug 2017 09:00 AM<br>
Description: Project meeting for CS2103"

### Delete tags in existing task

Input: `edit 1 -t/ newTag`

Result: <br>
"Task name: new task name
Tags: [OVERLAP] [NUS] [CS2103]
Status: Incomplete
From: Thu 20 Jul 2017 08:00 AM To: Thu 10 Aug 2017 09:00 AM
Description: Project meeting for CS2103"


### Update floating task to deadline task

Input:
1. `find buy`
2. `edit 2 e/9 august`

Result:
1. 8 tasks listed!
2. "Task name: Buy Ice Cream
Tags: [GROCERIES]
Status: Incomplete
Due by: Wed 9 Aug 2017 11:59 PM
Description: Buy 2 tubs of chocolate ice cream"

### Update floating task to event task

Input:
1. `find buy`
2. `edit 3 s/8 august e/9 august`

Result:
1. 8 tasks listed!
2. "Task name: Buy some beer
Tags: [GROCERIES]
Status: Incomplete
From: Tue 8 Aug 2017 11:59 PM To: Wed 9 Aug 2017 11:59 PM
Description: Empty"

### Update deadline task to event task

Input:
1. `find buy`
2. `edit 3 s/8 august`

Result:
1. 8 tasks listed!
2. "Task name: Buy Ice Cream
Tags: [GROCERIES] [OVERLAP]
Status: Incomplete
From: Tue 8 Aug 2017 11:59 PM To: Wed 9 Aug 2017 11:59 PM
Description: Buy 2 tubs of chocolate ice cream"

### Reset event task to floating task

Input:
1. `find party`
2. `reset 1`

Result:
1. 2 tasks listed!
2. "Reseted Task: John bachelor party Tags: [WEDDING] "

### Reset deadline task to floating task

Input:
1. `find party`
2. `reset 1`

Result:
1. 2 tasks listed!
2. "Reseted Task: Christmas Party at work Tags: [HOLIDAY] "


## Testing out the `filepath` command
Input `filepath`

Result: "File Path located at: data/whatsnext.xml"

## Testing out the `changepath` command:
Input `changepath data/test`

Result: "Save location changed to: data/test/whatsnext.xml"<br>
File path is now changed to `data/test/whatsnext.xml`

## Testing out the `remind` command:
Input:
1. `remind 1 week`
2. `remind`

Result:
1. "Reminder set: 1 week"
2. "Display reminders" <br>
  Popup message box which displays events with starting date within the week or<br>
  deadline tasks with end date within the week



## Testing out the EXIT function:
Input `EXIT`

Result: WhatsNext closes.
