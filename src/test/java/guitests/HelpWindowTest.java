package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.HelpWindowHandle;

public class HelpWindowTest extends TaskManagerGuiTest {

    @Test
    public void openHelpWindow() {
        commandBox.pressEnter();
        //use accelerator
        commandBox.clickOnTextField();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        resultDisplay.clickOnTextArea();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        eventListPanel.clickOnListView();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        deadlineListPanel.clickOnListView();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());

        floatingListPanel.clickOnListView();
        assertHelpWindowOpen(mainMenu.openHelpWindowUsingAccelerator());
    }

    private void assertHelpWindowOpen(HelpWindowHandle helpWindowHandle) {
        assertTrue(helpWindowHandle.isWindowOpen());
        helpWindowHandle.closeWindow();
    }
}
