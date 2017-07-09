# WhatsNext - User Guide

By : `T01-T4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
3. [FAQ](#3-faq)
4. [Command Summary](#4-command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_131` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `whatsNext.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Task Manager.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add n/project meeting d/July 10 t/5-6`** adds an `Event` task to your task manager.
   * **`delete`**` 1` : deletes the 1st task shown in your current list
   * **`exit`** : exits the app
6. Refer to the [Features](#2-features) section below for details of each command.<br>

## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

> **Reserved Tags**
>
> * Reserved tag `HIGH`, `MEDIUM`, `LOW` names  are used to denote the importance of a certain task
> * Tagged task will be highlighted `RED`, `BLUE`, `GREEN`
> * Reserved tag `OVERLAP` are used to warn you about overlapping events.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd` or the keyword `help`

### 2.2. Adding a task: `add` <br>

Adds an (1) event, (2) deadline or (3) floating to the task manager<br>
Event must have a date, start time and end time. Event can overlap, but it will be tagged with the reserved tag `OVERLAP` to warn you. <br>
Deadline must have a date, but the end time could be optional. If it is specified, it will be by default 2359. <br>
Floating task do not have date or time. <br>

#### 2.2.1 Adding a event <br>
Format: <br>
* `add TASK_NAME on DATETIME to DATETIME tag TAG1 [tag TAG2...]` <br>
Examples: <br>
* `add project on July 10 5pm to July 10 6pm tag meeting` <br>

#### 2.2.2 Adding a deadline <br>
Format: <br>
* `add TASK_NAME on DATETIME tag TAG1 [tag TAG2...]` <br>
Examples:<br>
* `add project on July 10 6pm tag meeting` <br>

#### 2.2.3 Adding a floating <br>
Format: <br>
* `add TASK_NAME tag TAG1 [tag TAG2...]` <br>
Examples:<br>
* `add project tag meeting`

##### Note:
> Tasks can have any number of tags (including 0) <br>
> Only the first priority tag will be accepted if there are more than one inputed. <br>
> TASK_TYPE **must match task type** (1) event, (2) deadline or (3) floating <br>
> TASK_PARAMETERS **must match task parameters of task type**



### 2.3. Listing tasks : `list`

Shows a list of (1) incomplete, (2) complete, (3) all tasks of the particular type in the task manager.<br>
Format:  <br>
* List by default or list incomplete tasks: `list` <br>
* List completed tasks: `list completed` <br>
* List all tasks: `list all` <br>

### 2.4. Editing a task : `edit`

Edits an existing task of a particular type in the task manager.<br>
Format: `edit INDEX [name to NEW_TASK_NAME] [starting date/time to DATETIME] [ending date/time to TIME]  [new tag TAG1] [delete tag TAG2]...`

> * Edits a task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Option fields **must match task type** (1) event, (2) deadline or (3) floating
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will not not be removed. But if there is already a priority tag, i.e. HIGH, MEDIUM, LOW, and the new tag is a priority tag, the original priority tag will be replace with the new one. 
> * When deleting a tag, the tag provided must be inside the existing tag list.

Examples:
* `edit 1 name to midterm exam new tag HIGH delete tag CS2010`<br>
  Edits name 1st task to be `midterm exam`, create a new Priority Tag `HIGH` and delete the existing tag `CS2010` respectively.

### 2.5. Finding all tasks containing any keyword in their name or tags: `find`

Finds tasks whose names or tags contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `meeting` will match `Meeting`
> * The order of the keywords does not matter. e.g. `meeting submission` will match `submission meeting`
> * Only the name and tags are searched.
> * Only full words will be matched e.g. `meeting` will not match `meetings`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `meeting` will match `submission meeting`

Examples:

* `find submission`<br>
  Returns `submission meeting` but not `submission`
* `find CS2103`<br>
  Returns Any tasks having tags `CS2103`

### 2.6. Deleting a task : `delete`

Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd incomplete task in the task manager.
* `find CS2103`<br>
  `delete TASK_TYPE 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. View in detail : `select`

Select a specific task to view in detail. The details will be in command box. <br>
Format `select INDEX` <br>
Example: <br>
* `select 1`
  select and view the 1 task in the task manager.

### 2.8. Undo last action : `undo`

Undo the last action performed by you.<br>
There can be more than one undo action. You may undo the actions during that single session. <br>
Format: `undo`


### 2.9. Clearing tasks : `clear`
Clears (1) incomplete, (2) complete, (3)all tasks in the task manager.<br>
Clears all entries of the same type from the task manager.<br>
Format: `clear MODIFYIER`
* Clear incomplete tasks: `clear incomplete` <br>
* Clear complete tasks: `clear` <br>
* Clear all tasks: `clear all` <br>

### 2.10. Marking tasks : `mark`
Mark the task at the specified `INDEX` to complete the task. <br>
Format: `mark INDEX`
> mark the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...
Examples:

* `list`<br>
  `mark 2`<br>
  Marks the 2nd task in the task manager.
* `find CS2103`<br>
  `mark 1`<br>
  Marks the 1st task in the results of the `find` command.

### 2.11. Marking tasks : `unmark`
Unmark the task at the specified `INDEX`. <br>
Format: `unmark INDEX`
> Unmark the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...
Examples:

* `list`<br>
  `unmark 2`<br>
  Unmarks the 2nd task in the task manager.
* `find CS2103`<br>
  `unmark 1`<br>
  Unmarks the 1st task in the results of the `find` command.

### 2.12. View current data file path : `viewPath`

View data file directory path. <br>
Format: `viewPath`
> View the directory where the data file is saved <br>

### 2.13. Updating data file

Updates data file directory. : `updatePath`<br>
Format: `updatePath [Path Directory]`
> Updates the directory where the data file is saved <br>
Task Manager data are saved in the specified path directory.<br>

### 2.14. Saving the data

Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.15. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manger folder.

## 4. Command Summary

Function | Format | Examples
-------- | ------ | --------
Get Help infomation | `help` |
Add a event | `add TASK_NAME on START_DATE_TIME to END_DATE_TIME [tag TAG]...` | `add Project metting on July 5 18 to July 5 19`
Add a deadline | `add TASK_NAME on END_DATE_TIME [tag TAG1]...` | `add Project submission on July 5 20`
Add a floating | `add TASK_NAME [tag TAG]...` | `add CS2103 exam tag HIGH`
List all tasks | `list all` |
List incomplete tasks | `list incomplete` |
List complete tasks | `list complete` |
Clear all tasks | `clear all` |
Clear incomplete tasks | `clear incomplete` |
Clear complete tasks | `clear complete` |
Edit task | `edit INDEX [name to TASK_NAME] [start datetime to DATE_TIME] [end datetime to DATE_TIME]  [new tag TAG] [delete tag TAG]` | `edit 1 new tag HIGH`
Delete task | `delete INDEX` | `delete 1`
Find by keywords or tags | `find KEYWORD [MORE_KEYWORDS]` | `find CS2103 Exams`
Undo the last action | `Undo` |
Mark incomplete tasks as completed | `mark INDEX` | `mark 1`
Unmark completed task as incomplete | `unmark INDEX` | `unmark 1`
Select and view tasks | `view INDEX` | `view 1`
ViewPath of the storage file | `viewPath` |
Change the path of the storage file | `updatePath [path_directory]` | `updatePath C:\User\tasks.xml`
Exit the programme | `exit` |
