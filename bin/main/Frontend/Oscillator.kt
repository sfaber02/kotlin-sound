package frontend

import com.googlecode.lanterna.gui2.Component
import com.googlecode.lanterna.gui2.GridLayout
import com.googlecode.lanterna.gui2.Label
import com.googlecode.lanterna.gui2.Panel

fun makeOscillatorUI (): Component {
    var panel: Panel = Panel(GridLayout(2))
    
    val title: Label = Label("Oscillator")

    title.setLayoutData(GridLayout.createLayoutData(
        GridLayout.Alignment.BEGINNING, // Horizontal alignment in the grid cell if the cell is larger than the component's preferred size
        GridLayout.Alignment.BEGINNING, // Vertical alignment in the grid cell if the cell is larger than the component's preferred size
        true,       // Give the component extra horizontal space if available
        false,        // Give the component extra vertical space if available
        2,                  // Horizontal span
        1));

    panel.addComponent(title)






    return panel
}