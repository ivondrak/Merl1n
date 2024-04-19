package merl1n.app.web;

import java.awt.*;
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

import merl1n.app.*;
import merl1n.gui.web.*;
import merl1n.es.*;



/**
 * The main applet class for Merl1n web client
 *
 * @author Ivo Vondrak
 * @version 1.3.2 05/20/2000
 */
public class run extends JApplet {
    /**
     * Reference to es panel
     */
    protected M1Web m1web;
    
    /**
     * Status label
     */
    protected JLabel status;
    
    /**
     * Reference to project
     */
    protected Project project;
    
    /**
     * Used flag, because of Netscape, it shows Merl1n frame when browser is resized
     */
    protected boolean used;
    
    /**
     * Entry point to application
     */
    public void init() {
	// Scan arguments
	String look = this.getParameter("look&feel");
	String label = this.getParameter("label");
	String project = this.getParameter("project");
	this.setLook(look);
	status = new JLabel("Expert System Merl1n initializing...");
	status.setHorizontalAlignment(SwingConstants.CENTER);
	Font font = status.getFont();
	font = new Font(font.getName(), Font.BOLD, font.getSize());
	status.setFont(font);	
	this.getContentPane().setLayout(new BorderLayout());
	this.getContentPane().add("Center",status);
	this.validate();
	this.setVisible(true);
        this.loadProject(project.trim());
        if (this.project != null)
	    m1web = new M1Web(this, label, this.project);
    }
    
    /**
     * Refreshes an applet
     */
    public void start() {
        if (project != null && !used) {
            used = true;
	    m1web.show();
	}
    }
    
    /**
     * Sets look&feel of applet
     */
    protected void setLook(String look) {
	if (look == null) look = "-x";
	if (look.equals("-s"))
	    try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch (Exception exc) {}	
	if (look.equals("-x"))
	    try {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    }
	    catch (Exception exc) {} 
        this.setFonts();
    }
    
    /**
     * Load project from remote site
     *
     * @return returns instance of Project
     */
    protected void loadProject(String projectName) {
	ObjectInput input = null;
	project = null;
	Cursor cursor = this.getCursor();
	this.setCursor(new Cursor(Cursor.WAIT_CURSOR));		
	try {	
	    URL url = new URL(this.getCodeBase().toString()+"projects/"+projectName);
	    input = new ObjectInputStream(url.openStream());
	    project = (Project) input.readObject();
	}
	catch (Exception e) {
	    Toolkit.getDefaultToolkit().beep();
	    this.setStatus("Fatal error: Project not loaded!");
	}
	finally {
	    try {
		input.close();
	    }
	    catch (Exception e) {}
	}
	this.setCursor(cursor);
    }  
        
    /**
     * Sets status label
     *
     * @param text status text
     */
    public void setStatus(String text) {
	status.setText(text);
    }
    
    protected void setFonts() {
        String[] keys = {
            "FormattedTextField.font",
            "MenuItem.acceleratorFont",
            "RadioButtonMenuItem.font",
            "Viewport.font",
            "PasswordField.font",
            "CheckBoxMenuItem.font",
            "RadioButton.font",
            "Table.font",
            "EditorPane.font",
            "ColorChooser.font",
            "ToolBar.font",
            "ProgressBar.font",
            "Menu.font",
            "Button.font",
            "TitledBorder.font",
            "PopupMenu.font",
            "Menu.acceleratorFont",
            "TextArea.font",
            "ComboBox.font",
            "InternalFrame.titleFont",
            "CheckBoxMenuItem.acceleratorFont",
            "ToggleButton.font",
            "TabbedPane.font",
            "TextField.font",
            "ToolTip.font",
            "List.font",
            "DesktopIcon.font",
            "OptionPane.font",
            "Panel.font",
            "MenuBar.font",
            "Tree.font",
            "ScrollPane.font",
            "TableHeader.font",
            "MenuItem.font",
            "TextPane.font",
            "CheckBox.font",
            "Label.font",
            "Spinner.font",
            "RadioButtonMenuItem.acceleratorFont"
        };
        Font newFont = new Font("Dialog",Font.PLAIN,11);
        for (int i=0; i < keys.length; i++)    
            UIManager.put(keys[i], newFont);        
    }        
}
