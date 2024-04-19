package merl1n.gui.shell;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import merl1n.app.*;
import merl1n.es.*;
import merl1n.es.Module;
import merl1n.gui.*;

/**
 * The RenameModule class is GUI element for renaming of module
 *
 * @author Ivo Vondrak
 * @version 1.0 09/22/99
 */ 
public class RenameModule extends ModalDialog {

    /**
     * Module being renamed
     */
    protected Module module;
    
    /**
     * Text field for a name
     */
    protected JTextField text;   
    
    /**
     * Constructor
     */
    public RenameModule(AppType app, Module module) {
	super(app,"Rename Module");
	this.module = module;
	this.setLayout(new BorderLayout(5,5));
	// North input panel
	JPanel north = new JPanel(new BorderLayout());
	JLabel label = new JLabel("Name:");
	text = new JTextField(module.getName());
	new TextEditMenu(text);
	north.add("North",label);
	north.add("South",text);
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
		    RenameModule.this.ok();
		}
	    }
	);
	cancel.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    RenameModule.this.cancel();
		}
	    }	
	);
	north.setBorder(new EmptyBorder(5,5,5,5));
	south.setBorder(new EmptyBorder(5,5,5,5));		
	this.add("North",north);
	this.add("South",south);
	dialog.pack();
    }
    
    /**
     * OK button clicked
     */
    protected void ok() {
	module.setName(text.getText());
	dialog.dispose();
    }
    
    /**
     * Cancel button clicked
     */
    protected void cancel() {
	dialog.dispose();
    }
}