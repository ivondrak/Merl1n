package merl1n.gui.exec;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import merl1n.app.*;
import merl1n.gui.*;
import merl1n.es.*;
import merl1n.es.Module;
import merl1n.tool.*;



/**
 * The Input dialog load variable values for a given module
 *
 * @author Ivo Vondrak
 * @version 1.3 10/19/99
 */ 
public class InputDialog extends ModalDialog  {
    /**
     * Reference to project
     */
    protected Project project;
    
    /**
     * Reference to module
     */
    protected merl1n.es.Module module;
    
    /**
     * References to variable panels
     */
    protected Vector panels;
    
    /**
     * Constructor
     */
    public InputDialog(AppType app, Project project, merl1n.es.Module module) {
        super(app, "Input Dialog");
        this.project = project;
        this.module = module;
        panels = new Vector();
	this.setLayout(new BorderLayout(5,5));
	// Center panel
	JPanel center = new JPanel(new BorderLayout());
	JLabel label = new JLabel(" Input:");
	JScrollPane scrollPanel = new JScrollPane(this.generatePanel());
	JScrollBar scrollBar = scrollPanel.getVerticalScrollBar();
	scrollBar.setUnitIncrement(10);
	scrollBar = scrollPanel.getHorizontalScrollBar();
	scrollBar.setUnitIncrement(10);
	center.add("North",label);
	center.add("Center",scrollPanel);
	// South button panel
	JPanel south = new JPanel(new BorderLayout());
	// South-west panel
	JPanel south_west = new JPanel(new GridLayout(1,2,5,5));
	JButton ok = new JButton("   OK   ");
	ok.setMnemonic('O');
	this.setDefaultFocus(ok);	
	JButton cancel = new JButton(" Cancel ");
	cancel.setMnemonic('C');
	south_west.add(ok);
	south_west.add(cancel);
	south.add("West",south_west);
	// Register listener
	ok.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    InputDialog.this.ok();
		}
	    }
	);
	cancel.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    InputDialog.this.cancel();
		}
	    }	
	);
	Border border = new BevelBorder(BevelBorder.RAISED);
	Border margin = new EmptyBorder(5,5,5,5);
	center.setBorder(new CompoundBorder(border, margin));	
	south.setBorder(new EmptyBorder(5,5,5,5));		
	this.add("Center",center);
	this.add("South",south);
	Dimension dim = app.getFrame().getSize();
	dialog.setSize(dim.width - (int) Math.round((dim.width/100)*30f),
	               dim.height - (int) Math.round((dim.height/100)*40f));
    }
    
    /**
     * Generates input panel
     */
    protected JPanel generatePanel() {
        JPanel input = new JPanel();
	input.setBackground(new Color(210,210,210));
        int rows = 0;
        for (Enumeration vars=module.getVariables().elements();vars.hasMoreElements();) {
            Variable variable = (Variable) vars.nextElement();
            if (variable.isExternal())
                rows++;
        }
        input.setLayout(new GridLayout(rows+10, 1, 0, 0));
        for (Enumeration vars=module.getVariables().elements();vars.hasMoreElements();) {
            Variable variable = (Variable) vars.nextElement();
            // Variable need not be external
            if (!variable.isExternal())
                continue;
            VariablePanel panel = null;
            switch (variable.getType()) {
                case Public.BOOLEAN:
                    panel = new BooleanVariablePanel(variable);
                    break;
                case Public.INTEGER:
                    panel = new IntegerVariablePanel(variable);
                    break;
                case Public.REAL:
                    panel = new RealVariablePanel(variable);
                    break;
                case Public.TEXT:
                    panel = new TextVariablePanel(variable);
                    break;
            }
            input.add(panel);
            panels.addElement(panel);
        }
        return input;
    }
    
    /**
     * OK button clicked
     */
    public void ok() {
        for (Enumeration ps = panels.elements(); ps.hasMoreElements();) {
            ((VariablePanel) ps.nextElement()).assign();
        }
        dialog.dispose();
    }
    
    /**
     * Cancel button clicked
     */
    protected void cancel() {
        project.setState(Public.STOPPED);
	dialog.dispose();
    }
    
}