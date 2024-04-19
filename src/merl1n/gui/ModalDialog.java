package merl1n.gui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import merl1n.app.*;

/**
 * The ModalDialog is a root class for all modal gui elements
 *
 * @author Ivo Vondrak
 * @version 1.0 07/26/99
 */ 
public class ModalDialog extends JPanel {
    /**
     * Reference to the owning application
     */
    protected AppType app; 
    
    /**
     * Reference to dialog itself
     */
    protected JDialog dialog;
    
    /**
     * Vector of instantiated dialogs
     */
    public static Vector dialogs = new Vector();

    /**
     * Constructor
     *
     * @param frame owning frame
     * @param title title string
     */
    public ModalDialog(AppType app,String title) {
	this.app = app; 
        this.increment();
	dialog = new JDialog(app.getFrame(), title, true);
	dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	dialog.getContentPane().add("Center",this);
    }


    /**
     * Moves dialog to the center of the frame and shows it.
     */
    public void show() {
    	Dimension fdim = app.getFrame().getSize();
    	Dimension ddim = dialog.getSize();
	int newX = app.getFrame().getLocationOnScreen().x+(fdim.width-ddim.width)/2;
	int newY = app.getFrame().getLocationOnScreen().y+(fdim.height-ddim.height)/2;
	if (newX < 0) newX = 1;
	if (newY < 0) newY = 1;
    	dialog.setLocation( newX, newY);
	dialog.show();
	this.decrement();
    }
    
    /**
     * Increments number of dialogs
     */
    protected void increment() {
        synchronized (dialogs) {
            dialogs.addElement(this);
            this.updateLock();
        }
    }
    
    /**
     * Decrements number of dialogs
     */
    protected void decrement() {
        synchronized (dialogs) {
            dialogs.removeElement(this);
            this.updateLock();
        }
    }
    
    /**
     * Updates lock of frame based on number of opened dialogs
     */
    protected void updateLock() {
        if (dialogs.size() == 0) {
            ((JFrame) app.getFrame()).getGlassPane().setVisible(false);
            app.getFrame().toFront();
        }
        else
            ((JFrame) app.getFrame()).getGlassPane().setVisible(true);
    }
        
    
    /**
     * Sets a default focus
     *
     * @param c component to grab focus
     */
     public void setDefaultFocus(final JComponent c) {
	dialog.addWindowListener(
	    new WindowAdapter() {
		public void windowOpened(WindowEvent e) {
		    c.grabFocus();
		}
	    }
	);
     }
    
}