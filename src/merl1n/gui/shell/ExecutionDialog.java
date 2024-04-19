package merl1n.gui.shell;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import merl1n.app.*;
import merl1n.gui.*;
import merl1n.gui.exec.*;
import merl1n.es.*;



/**
 * The Execution dialog enables evaluation of rules from M1Shell
 *
 * @author Ivo Vondrak
 * @version 1.0 10/02/99
 */ 
public class ExecutionDialog extends ModalDialog {
    /**
     * Constructor
     */
    public ExecutionDialog(AppType app) {
	super(app,"Execution Dialog");
	this.setLayout(new BorderLayout(5,5));
	// Center panel
	ExecutionPanel center = new ExecutionPanel(app, ((ProjectType) app).getProject());
	this.setDefaultFocus(center);
	// South button panel
	JPanel south = new JPanel(new BorderLayout());
	//South-west panel
	JPanel south_west = new JPanel(new GridLayout(1,1,5,5));
	JButton close = new JButton(" Close ");
	close.setMnemonic('C');
	south_west.add(close);
	south.add("West",south_west);
	// Register listener
	close.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    ExecutionDialog.this.close();
		}
	    }
	);
	center.setClose(close);
	Border border = new BevelBorder(BevelBorder.RAISED);
	Border margin = new EmptyBorder(5,5,5,5);
	center.setBorder(new CompoundBorder(border, margin));	
	south.setBorder(new EmptyBorder(5,5,5,5));		
	this.add("Center",center);
	this.add("South",south);
	Dimension dim = app.getFrame().getSize();
	dialog.setSize(dim.width - (int) Math.round(dim.width/10.0),
	               dim.height - (int) Math.round(dim.height/10.0));
    }
    
    
    /**
     * Close button clicked
     */
    protected void close() {
	dialog.dispose();
    }    
}