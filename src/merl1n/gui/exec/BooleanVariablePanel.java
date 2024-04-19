package merl1n.gui.exec;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import merl1n.es.*;
import merl1n.tool.*;



/**
 * The Variable panel enables define the value of variable
 *
 * @author Ivo Vondrak
 * @version 1.0 10/03/99
 */ 
public class BooleanVariablePanel extends VariablePanel {
    /**
     * Radio button
     */
    protected JRadioButton[] buttons;
    
    /**
     * Constructor
     */
    public BooleanVariablePanel(Variable variable) {
        super(variable);
    }

    /**
     * Generates input panel
     */
    protected JPanel createValuePanel() {
        JPanel value = new JPanel(new GridLayout(1, 2, 50, 5));
        ButtonGroup group = new ButtonGroup();
        buttons = new JRadioButton[2];
        JPanel[] iconPanels = new JPanel[2];
        for (int i=0; i < 2; i++) {
            iconPanels[i] = new JPanel(new BorderLayout());
        }
	ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.TRUE);
        buttons[0] = new JRadioButton();
        iconPanels[0].add("West",buttons[0]);
        iconPanels[0].add("East",new JLabel("True",icon,SwingConstants.LEFT));
        JPanel falsePanel = new JPanel(new BorderLayout());
	icon = (ImageIcon) Public.ICONS.get(Public.FALSE);
        buttons[1] = new JRadioButton();
        iconPanels[1].add("West",buttons[1]);
        iconPanels[1].add("East",new JLabel("False",icon,SwingConstants.LEFT));
        for (int i=0; i < 2; i++) {
            group.add(buttons[i]);
            value.add(iconPanels[i]);
        }
        if (((Boolean) variable.getValue()).booleanValue() == true)
            buttons[0].setSelected(true);
        else
            buttons[1].setSelected(true);
        return value;
    }
    
    /**
     * Assign new value to a variable
     */
    public void assign() {
        if (buttons[0].isSelected())
            variable.setValue(new Boolean(true));
        else
            variable.setValue(new Boolean(false));
    }
}