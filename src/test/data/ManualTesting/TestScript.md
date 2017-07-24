# Manual Testing : Test Script
##Steps to load the sample data
1. Navigate to TickTask folder in file explorer
2. Ensure that both the TickTask.jar and SampleData.xml are in the same folder
3. Rename the current  SampleData.xml into whatnow.xml
4. Launch TickTask.jar

###Add Command
 Command: `add Buy flowers for Rachel #love` <br>
 Expected: New task added: Buy flowers for Rachel Time:  Task Type: floating Date:  Tags: [love] <br> 
 
 Command: `add End of year family chalet from friday to sunday`<br>
 Expected: New task added: End of year family chalet Time: 00:00 - 23:59 Task Type: event Date: 07/28/2017 - 07/30/2017 Tags: [family]<br>
 
 Command: `add Call OCBC Bank by today at 3pm` <br>
 Expected: New task added: Call OCBC Bank Time: 15:00 Task Type: deadline Date: 07/24/2017 Tags:<br>
 
 Command: `add Study for final paper from tomorrow to sunday at 2pm to 5pm` <br>
 Expected: New task added: Fly kite in marina bay from 10/11/2016 12:00am to 13/11/2016 5:00pm  <br>
 
 Command: `add Fix computer by tmr` <br>
 Expected: New task added: Fix computer Time: 23:59 Task Type: deadline Date: 07/25/2017 Tags:"  <br>
 
 Command: `add Fix bicycle by tmr` <br>
 Expected: New task added: Fix computer Time: 23:59 Task Type: deadline Date: 07/25/2017 Tags:"  <br>
 
 Command: `add CS2103 Final Paper on Friday at 2:30pm to 4:30pm` <br>
 Expected: New task added: CS2103 Final Paper Time: 14:30 - 16:30 Task Type: event Date: 07/28/2017 Tags:  <br>
 
 Command: `add inv@l!d ` <br>
 Expected: Task names should only contain alphanumeric characters and spaces, and it should not be blank  <br>
 

###Delete Command
 Command: `delete schedule 1` <br>
 Expected: Deleted Task: Holiday in Denmark from 09/10/2016 04:45am to 14/10/2016 06:45pm  <br>
 
 Command: `delete todo 1` <br>
 Expected: Deleted Task: Buy milk  <br>
 
###Done Command
 Command: `done todo 2` <br>
 Expected: Task marked as completed: Sleep like a log  <br>
 
 Command: `done schedule 2` <br>
 Expected: Task marked as completed: Overseas students coming over from 12/10/2016 to 14/10/2016 <br>

###List Command
 Command: `list done` <br>
 Expected: Listed all completed tasks <br>
 
 Command: `list` <br>
 Expected: Listed all incomplete tasks <br>
 
 Command: `list all` <br>
 Expected: Listed all tasks <br>
 
###Undone Command
 Command: `undone schedule 1` <br>
 Expected: Task marked as incompleted: Buy groceries on 12/10/2016 <br>
 	
###Clear Command
 Command: `clear` <br>
 Expected: WhatNow has been cleared! <br>
 
###Undo Command
 Command: `undo` <br>
 Expected: Undo Successfully <br>
 
 Command: `add "Cookies for all"` <br>
 Expected: New task added: Cookies for all <br>
 
 Command: `undo` <br>
 Expected: Undo Successfully <br>
 
###Redo Command
 Command: `redo` <br>
 Expected: Redo Successfully <br>
 
###FreeTime command
 Command: `freetime 17/10/2016` <br>
 Expected: No freetime was found for: 17/10/2016 <br>

###Pin Command
 Command: `pin date 17/10/2016` <br>
 Expected: Pinned Item view updated! <br>
 
 Command: `pin date none` <br>
 Expected: Pinned Item view updated! <br>
 
 Command: `pin tag high` <br>
 Expected: Pinned Item view updated! <br>

###Update Command
 Command: `update todo 6 date 30/12/2016` <br>
 Expected: Updated Task: 
From: Eat chocolate 
To: Eat chocolate on 30/12/2016 <br>

 Command: `undo` <br>
 Expected: Undo Successfully <br>

 Command: `redo` <br>
 Expected: Redo Successfully <br>
 
###Change Command

 Command: `change location to C:\Users\lim\Desktop` <br>
 Expected: The data storage location has been successfully changed to: C:\Users\lim\Desktop\whatnow.xml <br>

###Undo Command
 Command: `undo` <br>
 Expected: Successfully able to undo to ./data/whatnow.xml <br>

###Find Command
 Command: `find tour` <br>
 Expected: 1 tasks listed! <br>
  
###Help Command
 Command: `help` <br>
 Expected: Direct to user guide <br>
 
###Exit Command
 Command: `exit` <br>
 Expected: Exit from WhatNow
