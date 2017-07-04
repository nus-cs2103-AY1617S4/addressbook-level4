# WhatsNext - User Guide

By : `Team 4`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Jun 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

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
   * **`add d/July 10 t/5-6 e/project meeting`** adds an `Event` task to your task manager.
   * **`delete`**` 1` : deletes the 1st task shown in your current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


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
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd` or the keyword `help`

### 2.2. Adding a task: `add`

Adds an (1) event, (2) deadline or (3) floating to the task manager<br>
Format: <br> 
>`(1) add d/DATE t/TIME n/TASK_NAME [h/TAG]...` <br>
>`(2) add d/DATE n/TASK_NAME [h/TAG]...` <br>
>`(3) add n/TASK_NAME [h/TAG]...` <br>
> Tasks can have any number of tags (including 0)
Smart Add: <br>
>`(1) add TASK_TYPE TASK_PARAMETERS` <br>
> TASK_TYPE **must match task type** (1) event, (2) deadline or (3) floating <br>
> TASK_PARAMETERS **must match task parameters of task type** 

Examples:

* `add d/July 10 t/5-6 e/project meeting`
* `add d/July 10 n/project meeting`
* `add n/project meeting h/CS2103`
* `add n/project meeting h/CS2103 h/SPECIAL_TERM`
<br>
* `add event July 10, 5-6, project meeting`

### 2.3. Listing tasks : `list`

Shows a list of (1) incomplete, (2) complete, (3)all tasks in the task manager.<br>
Format:  <br> 
>`(1) list `  or <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`list /i`<br>
>`(2) list /c` <br>
>`(3) list /a` <br>

### 2.4. Editing a task : `edit`

Edits an existing task in the task manager.<br>
Format: `edit INDEX [d/DATE] [t/TIME] [n/TASK_NAME] [h/TAG]...`

> * Edits a task at the specified `INDEX`.
    The index refers to the index number shown in the last task listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Option fields **must match task type** (1) event, (2) deadline or (3) floating
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all task's tags by typing `t/` without specifying any tags after it. 

Examples:
* `edit 1 d/July 10 t/5-10`<br>
  Edits the date and time of the 1st task (event) to be `July 10` and `5-10` respectively.

* `edit 2 n/cs2103 submission meeting t/`<br>
  Edits the task name of the 2nd task (deadline) to be `cs2103 submission` and clears all existing tags.

### 2.5. Finding all tasks containing any keyword in their name or tags: `find`

Finds tasks whose names or tags contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `meeting` will match `Meeting`
> * The order of the keywords does not matter. e.g. `submission meeting` will match `submission meeting`
> * Only the name and tags are searched.
> * Only full words will be matched e.g. `meeting` will not match `meetings`
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `meeting` will match `submission meeting`

Examples:

* `find submission`<br>
  Returns `submission meeting` but not `submission`
* `find CS2103`<br>
  Returns Any person having tags `CS2103`

### 2.6. Deleting a task : `delete`

Deletes the specified task from the task manager. Irreversible.<br>
Format: `delete INDEX`

> Deletes the task at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd task in the task manager.
* `find CS2103`<br>
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.7. Undo last action : `undo`

Undo the last action performed by the user.<br>
Format: `undo`


### 2.8. Clearing tasks : `clear`
Clears (1) incomplete, (2) complete, (3)all tasks in the task manager.<br>
Clears all entries from the task manager.<br>
Format: `clear`
>`(1) clear /i` <br>
>`(2) clear `  or <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`clear /c`<br>
>`(3) clear /a` <br>

### 2.9. Marking tasks : `mark`
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
  
### 2.10. Marking tasks : `unmark`
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
  
### 2.11. Sorting tasks : `sort`
Sort the tasks on the task manager<br>
Format: `sort tt/TASK_TYPE [DATE] [IMPORTANCE] [ALPHABETICALLY]`
> sort the task of the specified `TASK_TYPE`. <br>
> The TASK_TYPE **must be a valid task type** `events`, `deadline`, `floating` .

* `sort events important`<br>
  Sorts the events task in the task manager by importance.
* `sort events date`<br>
  Sorts the events task in the task manager by date and time.
* `sort deadline date`<br>
  Sorts the deadline task in the task manager by date.
* `sort floating alpha`<br>
  Sorts the floating task in the task manager alphabetically.

### 2.11. View current data file path : `viewPath`

View data file directory path. <br>
Format: `viewPath` 
> View the directory where the data file is saved <br>

### 2.11. Updating data file

Updates data file directory. : `updatePath`<br>
Format: `updatePath [Path Directory]` 
> Updates the directory where the data file is saved <br>
Task Manager data are saved in the specified path directory.<br>


### 2.12. Saving the data

Task Manager data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

### 2.13. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Task Manger folder.

## 4. Command Summary

* **Add**  `add d/DATE t/TIME n/TASK_NAME [h/TAG]...` ` <br>
  e.g. `add d/July 10 t/5-6 e/project meeting`
  
* **List**  `list` <br>
  e.g.
  
* **Clear** : `clear`

* **Edit** : `edit INDEX` <br>
   e.g. `edit 3`

* **Delete** : `delete INDEX` <br>
   e.g. `delete 3`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
  e.g. `find CS2103 Exams`

* **Undo** : `undo` <br>
  e.g.
  
* **Mark** : `mark INDEX` <br>
  e.g. mark 1
  
* **Mark** : `unmark INDEX` <br>
  e.g. unmark 1
  
* **Sort** : `sort tt/TASK_TYPE [DATE] [IMPORTANCE] [ALPHABETICALLY]` <br>
  e.g. sort floating alpha

* **View Data File Path** : `viewPath` <br>
  e.g.
  
* **Update Data File Path** : `updatePath [Path Directory]` <br>
  e.g.
  
* **Exit** : `exit` <br>
  e.g.  
  
* **Help** : `help` <br>
  e.g.


