package merl1n.gui.exec;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import merl1n.es.*;
import merl1n.gui.*;



/**
 * The Variable panel enables define the value of variable
 *
 * @author Ivo Vondrak
 * @version 1.3 10/19/99
 */ 
public class RealVariablePanel extends VariablePanel {
    /**
     * ComboBox
     */
    protected JComboBox comboBox;
    
    /**
     * Reference to text field
     */
    protected JTextField text;
    
    /**
     * Constructor
     */
    public RealVariablePanel(Variable variable) {
        super(variable);
    }

    /**
     * Generates input panel
     */
    protected JPanel createValuePanel() {
        JPanel value = new JPanel(new BorderLayout(5,5));
        if (variable.isEnumerative()) {
            Vector range = variable.getRange();
            comboBox = new JComboBox();
            comboBox.setEditable(false);
            for (int i=0; i < range.size(); i++) {
                String item = " "+range.elementAt(i).toString()+"     ";
                comboBox.addItem(item);
                if (variable.getValue().equals(range.elementAt(i)))
                    comboBox.setSelectedIndex(i);
            }
            value.add("West",comboBox);
        }
        else {
            text = new JTextField(variable.getValue().toString(),10);
            new TextEditMenu(text);
            value.add("West",text);
        }
        return value;
    }
    
    /**
     * Assign new value to a variable
     */
    public void assign() {
        if (variable.isEnumerative()) {
            int i = comboBox.getSelectedIndex();
            variable.setValue(variable.getRange().elementAt(i));
        }
        else {
            try {
                Float v = new Float(text.getText());
                variable.setValue(v);
            }
            catch (Exception e) {
                text.setText(variable.getValue().toString());
            }
        }
    }
}