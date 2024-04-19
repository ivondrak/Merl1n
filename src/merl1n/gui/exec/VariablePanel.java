package merl1n.gui.exec;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import merl1n.es.*;



/**
 * The Variable panel enables define the value of variable
 *
 * @author Ivo Vondrak
 * @version 1.0 10/03/99
 */ 
public abstract class VariablePanel extends JPanel {
    /**
     * Reference to variable
     */
    protected Variable variable;

    /**
     * Constructor
     */
    public VariablePanel(Variable variable) {
        this.variable = variable;
	this.setLayout(new BorderLayout());
	// North panel
	JPanel north = new JPanel(new BorderLayout(2,2));
	JLabel label = new JLabel(variable.getLabel());
	JPanel north_south = new JPanel(new BorderLayout());
	// South-west panel
	JPanel north_south_west = this.createValuePanel();
	north_south.add("West",north_south_west);
	north.add("North",label);
	north.add("South",north_south);
	this.add("North",north);
	Border border = new EtchedBorder(EtchedBorder.LOWERED);
	Border margin = new EmptyBorder(5,5,5,5);
	this.setBorder(new CompoundBorder(border, margin));
    }
    
    /**
     * Generates input panel
     */
    protected abstract JPanel createValuePanel();
    
    /**
     * Assign new value to a variable
     */
    public abstract void assign();
}