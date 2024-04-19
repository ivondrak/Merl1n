package merl1n.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;


/**
 * The TextEditMenu enables editing of text component
 *
 * @author Ivo Vondrak
 * @version 1.0 09/22/99
 */ 
public class TextEditMenu { 

    /**
     * Text area for editing
     */
    protected JTextComponent text;
    
    /**
     * Popup menu for text component
     */
    protected JPopupMenu popupMenu;
    

    /**
     * Constructor
     *
     * @param text text component
     * @param type type of edit dialog
     */
    public TextEditMenu(JTextComponent text) {
	this.text = text;
	this.createPopupMenu();
	// Register text with mouse event
	this.text.addMouseListener(
	    new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
		    TextEditMenu.this.popupMenu(e);
		}
	    }
	);
    }
    
    /**
     * Creates popup menu
     */
    protected void createPopupMenu() {
	popupMenu = new JPopupMenu();
	JMenuItem cutItem = new JMenuItem("Cut");
	cutItem.setMnemonic('t');
	cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
	cutItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    text.cut();
		}
	    }
	);	
	JMenuItem copyItem = new JMenuItem("Copy");
	copyItem.setMnemonic('C');
	copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));	
	copyItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    text.copy();
		}
	    }
	);	
	JMenuItem pasteItem = new JMenuItem("Paste");
	pasteItem.setMnemonic('P');
	pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));	
	pasteItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    text.paste();
		}
	    }
	);
	JMenuItem selectItem = new JMenuItem("Select All");
	selectItem.setMnemonic('A');
	selectItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));	
	selectItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    text.selectAll();
		}
	    }
	); 
	popupMenu.add(cutItem);
	popupMenu.add(copyItem);
	popupMenu.add(pasteItem);
	popupMenu.addSeparator();
	popupMenu.add(selectItem);	  	   
    }    
    
    /**
     * Opens edit menu
     */
    protected void popupMenu(MouseEvent e) {
	if (SwingUtilities.isRightMouseButton(e)) {
	    popupMenu.show(text, e.getX(), e.getY()); 
	}
    }   
}