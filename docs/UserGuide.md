# WhatsNext - User Guide

By : `T01-T4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#1-quick-start)
2. [Features](#2-features)
    1. [Viewing help](#21-viewing-help--help)
    2. [Adding a task](#22-adding-a-task-add-)
    3. [Listing tasks](#23-listing-tasks--list)
    4. [Editing a task](#24-editing-a-task--edit)
    5. [Finding all tasks containing any keywords in their names or tags](#25-finding-all-tasks-containing-any-keyword-in-their-name-or-tags-find)
    6. [Deleting a task](#26-deleting-a-task--delete)
    7. [Select a task to view in detail](#27-select-a-task-to-view-in-detail--select)
    8. [Undo last action](#28-undo-last-action--undo)
    9. [Redo last action](#29-redo-last-action--redo)
    10. [Clear tasks](#210-clear-tasks--clear)
    11. [Mark a task](#211-mark-a-task--mark)
    12. [Unmark a task](#212-unmark-a-task--unmark)
    13. [Reset a task](#213-reset-a-task--reset)
    14. [Set reminder period](#214-set-reminder-period--remind)
    15. [View current data file path](#215-view-current-data-file-path--filepath)
    16. [Change data file path](#216-change-data-file-path--changepath)
    17. [Save the data](#217-save-the-data)
    18. [Exit the programme](#218-exit-the-programme--exit)

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
   * **`list all`** : lists all tasks
   * **`add project meeting s/10 July 5pm e/10 July 6pm`** adds an `Event` task to your task manager.
   * **`delete`**` 1` : deletes the 1st task shown in your current list
   * **`exit`** : exits the app
6. Refer to the [Features](#2-features) section below for details of each command.<br>

## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order except for task name which has be the first one for add command.

> **Reserved Tags**
>
> * Reserved tag `HIGH`, `MEDIUM`, `LOW` names  are used to denote the importance of a certain task
> * Tagged task will be highlighted `RED`, `BLUE`, `GREEN` respectively for `HIGH`, `MEDIUM`, `LOW`
> * Reserved tag `OVERLAP` are used to warn you about overlapping events.

> **DateTime parsing**
>
> * WhatsNext uses the [Pretty Time Parser](http://www.ocpsoft.org/prettytime/nlp/) to parse Strings to Date objects.
> * Format Accepted by for parsing time:
>   1. DD MM YYYY HH:MM am/pm
> 2. DD MM YYYY
> 3. next DD
> 4. last DD
> Example:
> 1. 11 August 2017 6am
> 2. 11 August 2017
> 3. next monday
> 4. last monday
> * If date is not specified, it will default to 11.59pm.
> * The start date of a task cannot be after the end date of a task.
> * Expired dates (task with dates before today) are highlighted red.


### 2.1. Viewing help : `help`

Format: `help`
* Opens a window which displays the UserGuide

Format: `help COMMAND_WORD`
* Displays the MESSAGE_USAGE for that command

Examples:
* `help add`
* `help edit`

### 2.2. Adding a task: `add` <br>

Adds an (1) event, (2) deadline or (3) floating to the task manager
1. Event must have a date, start time and end time. Event can overlap, but it will be tagged with the reserved tag `OVERLAP` to warn you.
2. Deadline must have a date, but the end time can be optional. If time is not specified, it will default to 23:59.
3. Floating task do not have date or time.

A task can be added using the `prefix` or `parse by comma` method
1. Prefix: `add TASK_NAME s/ START_DATE_TIME e/ END_DATE_TIME [m/ TASK_DESCRIPTION] [t/ TAG1 t/ TAG2...]`
2. Parse By Comma: `add TASK_NAME, ["TASK_DESCRIPTION"], START_DATE_TIME, END_DATE_TIME, [tags: TAG1 TAG2...]`

#### 2.2.1 Adding a event <br>
Format:
* `add TASK_NAME s/ START_DATE_TIME e/ END_DATE_TIME [m/ TASK_DESCRIPTION] [t/ TAG1 t/ TAG2...]`
* `add TASK_NAME, ["TASK_DESCRIPTION"], START_DATE_TIME, END_DATE_TIME, [tags: TAG1 TAG2...]`

Examples:
* `add project s/ July 10 5pm e/ July 10 6pm t/ meeting`
* `add project, "CS2103 project", July 10 5pm, July 10 6pm`

Note:
> Events can overlap but it will be marked with an exclamation icon and `OVERLAP` tag.


#### 2.2.2 Adding a deadline <br>

Format:
* `add TASK_NAME e/ DATETIME [t/ TAG1 t/ TAG2...]`
* `add TASK_NAME, ["TASK_DESCRIPTION"], DATETIME, [tags: TAG1 TAG2...]`

Examples:
* `add project e/ July 10 6pm t/ meeting`
* `add project, "CS2103 project", July 10 6pm, tags: meeting`

#### 2.2.3 Adding a floating

Format:
* `add TASK_NAME [m/ TASK_DESCRIPTION] [t/ TAG1 t/ TAG2...]`
* `add TASK_NAME, ["TASK_DESCRIPTION"], [tags: TAG1 t/ TAG2...]`

Examples:
* `add project t/ meeting`
* `add project, "CS2103 project", tags: meeting`

##### Note:
> Tasks can have any number of tags (including 0) <br>
> Only the first priority tag will be accepted if there are more than one inputed. <br>
> TASK_TYPE **must match task type** (1) event, (2) deadline or (3) floating <br>
> TASK_TYPE will be determined by the application internally based on your input. Event must have starting and ending date-time. Deadline must have ending date-time and Floating should not have any date-time. <br>
> TASK_PARAMETERS **must match task parameters of task type**

### 2.3. Listing tasks : `list`

Shows a list of (1)upcoming incomplete, (2) all incomplete, (3) complete, (4) expired, (5) all tasks of the particular type in the task manager.<br>
All expired tasks' DateTime will be coloured as red.

Format:
* List upcoming incomplete tasks: `list`
* List all incomplete tasks: `list incomplete`
* List completed tasks: `list completed`
* List expired tasks: `list expired`
* List all tasks: `list all`

### 2.4. Editing a task : `edit`

Edits an existing task in the task manager.
Format: `edit INDEX [n/ NEW_TASK_NAME] [s/ to START_DATE_TIME] [e/ to START_DATE_TIME]  [+t/ TAG1] [-t/ TAG2]...`

* Edits a task at the specified `INDEX`.
  The index refers to the index number shown in the last task listing.<br>
  The index **must be a positive integer** 1, 2, 3, ...
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the task will not not be removed. But if there is already a priority tag, i.e. HIGH, MEDIUM, LOW, and the new tag is a priority tag, the original priority tag will be replace with the new one.
* When deleting a tag, the tag provided must be inside the existing tag list.

Examples:
* `edit 1 n/ midterm exam +t/ HIGH -t/ CS2010`<br>
  Edits name 1st task to be `midterm exam`, create a new Priority Tag `HIGH` and delete the existing tag `CS2010` respectively.

### 2.5. Finding all tasks containing any keyword in their name or tags: `find`

Finds tasks whose names or tags contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case insensitive. e.g `meeting` will match `Meeting`
* The order of the keywords does not matter. e.g. `meeting submission` will match `submission meeting`
* Only the name and tags are searched.
* Only full words will be matched e.g. `meeting` will not match `meetings`
* Tasks matching at least one keyword will be returned (i.e. `OR` search).<br>
  e.g. `meeting` will match `submission meeting`

Examples:

* `find submission`<br>
  Returns `submission meeting` but not `submission`
* `find CS2103`<br>
  Returns Any tasks having tags `CS2103`

### 2.6. Deleting a task : `delete`

Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

* Deletes the task at the specified `INDEX`. <br>
* The index refers to the index number shown in the most recent listing.<br>
* The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd incomplete task in the task manager.
* `find CS2103`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Select a task to view in detail : `select`

Select a specific task to view in detail. The details will be in command box. <br>
Format `select INDEX` <br>
Example: <br>
* `select 1`
  select and view the 1 task in the task manager.

### 2.8. Undo last action : `undo`

Undo the last action performed by you.<br>
There can be more than one undo action. You may undo the actions during that single session. <br>
Format: `undo`

### 2.9. Redo last action : `redo`

Redo the last action undid by previous undo.<br>
There can be more than one redo action. You may redo the actions during that single session. <br>
Format: `redo`

### 2.10. Clear tasks : `clear`
Clears (1) incomplete, (2) complete, (3) expired, (4)all tasks in the task manager.<br>
Clears all entries of the same type from the task manager.

Format: `clear MODIFYIER`
1. Clear incomplete tasks: `clear incomplete`
2. Clear complete tasks: `clear completed`
3. Clear expired tasks: `clear expired`
4. Clear all tasks: `clear all`

### 2.11. Mark a task : `mark`
Mark the task at the specified `INDEX` to complete the task.

Format: `mark INDEX`
* mark the task at the specified `INDEX`.
* Index refers to the index number shown in the most recent listing.
* Index **must be a positive integer** 1, 2, 3, ...

Examples:
* `list`<br>
  `mark 2`<br>
  Marks the 2nd task in the task manager.
* `find CS2103`<br>
  `mark 1`<br>
  Marks the 1st task in the results of the `find` command.

### 2.12. Unmark a task : `unmark`
Unmark the task at the specified `INDEX`.

Format: `unmark INDEX`
* Unmark the task at the specified `INDEX`.
* The index refers to the index number shown in the most recent listing.
* The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `unmark 2`<br>
  Unmarks the 2nd task in the task manager.
* `find CS2103`<br>
  `unmark 1`<br>
  Unmarks the 1st task in the results of the `find` command.

### 2.13. Reset a task : `reset`
Reset a event or deadline at the specified `INDEX` to a floating task. It removes the start and end Date and Time and the overlap tag is also removed if it existed. <br>

Format: `reset INDEX`
* The index refers to the index number shown in the most recent listing.
* The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `reset 2`<br>
  Resets the 2nd task in the task manager.
* `find CS2103`<br>
  `reset 1`<br>
  Resets the 1st task in the results of the `find` command.

### 2.14. Set reminder period : `remind`
The app will automatically display all events and deadlines within the period specified by the user when the app starts. <br>
The command allows the user to specify the time period or display the events and deadlines again without restarting the app. <br>
Format: `remind`
> Display all events and deadlines within the period specified.

Format: `remind TIMEPERIOD`
> Set the time period for the reminder on startup.
> The time unit must be sigular, i.e. "week" instead of "weeks"
Examples:

* `remind 3 day`<br>
  Display all deadlines and events within 3 days upon startup.
* `remind 2 week`<br>
  Display all deadlines and events within 2 weeks upon startup.

### 2.15. View current data file path : `filepath`

View data file directory path. <br>
Format: `filepath`
> View the directory where the data file is saved <br>

### 2.16. Change data file path : `changepath`

Updates data file directory. : `changepath`<br>
If the folder does not exist, the folder will be created authomatically. The save file at the original position will be deleted.<br>
Format: `changepath [Path Directory]`
> Changes the directory where the data file is saved <br>
> Task Manager data are saved in the specified path directory.<br>
Examples:

* `changepath user`
  Change the file path to user folder under the current directory.<br>
* `changepath C:\Users\User\Desktop`
  Change the file path to desktop of the windows system. <br>

### 2.17. Save the data

Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.18. Exit the programme : `exit`

Exits the program.<br>
Format: `exit`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manger folder.

## 4. Command Summary

Function | Format | Examples
-------- | ------ | --------
Get Help infomation | `help` OR `help COMMANDWORD` | `help`<br> `help add`
Add a event | `add TASK_NAME s/ START_DATE_TIME e/ END_DATE_TIME [t/ TAG...]`<br> `add TASK_NAME, ["TASK_DESCRIPTION"], START_DATE_TIME, END_DATE_TIME, [tags: TAG1 TAG2...]` | `add Project metting s/ July 5 18 e/ July 5 19`<br> `add project, "CS2103 project", July 10 5pm, July 10 6pm`
Add a deadline | `add TASK_NAME e/ END_DATE_TIME [t/ TAG1...]`<br> `add TASK_NAME, ["TASK_DESCRIPTION"], DATETIME, [tags: TAG1 TAG2...]` | `add Project submission e/ July 5 20`<br> `add project, "CS2103 project", July 10 6pm, tags: meeting`
Add a floating | `add TASK_NAME [t/ TAG...]`<br> `add TASK_NAME, ["TASK_DESCRIPTION"], [tags: TAG1 t/ TAG2...]` | `add CS2103 exam t/ HIGH`<br> `add project, "CS2103 project", tags: meeting`
List upcominng incomplete tasks | `list` |
List expired tasks | `list expired` |
List all tasks | `list all` |
List all incomplete tasks | `list incomplete` |
List complete tasks | `list complete` |
Clear all tasks | `clear all` |
Clear expired tasks | `clear expired` |
Clear incomplete tasks | `clear incomplete` |
Clear complete tasks | `clear complete` |
Edit task | `edit INDEX [n/ TASK_NAME] [s/ START_DATE_TIME] [e/ END_DATE_TIME]  [+t/ TAG] [-t/ TAG]` | `edit 1 +t/ HIGH`
Delete task | `delete INDEX` | `delete 1`
Find by keywords or tags | `find KEYWORD [MORE_KEYWORDS]` | `find CS2103 Exams`
Undo the last action | `undo` |
Redo the last undo | `redo` |
Mark incomplete tasks as completed | `mark INDEX` | `mark 1`
Unmark completed task as incomplete | `unmark INDEX` | `unmark 1`
Reset a event or deadline task | `reset INDEX` | `reset 1`
Display all events and deadlines within reminder period | `remind` |
Set reminder period | `remind TIMEPERIOD` | `remind 2 day`
Select and view tasks | `select INDEX` | `select 1`
Check file path of the storage file | `filepath` |
Change the path of the storage file | `changepath [path_directory]` | `changepath User`<br> `changepath C:\Users\User\Desktop`
Exit the programme | `exit` |
