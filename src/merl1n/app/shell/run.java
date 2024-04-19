package merl1n.app.shell;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import merl1n.gui.shell.*;
import merl1n.tool.*;


/**
 * The main class for Merl1n's Shell
 *
 * @author Ivo Vondrak
 * @version 1.3.2 05/20/2000
 */
public class run {
    /**
     * Entry point to application
     */
    public static void main(String args[]) {
	// Scan arguments
	boolean uimanager = false;
	for (int i=0; i < args.length; i++) {
	    if (args[i].equals("-s"))
		try {
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    uimanager = true;
		}
		catch (Exception exc) {}	
	    if (args[i].equals("-x"))
		try {
		    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    uimanager = true;
		}
		catch (Exception exc) {}
	    if (args[i].equals("-?")) {
		System.out.println("Options:");
		System.out.println("	-s  system look&feel");
		System.out.println("	-x  cross platform look&feel");
		System.out.println("	-?  help");
		return;
	    }
	}
	if (!uimanager)
	    try {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		uimanager = true;
	    }
	    catch (Exception exc) {}
	// Evaluation trigger
	Public.EVAL = false;
        setFonts();
	M1Shell app = new M1Shell();
	app.show();
    }
    
    protected static void setFonts() {
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
