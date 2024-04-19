package merl1n.gui.shell;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import merl1n.app.*;
import merl1n.tool.*;
import merl1n.gui.*;


/**
 * The AboutDialog class
 *
 * @author Ivo Vondrak
 * @version 2.1 10/06/2002
 */ 
public class AboutDialog extends ModalDialog {

    /**
     * Array of string to be displayed
     */
    String[] text = {
		    "Merl1n",
		    "                         ",
		    "Expert System Shell",
		    "version 2.1"+(Public.EVAL?" (Demo)":""),		    
		    "Copyright (C) 1999-2002 Ivo Vondrak"};  

    /**
     * Constructor
     */
    public AboutDialog(AppType app) {
	super(app,"About Dialog");
	this.setLayout(new BorderLayout(5,5));
	// North panel
	JPanel north = new JPanel(new BorderLayout());
	ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.M1SHELL2X);
	JLabel image = new JLabel(icon);
	north.add(image);
	JPanel north_south = new JPanel(new GridLayout(5,1,2,2));
	JLabel[] labels = new JLabel[5];
	for (int i=0; i < 5; i++) {
	    labels[i] = new JLabel(text[i]);
	    labels[i].setHorizontalAlignment(SwingConstants.CENTER);
	    north_south.add(labels[i]);
	}
	Font font = labels[0].getFont();
	font = new Font(font.getName(), Font.BOLD, font.getSize());
	labels[0].setFont(font);
	labels[0].setForeground(Color.blue);
	north.add("South",north_south);
	// South button panel
	JPanel south = new JPanel(new BorderLayout());
	//South-west panel
	JPanel south_west = new JPanel(new GridLayout(1,1,5,5));
	JButton ok = new JButton("   OK   ");
	ok.setMnemonic('O');
	this.setDefaultFocus(ok);
	south_west.add(ok);
	south.add("West",south_west);
	// Register listener
	ok.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    AboutDialog.this.ok();
		}
	    }
	);
	north.setBorder(new EmptyBorder(15,15,15,15));
	south.setBorder(new EmptyBorder(15,15,15,15));
	Border border = new BevelBorder(BevelBorder.LOWERED);
	Border margin = new EmptyBorder(15,15,15,15);
	JPanel panel = new JPanel(new BorderLayout());
	panel.setBorder(new CompoundBorder(border, margin));
	panel.add("North",north);
	panel.add("South",south);
	this.add("Center",panel);
	dialog.setSize(340,280);
    }
    
    /**
     * OK button clicked
     */
    protected void ok() {
	dialog.dispose();
    }    
}