# WhatsNext(Task Manager Application) - Developer Guide

By: T01-T4  &nbsp;&nbsp;&nbsp;&nbsp;  Since: Jun 2017 &nbsp;&nbsp;&nbsp;&nbsp;  License: MIT

---

1. [Setting Up](#1-setting-up)
2. [Design](#2-design)
3. [Implementation](#3-implementation)
4. [Testing](#4-testing)
5. [Dev Ops](#5-dev-ops)

* [Appendix A: User Stories](#appendix-a--user-stories)
* [Appendix B: Use Cases](#appendix-b--use-cases)
* [Appendix C: Non Functional Requirements](#appendix-c--non-functional-requirements)
* [Appendix D: Glossary](#appendix-d--glossary)
* [Appendix E: Product Survey](#appendix-e--product-survey)

## 1. Setting up

### 1.1. Prerequisites

1. **JDK `1.8.0_131`**  or later<br>

    > Having any Java 8 version is not enough. <br>
    This app will not work with earlier versions of Java 8.

2. **Eclipse** IDE
3. **e(fx)clipse** plugin for Eclipse (Do the steps 2 onwards given in
   [this page](http://www.eclipse.org/efxclipse/install.html#for-the-ambitious))
4. **Buildship Gradle Integration** plugin from the Eclipse Marketplace
5. **Checkstyle Plug-in** plugin from the Eclipse Marketplace

### 1.2. Importing the project into Eclipse

0. Fork this repo, and clone the fork to your computer
1. Open Eclipse (Note: Ensure you have installed the **e(fx)clipse** and **buildship** plugins as given in the prerequisites above)
2. Click `File` > `Import`
3. Click `Gradle` > `Gradle Project` > `Next` > `Next`
4. Click `Browse`, then locate the project's directory
5. Click `Finish`

  > * If you are asked whether to 'keep' or 'overwrite' config files, choose to 'keep'.
  > * Depending on your connection speed and server load, it can even take up to 30 minutes for the set up to finish
      (This is because Gradle downloads library files from servers during the project set up process)
  > * If Eclipse auto-changed any settings files during the import process, you can discard those changes.

### 1.3. Configuring Checkstyle
1. Click `Project` -> `Properties` -> `Checkstyle` -> `Local Check Configurations` -> `New...`
2. Choose `External Configuration File` under `Type`
3. Enter an arbitrary configuration name e.g. whatsnext
4. Import checkstyle configuration file found at `config/checkstyle/checkstyle.xml`
5. Click OK once, go to the `Main` tab, use the newly imported check configuration.
6. Tick and select `files from packages`, click `Change...`, and select the `resources` package
7. Click OK twice. Rebuild project if prompted

> Note to click on the `files from packages` text after ticking in order to enable the `Change...` button

### 1.4. Troubleshooting project setup

**Problem: Eclipse reports compile errors after new commits are pulled from Git**

* Reason: Eclipse fails to recognize new files that appeared due to the Git pull.
* Solution: Refresh the project in Eclipse:<br>
  Right click on the project (in Eclipse package explorer), choose `Gradle` -> `Refresh Gradle Project`.

**Problem: Eclipse reports some required libraries missing**

* Reason: Required libraries may not have been downloaded during the project import.
* Solution: [Run tests using Gradle](UsingGradle.md) once (to refresh the libraries).

## 2. Design

### 2.1. Architecture

Author: Lui Sheng Jie

**WhatsNext** is a stand alone CRUD (Create, Read, Update, Delete) Desktop application.

<img src="images/Architecture.png" width="600"><br>
_Figure 2.1.1 : Architecture Diagram_

The **_Architecture Diagram_** given above explains the high-level design of the App.

protected Ui ui;
    protected Logic logic;
    protected Storage storage;
    protected Model model;
    protected Config config;
    protected UserPrefs userPrefs;

**Architecture Design of WhatsNext**
1. Singleton Pattern: The MainApp of the program restricts the number of instantiated `Logic`, `Storage`, `Model`, `Config`, `UserPrefs` objects created.
2. MVC pattern: The application makes use of MVC to decouple data, presentation and control logic.
  * Model: `model`, `storage` component
  * View: `GUI`
  * Controller: `logic` component
3. Observer Pattern: The application makes use of javafx ObservableList which allows listeners to track changes when they occur. E.g. Ui updates when there is a taskManagerChangeEvent raised.
4. Facade Pattern: `Ui` component of the application requires access to the startDateTime and endDateTime objects in BasicTask class from the `Model` component.
5. Command Pattern: Abstract class command is used to support multiple commands each performing a different tasks (e.g. `add`, `delete`, `list` commands).

A top down approach is used to design the Architecture of the app to better faciliate the Object Oriented framework used.
Given below is a quick overview of each component.

[**`Main`**] consists of a single class called [`MainApp`](../src/main/java/seedu/whatsnext/MainApp.java) which is responsible for,

1. At app launch: Initializes the `Ui`, `Logic`, `Storage`, `Model`, `Config` and `UserPrefs` components in the correct sequence, and connects them up with each other. It also ensures that prefs file is updated in the case where it is missing or when there are new/unused fields.
2. At shut down: Shuts down the components, saves the `UserPrefs` and invokes cleanup method where necessary.

[**`Commons`**] represents a collection of classes used by multiple other components.
The `Commons` component contains utility code used across other components
* `EventsCenter` : This class (written using [Google's Event Bus library](https://github.com/google/guava/wiki/EventBusExplained))
  is used by components to communicate with other components using events (i.e. a form of _Event Driven_ design)
* `LogsCenter` : Used by many classes to write log messages to the App's log file.
* `UnmodifiedObservableList` : Unmodifiable view of an observable list is used to prevent illegal changes to be done to its data.

The rest of the App consists of four components.

1. [**`UI`**] : The UI of the App. Makes use of the data stored in `Model` to display Tasks.
2. [**`Logic`**] : The command executor. Parse the arguments and calls the respective Command objects.
3. [**`Model`**] :
  * Holds the data of the App in-memory which is used to display the `Ui`.
  * Model utilizes third party library Pretty Time Parser to parse Strings to Date objects.
4. [**`Storage`**] : Reads data from, and writes data to, the hard disk. This allows the app to retain added tasks even after the program is closed.

Each of the four components

* Defines its _API_ in an `interface` with the same name as the Component.
* Exposes its functionality using a `{Component Name}Manager` class.

For example, the `Logic` component (see the class diagram given below) defines it's API in the `Logic.java`
interface and exposes its functionality using the `LogicManager.java` class.<br>
<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.1.2 : Class Diagram of the Logic Component_

#### Events-Driven nature of the design

The _Sequence Diagram_ below shows how the components interact for the scenario where the user issues the
command `delete 1`.

<img src="images/SDforDeleteTask.png" width="800"><br>
_Figure 2.1.3a : Component interactions for `delete 1` command (part 1)_

>Note how the `Model` simply raises a `TaskManagerChangedEvent` when the Task Manager data are changed,
 instead of asking the `Storage` to save the updates to the hard disk.

The diagram below shows how the `EventsCenter` reacts to that event, which eventually results in the updates
being saved to the hard disk and the status bar of the UI being updated to reflect the 'Last Updated' time. <br>
<img src="images/SDforDeleteTaskEventHandling.png" width="800"><br>
_Figure 2.1.3b : Component interactions for `delete 1` command (part 2)_

> Note how the event is propagated through the `EventsCenter` to the `Storage` and `UI` without `Model` having
  to be coupled to either of them. This is an example of how this Event Driven approach helps us reduce direct
  coupling between components.

The sections below give more details of each component.

### 2.2. UI component

Author: Aung Swumm Htet Pyi Aye

<img src="images/UiClassDiagram.png" width="800"><br>
_Figure 2.2.1 : Structure of the UI Component_

**API** : [`Ui.java`](../src/main/java/seedu/whatsnext/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `EventListPanel`, `StatusBarFooter` etc. The three panels, `EventListPanel`, `DeadlineListPanel` and `FloatingListPanel`, hosts their own respective cards, `EventTaskCard`, `DeadlineTaskCard` and `FloatingTaskCard` listviews. 'All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files
 that are in the `src/main/resources/view` folder.<br>
 For example, the layout of the [`MainWindow`](../src/main/java/seedu/whatsnext/ui/MainWindow.java) is specified in
 [`MainWindow.fxml`](../src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Binds itself to some data in the `Model` so that the UI can auto-update when data in the `Model` change.
* Responds to events raised from various parts of the App and updates the UI accordingly.

### 2.3. Logic component

Author: Tay Chi Shien

<img src="images/LogicClassDiagram.png" width="800"><br>
_Figure 2.3.1 : Structure of the Logic Component_

**API** : [`Logic.java`](../src/main/java/seedu/whatsnext/logic/Logic.java)

1. `Logic` uses the `Parser` class to parse the user command.
2. This results in a `Command` object which is executed by the `LogicManager`.
3. The command execution can affect the `Model` (e.g. adding a task) and/or raise events.
4. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")`
 API call.<br>
<img src="images/DeleteTaskSdForLogic.png" width="800"><br>
_Figure 2.3.2 : Interactions Inside the Logic Component for the `delete 1` Command_

The Command section of the Logic component utilises the Open-Closed Principle whereby one can create other new types of command easily without the need to modify current codes via the Parent Abstract class [`Command`](../src/main/java/seedu/whatsnext/logic/commands/Command.java). Thus, the Command section illustrates the principle whereby it is "open for extensions but closed for modification".

<img src="images/OpenCloseDiagram.png" width="800"><br>
_Figure 2.3.3 : Open-Closed Principle within the Logic Component under Command section_

### 2.4. Model component

Author: Li Shicheng

<img src="images/ModelClassDiagram.png" width="800"><br>
_Figure 2.4.1 : Structure of the Model Component_

**API** : [`Model.java`](../src/main/java/seedu/whatsnext/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user's preferences.
* stores the current and previous Task Manager data; current Task Manager data will be on display in the UI and will be in sync with the storage file, while previous Task Manager data will enable the user to revert the changes in the current session.
* exposes a `UnmodifiableObservableList<BasicTaskFeatures>` that can be 'observed' e.g. the UI can be bound to this list
  so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.

**Undo/Redo functions** <br>
Undo and Redo functions are implemented using two stacks: `undoTaskManager` and `redoTaskManager`.<br>

<img src="images/UndoCommandSequenceDiagram.png" width="800"><br>
_Figure 2.4.2 : Sequence of Undo Command_

`UndoTaskManagers` stores the instances of TaskManager data when data changes are made. It enables user to undo multiple data-mutating commands by restoring the instances of the data in the stack. As the stack is initialized as empty when the app starts to run, it will only undo the changes in the current session. <br>

<img src="images/RedoCommandSequenceDiagram.png" width="800"><br>
_Figure 2.4.3 : Sequence of Redo Command_

`RedoTaskManagers` also stores instances of TaskManager data, but only when undo commands are called. Functioning in the same way as undoTaskManager, it restores previous instances of data before the undo commands. It is an empty stack during initialization and will only redo the undo commands in the current session. <br>

**Reserved Tags** <br>
To indicate priority of a certain task, the system set aside three reserved tags -- `HIGH`, `MEDIUM`, `LOW` -- to indicate descending priorities. The reserved tags will always displayed as the first tag among the tag list. And due the nature of such tags, one task will only have one priority tag at any time. For easier use, the user does not have to manually delete the current priority tag, and rather add a new priority tag and the app will automatically replace the previous tag.

### 2.5. Storage component

Author: Lim Dao Han

<img src="images/StorageClassDiagram.png" width="800"><br>
_Figure 2.5.1 : Structure of the Storage Component_

**API** : [`Storage.java`](../src/main/java/seedu/whatsnext/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save the Task Manager data in xml format and read it back.

### 2.6. Common classes

Classes used by multiple components are in the `seedu.whatsnext.commons` package.

## 3. Implementation

### 3.1. Logging

We are using `java.util.logging` package for logging. The `LogsCenter` class is used to manage the logging levels
and logging destinations.

* The logging level can be controlled using the `logLevel` setting in the configuration file
  (See [Configuration])
* The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to
  the specified logging level
* Currently log messages are output through: `Console` and to a `.log` file.

**Logging Levels**

* `SEVERE` : Critical problem detected which may possibly cause the termination of the application
* `WARNING` : Can continue, but with caution
* `INFO` : Information showing the noteworthy actions by the App
* `FINE` : Details that is not usually noteworthy but may be useful in debugging
  e.g. print the actual list instead of just its size

### 3.2. Configuration

Certain properties of the application can be controlled (e.g App name, logging level) through the configuration file
(default: `config.json`):

## 4. Testing

Tests can be found in the `./src/test/java` folder.

**In Eclipse**:

* To run all tests, right-click on the `src/test/java` folder and choose
  `Run as` > `JUnit Test`
* To run a subset of tests, you can right-click on a test package, test class, or a test and choose
  to run as a JUnit test.

**Using Gradle**:

* See [UsingGradle.md](UsingGradle.md) for how to run tests using Gradle.

We have two types of tests:

1. **GUI Tests** - These are _System Tests_ that test the entire App by simulating user actions on the GUI.
   These are in the `guitests` package.

2. **Non-GUI Tests** - These are tests not involving the GUI. They include,
   1. _Unit tests_ targeting the lowest level methods/classes. <br>
      e.g. `seedu.whatsnext.commons.UrlUtilTest`
   2. _Integration tests_ that are checking the integration of multiple code units
     (those code units are assumed to be working).<br>
      e.g. `seedu.whatsnext.storage.StorageManagerTest`
   3. Hybrids of unit and integration tests. These test are checking multiple code units as well as
      how the are connected together.<br>
      e.g. `seedu.whatsnext.logic.LogicManagerTest`

#### Headless GUI Testing
Thanks to the [TestFX](https://github.com/TestFX/TestFX) library we use,
 our GUI tests can be run in the _headless_ mode.
 In the headless mode, GUI tests do not show up on the screen.
 That means the developer can do other things on the Computer while the tests are running.<br>
 See [UsingGradle.md](UsingGradle.md#running-tests) to learn how to run tests in headless mode.

### 4.1. Troubleshooting tests

 **Problem: Tests fail because NullPointException when AssertionError is expected**
 * Reason: Assertions are not enabled for JUnit tests.
   This can happen if you are not using a recent Eclipse version (i.e. _Neon_ or later)
 * Solution: Enable assertions in JUnit tests as described
   [here](http://stackoverflow.com/questions/2522897/eclipse-junit-ea-vm-option).<br>
   Delete run configurations created when you ran tests earlier.

## 5. Dev Ops

### 5.1. Build Automation

See [UsingGradle.md](UsingGradle.md) to learn how to use Gradle for build automation.

### 5.2. Continuous Integration

We use [Travis CI](https://travis-ci.org/) and [AppVeyor](https://www.appveyor.com/) to perform _Continuous Integration_ on our projects.
See [UsingTravis.md](UsingTravis.md) and [UsingAppVeyor.md](UsingAppVeyor.md) for more details.

### 5.3. Publishing Documentation

See [UsingGithubPages.md](UsingGithubPages.md) to learn how to use GitHub Pages to publish documentation to the
project site.

### 5.4. Making a Release

Here are the steps to create a new release.

 1. Generate a JAR file [using Gradle](UsingGradle.md#creating-the-jar-file).
 2. Tag the repo with the version number. e.g. `v0.1`
 2. [Create a new release using GitHub](https://help.github.com/articles/creating-releases/)
    and upload the JAR file you created.

### 5.5. Converting Documentation to PDF format

We use [Google Chrome](https://www.google.com/chrome/browser/desktop/) for converting documentation to PDF format,
as Chrome's PDF engine preserves hyperlinks used in webpages.

Here are the steps to convert the project documentation files to PDF format.

 1. Make sure you have set up GitHub Pages as described in [UsingGithubPages.md](UsingGithubPages.md#setting-up).
 1. Using Chrome, go to the [GitHub Pages version](UsingGithubPages.md#viewing-the-project-site) of the
    documentation file. <br>
    e.g. For [UserGuide.md](UserGuide.md), the URL will be `https://<your-username-or-organization-name>.github.io/addressbook-level4/docs/UserGuide.html`.
 1. Click on the `Print` option in Chrome's menu.
 1. Set the destination to `Save as PDF`, then click `Save` to save a copy of the file in PDF format. <br>
    For best results, use the settings indicated in the screenshot below. <br>
    <img src="images/chrome_save_as_pdf.png" width="300"><br>
    _Figure 5.4.1 : Saving documentation as PDF files in Chrome_

### 5.6. Managing Dependencies

A project often depends on third-party libraries. For example, Task Manager depends on the
[Jackson library](http://wiki.fasterxml.com/JacksonHome) for XML parsing. Managing these _dependencies_
can be automated using Gradle. For example, Gradle can download the dependencies automatically, which
is better than these alternatives.<br>
a. Include those libraries in the repo (this bloats the repo size)<br>
b. Require developers to download those libraries manually (this creates extra work for developers)<br>

## Appendix A : User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have)  - `* *`,  Low (unlikely to have) - `*`

* Task in the following table refers to all three types of tasks involved: Floating; Deadlines; Events.

Priority | As a ... | I want to ... | So that I can...
-------- | :-------- | :--------- | :-----------
`* * *` | new user | see command instructions | refer to instructions when I forget how to use the App
`* * *` | user | create a event | keep track of task with both start and end time
`* * *` | user | create a deadline task | keep track of deadline I have to meet
`* * *` | user | create a new task which does not have time constrains | keep track of tasks without particular schedule, such as reading a novel
`* * *` | user | edit the information in the task | make sure the information is up-to-date
`* * *` | user | change a floating into deadline or events and vice versa | track the task accordingly
`* * *` | user | delete an existing task | no longer keep track of tasks I do not care about
`* * *` | user | search a task by name or by tag | locate task without having to go through the entire list
`* * *` | user | undo a recent action | remove my mistakes
`* * *` | user | redo the recent undo | revert any accidental undo
`* * *` | user | add priority marks to a task | know which tasks require more attention
`* * *` | user | list my completed tasks | review details of previous tasks
`* * *` | user | specify a storage folder | know where my data will be saved
`* * *` | user | see conflicts in events | resolve them earlier
`* * ` | user | set reminders | see all the tasks due in the period I set upon starting the app
`* *` | advanced user | use shortcut commands | utilize the app faster
`* *` | user | color code my tasks | differentiate the tasks easier
`*` | user | share notes with others | keep tasks updated among a group of people
`*` | user | share my schedule | let others know my occupied and unoccupied timeslots


## Appendix B : Use Cases

(For all use cases below, the **System** is the `WhatsNext` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Add event task

**MSS**

1.  User requests to create event task by specifying both start and end DateTime.
2.  System creates the event task and list it under the event task on UI.<br>
Use case ends.

**Extensions**

2a. There is already a event task during that time period.

> 2a1. System will tag the overlapping events with reserved tag `OVERLAP`<br>
> Use case ends

2b. The user input is not in the valid command format.

> 2b1. System will indicate to user that the input is invalid and display help information. <br>
> Use case resumes at step 1.

#### Use case: Add deadline task

**MSS**

1.  User requests to create event task by specifying end DateTime.
2.  System creates the deadline task and list it under the deadline task on UI.<br>
Use case ends.

**Extensions**

2a. The user input is not in the valid command format.

> 2a1. System will indicate to user that the input is invalid and display help information. <br>
> Use case resumes at step 1.

#### Use case: Add floating task

**MSS**

1.  User requests to create floating task by not specifying any date or time.
2.  System creates the floating task and list it under the floating task on UI.<br>
Use case ends.

**Extensions**

2a. The user input is not in the valid command format.

> 2a1. System will indicate to user that the input is invalid and display help information. <br>
> Use case resumes at step 1.

#### Use case: Delete task

**MSS**

1.  User requests the whole task list or request to find tasks by names or tags.
2.  System shows the whole task list or shows the relevant task lists.
3.  User specifies the task to be deleted.
4.  System deletes the task.<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

2b. There is no task with the given names or tags

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message <br>
  Use case resumes at step 2

#### Use case: Clear tasks by status(completed, incomplete, expired or all)

**MSS**

1. User request to clear task of a particular status.
2. System clear the tasks specified by the user and display success message.

#### Use case: Find task

**MSS**

1.  User inputs keywords.
2.  System shows tasks with query keywords in name or tag.<br>

Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

2b. The given query word is not found

> 2b1. System shows an "not found" message <br>
> Use case ends

#### Use case: Mark task completed

**MSS**

1.  User requests the task list.
2.  System shows the task list.
3.  User requests to mark specific task completed.
4.  System marks the specified task completed.<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. System shows an error message <br>
> Use case resumes at step 2

#### Use case: Undo last action

**MSS**

1.  User requests to undo last action.
2.  System reverts back to state before last action.<br>
Use case ends.

**Extensions**

2a. There is no previous action done.

> 2a1. System shows a "No actions to undo" message <br>
> Use case ends

#### Use Case: Redo last Undo

**MSS**

1.  User request to redo the action reverted by undo.
2.  System revert back to the state before undo command. <br>
Use case ends.

**Extensions**

2a. There is no previous undo action done.

> 2a1. System shows a "Nothing to redo" message <br>
> Use case ends

#### Use case: List tasks by status (upcoming incomplete, completed, incomplete, expired or all)

**MSS**

1.  User requests to view tasks by status (upcoming incomplete, completed, incomplete, expired or all).
2.  System lists all tasks of the type.<br>
Use case ends.

**Extensions**

2a. The list of that specific type is empty

> Use case ends

#### Use case: Edit tasks

**MSS**

1.  User requests the task list.
2.  System shows the task list.
3.  User requests to edit specific task's particular field.
4.  System edits the specified task.<br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given edit format is invalid

> 3a1. System shows an error message <br>
> Use case resumes at step 2

#### Use case: Reset task

**MSS**

1. User requests the task list.
2. System shows the task list.
3. User requests to reset the specific event or deadline.
4. System reset the specified task. <br>
Use case ends.

**Extensions**

2a. The list is empty

> Use case ends

3a. The given task is already a floating task

>3a1. System shows that the task is already a floating task<br>
> Use case resume at step 2

#### Use case: Set reminder period

**MSS**

1. User requests to set the reminder period.
2. System set and save the reminder period, and display the changed reminder period.<br>
Use case ends.

**Extensions**

1a. User input of the time period is invalid.

>1a1. System shows an error message and display help information.
> Use case ends.

#### Use case: Display events and deadlines within reminder period

**MSS**

1. User requests to display events and deadlines within reminder period.
2. System displays events and deadlines within reminder period.<br>
Use case ends.

#### Use case: Update storage file path

**MSS**

1. User request the storage file to be in a new file path.
2. System change the file path and delete the storage in the original path.<br>
Use case ends.

**Extensions**

1a. The new file path is invalid.

> 1a1. System display error message <br>
> Use case resumes at step 2


#### Use case: Exit application

**MSS**

1.  User requests to exit the application.
2.  System exits.<br>
Use case ends.


## Appendix C : Non Functional Requirements

1. Should work on any [mainstream OS] (#mainstream-os) as long as it has Java `1.8.0.131` or higher installed.
2. Should be able to store and process 1000 tasks without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. Should come with automated unit tests and open source code.
5. Should favor DOS style commands over Unix-style commands.

## Appendix D : Glossary

##### Mainstream OS

> Windows, Linux, Unix, OS-X

##### Reserved Tags

> System defined tags that cannot be modified and deleted. E.g. `HIGH` = high priority task tag; `MEDIUM` = medium priority task tag; `LOW` = low priority task tag; `OVERLAP` = to indicate events having clashing schedules.

##### Events

> Tasks that have a date, both starting and ending time. Overlapping events are allowed, but will be tagged by the system as `OVERLAP` to warn users.

##### Deadlines

> Tasks that have a date, but may have an ending time. If the ending time is not specified by the user, it will be set to 2359 by default. There are can be multiple deadlines at the same time.

##### Floating

> Tasks that do not have a particular time frame.

{More to be added}

## Appendix E : Product Survey

**Product Name**: Google Keep

Author: Lui Sheng Jie

Pros:

* Clean, simple & easy to use GUI
* Able to access notes when offline (from places where there is no ready Internet connectivity.)
* Color codes to organise notes
* Able to collaborate and share notes
* Easy to use search bar

Cons:

* No customizable keyword tags available
* Unable to sort notes based on tags/keywords (level of importance)

---

**Product Name**: ToDoist

Author: Lui Sheng Jie

Pros:

* Email reminder
* Supports natural language input like "tomorrow," "next month," "in 3 weeks," or for recurring tasks, "every week" or "every 3 days."
* Able to collaborate and share notes
* Easy to use search bar

Cons:

* No filtering (display by tags etc)
* Does not support a start date
* Features such as reminders, comments and file uploads require premium upgrades.

---

**Product Name**: Apple Calendar

Author: Shicheng

Pros:

* Simplistic graphic user interface
* Allow user to add notes
* Allow user to set reminders and alarms
* Synchronize with users' email contacts to reminder users of his/her contacts' birthday

Cons:

* Does not differentiate tasks by priorities
* Does not differentiate user-defined tasks and default festivals and holidays on the overall display (i.e. Chinese new year looks the same as project deadline)
* Does not allow event that lasts for more than a day (i.e. event time are set by hours on a specific day or all day.)

---

**Product Name**: Trello

Author: Chi Shien

Pros:
* Free
* Simple dashboard style GUI, sync with gmail
* Each board has cards
* Each card represents a group of tasks
* Each task add/change title, description, members, checklist, labels, due date, attachments, comments
* Extra features like calendar view and card voting, stickers, etc
* Allows click-drag
* Daily to do list across all boards

Cons:

* Cannot rank tasks by importance (though you can colour code them)
* Requires heavily use of mouse to navigate
* No keyboard shortcuts available
---

**Product Name**: HiTask

Author: Aung Swumm

Pros:
* Free
* Can set recurring task
* Can set a time tracker or a time estimator
* Can color-code tasks for ease of viewing
* Send reminder alerts to phone app or email

Cons:

* Cannot sync with google calendar on android phones

---

**Product Name**: Google Calendar

Author: Dao Han

Pros:
* Handles reminders, appointments and events
* Ease of setting up reminders
* Sets reminder when to leave location for appointment
* External apps such as Wix can sync with calendar
* Can view other people's calendars if they share it
* Color coding for events

Cons:

* Plain interface
* If too many events, may be hidden unless specific day is opened
* Not every email automatically downloaded into calendar
