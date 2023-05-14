package frontend

import com.googlecode.lanterna.TerminalSize
import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.gui2.*
import com.googlecode.lanterna.gui2.dialogs.MessageDialog
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton
import com.googlecode.lanterna.screen.TerminalScreen
import com.googlecode.lanterna.terminal.DefaultTerminalFactory
import com.googlecode.lanterna.terminal.Terminal
import java.io.IOException
import kotlinx.coroutines.*
import java.util.*


class Frontend() {
    val defaultTerminalFactory = DefaultTerminalFactory()
    val terminal: Terminal = defaultTerminalFactory.createTerminal()
    var screen: TerminalScreen = TerminalScreen(terminal)
    var textGraphics: TextGraphics = terminal.newTextGraphics();
    var gui: WindowBasedTextGUI = MultiWindowTextGUI(screen)
    var mainWindow: Window = BasicWindow("main")
    var contentPanel: Panel = Panel(GridLayout(2))
    var gridLayout = contentPanel.layoutManager as GridLayout
    var title = Label("This is a label that spans two columns")

    var rows: Int = screen.terminalSize.rows
    var cols: Int = screen.terminalSize.columns



    suspend fun buildUI() = runBlocking{
        screen.startScreen()
        screen.cursorPosition = null
        gridLayout.horizontalSpacing = 3

        title.setLayoutData(GridLayout.createLayoutData(
            GridLayout.Alignment.BEGINNING, // Horizontal alignment in the grid cell if the cell is larger than the component's preferred size
            GridLayout.Alignment.BEGINNING, // Vertical alignment in the grid cell if the cell is larger than the component's preferred size
            true,       // Give the component extra horizontal space if available
            false,        // Give the component extra vertical space if available
            2,                  // Horizontal span
            1));                  // Vertical span
        contentPanel.addComponent(title);

        contentPanel.addComponent(Label("Text Box (aligned)"))
        contentPanel.addComponent(
            TextBox()
                .setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.BEGINNING, GridLayout.Alignment.CENTER))
        )

        contentPanel.addComponent(Label("Read-only Combo Box (forced size)"))
        val timezonesAsStrings: ArrayList<String> = ArrayList<String>(TimeZone.getAvailableIDs().map{ i -> i.toString()})
        val readOnlyComboBox = ComboBox(timezonesAsStrings)
        readOnlyComboBox.isReadOnly = true
        readOnlyComboBox.preferredSize = TerminalSize(20, 1)
        contentPanel.addComponent(readOnlyComboBox)

        contentPanel.addComponent(Label("Editable Combo Box (filled)"))
        contentPanel.addComponent(
            ComboBox("Item #1", "Item #2", "Item #3", "Item #4")
                .setReadOnly(false)
                .setLayoutData(GridLayout.createHorizontallyFilledLayoutData(1))
        )

        contentPanel.addComponent(Label("Button (centered)"))
        contentPanel.addComponent(Button("Button") {
            MessageDialog.showMessageDialog(
                gui,
                "MessageBox",
                "This is a message box",
                MessageDialogButton.OK
            )
        }.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER)))

        contentPanel.addComponent(
            EmptySpace()
                .setLayoutData(
                    GridLayout.createHorizontallyFilledLayoutData(2)
                )
        )
        contentPanel.addComponent(
            Separator(Direction.HORIZONTAL)
                .setLayoutData(
                    GridLayout.createHorizontallyFilledLayoutData(2)
                )
        )
        contentPanel.addComponent(
            Button("Close", mainWindow::close).setLayoutData(
                GridLayout.createHorizontallyEndAlignedLayoutData(2)
            )
        )

        /*
            We now have the content panel fully populated with components. A common mistake is to forget to attach it to
            the window, so let's make sure to do that.
             */
        mainWindow.setComponent(contentPanel);


        /*
   Now the window is created and fully populated. As discussed above regarding the threading model, we have the
   option to fire off the GUI here and then later on decide when we want to stop it. In order for this to work,
   you need a dedicated UI thread to run all the GUI operations, usually done by passing in a
   SeparateTextGUIThread object when you create the TextGUI. In this tutorial, we are using the conceptually
   simpler SameTextGUIThread, which essentially hijacks the caller thread and uses it as the GUI thread until
   some stop condition is met. The absolutely simplest way to do this is to simply ask lanterna to display the
   window and wait for it to be closed. This will initiate the event loop and make the GUI functional. In the
   "Close" button above, we tied a call to the close() method on the Window object when the button is
   triggered, this will then break the even loop and our call finally returns.
    */

        launch {

            addWindow(mainWindow)
        }


        println ("passed add window")

        terminal.addResizeListener {terminal1: Terminal, newSize: TerminalSize ->
            println("new terminal size = ${newSize.rows}, ${newSize.columns}")
            rows = newSize.rows
            cols = newSize.columns
            try {
                terminal1.flush()
            } catch (e: IOException) {
                // Not much we can do here
                throw RuntimeException(e)
            }
        }
    }

    private suspend fun addWindow(mainWindow: Window) = coroutineScope {
        gui.addWindowAndWait(mainWindow);
    }
}