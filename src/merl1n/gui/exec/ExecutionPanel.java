package merl1n.gui.exec;

import java.util.*;
import java.net.*;
import java.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

import merl1n.app.*;
import merl1n.gui.*;
import merl1n.es.*;
import merl1n.es.Module;
import merl1n.tool.*;



/**
 * The Excution panel enables evaluation of rules
 *
 * @author Ivo Vondrak
 * @version 1.3 10/19/99
 */ 
public class ExecutionPanel extends JPanel  {
    /**
     * Reference to application
     */
    protected AppType app;
    
    /**
     * Reference to project
     */
    protected Project project;
    
    /**
     * Main tabbed panel
     */
    protected JTabbedPane tabbedPanel;
    
    /**
     * Reference to text panel
     */
    //protected JTextArea output;
    protected JEditorPane output;
    
    
    /**
     * References to variable panels
     */
    protected Vector panels;
    
    /**
     * Reference to input device
     */
    protected InputDevice inputDevice;
    
    /**
     * Reference to output device
     */
    protected OutputDevice outputDevice;
    
    /**
     * Reference to evaluation button
     */
    protected JButton eval;
    
    /**
     * Reference to close button, passed by owning frame/dialog
     */
    protected JButton close;
    
    /**
     * Reference to clear button of output panel
     */
    protected JButton clear;
    
    /**
     * Messages sent to user
     */
    protected String messages;
    
    /**
     * Constructor
     */
    public ExecutionPanel(AppType app, Project project) {
        this.app = app;
        this.project = project;
        panels = new Vector();
	this.setLayout(new BorderLayout(5,5));
	// Center tabbed panel
	tabbedPanel = new JTabbedPane(JTabbedPane.BOTTOM);
	// Center input panel
	JPanel inputPanel = new JPanel(new BorderLayout());
	JLabel inputLabel = new JLabel(" Input:");
	JScrollPane inputScrollPanel = new JScrollPane(this.generatePanel());
	JScrollBar scrollBar = inputScrollPanel.getVerticalScrollBar();
	scrollBar.setUnitIncrement(10);
	scrollBar = inputScrollPanel.getHorizontalScrollBar();
	scrollBar.setUnitIncrement(10);
        // South input panel
        JPanel southInputPanel = new JPanel(new BorderLayout());
	eval = new JButton(" Evaluate ");
	ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.EXECUTE);
	eval.setIcon(icon);
	eval.setMnemonic('E'); 
        southInputPanel.add("West",eval);
	inputPanel.add("North",inputLabel);
	inputPanel.add("Center",inputScrollPanel);
        inputPanel.add("South",southInputPanel);
	// Center output panel
	JPanel outputPanel = new JPanel(new BorderLayout());
	JLabel outputLabel = new JLabel(" Output:");
        messages = "";
        output = new JEditorPane();
        output.setContentType("text/html");
        output.setEditable(false);
        output.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    URL url = ExecutionPanel.this.getURL(e);
                    if (url != null) {
                        String ref = url.getRef();
                        if (ref != null && ref.equals("[IMAGE]")) {
                            ExecutionPanel.this.showImage(url);
                        }
                        else
                            ExecutionPanel.this.showDocument(url);
                    }
                }
            }
        });  
	new TextEditMenu(output);
	JScrollPane outputScrollPanel = new JScrollPane(output);
	outputScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // South outputt panel
        JPanel southOutputPanel = new JPanel(new BorderLayout());
	clear = new JButton(" Clear ");
	icon = (ImageIcon) Public.ICONS.get(Public.FALSE);
	clear.setIcon(icon);
	clear.setMnemonic('C'); 
        southOutputPanel.add("West",clear);        
	outputPanel.add("North",outputLabel);
	outputPanel.add("Center",outputScrollPanel);
        outputPanel.add("South",southOutputPanel);
	// Register listener
	eval.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    ExecutionPanel.this.evaluate();
		}
	    }
	);
        clear.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
                    ExecutionPanel.this.messages = "";
		    ExecutionPanel.this.display("");
		}
	    }
	); 
	tabbedPanel.addTab("Input",inputPanel);
	tabbedPanel.addTab("Output",outputPanel);
	inputDevice = new InputDevice() {
	    public void read(Module module) {
	        ExecutionPanel.this.read(module);
	    }
	};	
	outputDevice = new OutputDevice() {
	    public void display(String message) {
	        ExecutionPanel.this.display(message);
	    }
	};
	this.add("Center",tabbedPanel);
    }
    
    /**
     * Sets close button
     */
    public void setClose(JButton close) {
        this.close = close;
    }
    
    /**
     * Generates input panel
     */
    protected JPanel generatePanel() {
        JPanel input = new JPanel();
	input.setBackground(new Color(210,210,210));
        Module module = (Module) project.getModules().firstElement();
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
     * Evaluates expert system rules
     */
    public void evaluate() {
        eval.setEnabled(false);
        close.setEnabled(false);
        for (Enumeration ps = panels.elements(); ps.hasMoreElements();) {
            ((VariablePanel) ps.nextElement()).assign();
        }
        project.evaluate(inputDevice, outputDevice);
        tabbedPanel.setSelectedIndex(1);
        eval.setEnabled(true);
        close.setEnabled(true);
    } 
    
    /**
     * Read input values for a module
     */
    public void read(Module module) {
        new InputDialog(app, project, module).show();
    }  
    
    /**
     * Displays text
     */
    public void display(String text) {
        messages = messages+text;
        output.setText("<FONT SIZE=-1 FACE=DIALOG>"+messages+"</FONT>");
    }
    
    /**
     * Gets url of image or document.
     *
     *@return null if URL cannot be specified
     */
    protected URL getURL(HyperlinkEvent event) {
        String description = event.getDescription();
        try {
            return new URL(description);
        }
        catch (Exception e) {}
        if (app.getApplet() == null) {
            String filename = "";
            String separator = description.substring(0,1);
            if (separator.substring(0,1).equals("/")) {
                filename = description;
            }
            else {
                filename = System.getProperty("user.dir");
                filename = filename+"/"+description;
            }
            try {
                return new File(filename).toURL();
            }
            catch (Exception e) {}
        }
        else {
            try {
                return new URL(app.getApplet().getCodeBase(), description);
            }
            catch (Exception e) {}
        }
        return null;
    }
    
    /**
     * Shows image in a dialog box
     */
    protected void showImage(URL url) {
        try {
            URL img = new URL(url.getProtocol(),url.getHost(),url.getPort(),url.getFile());
            new ImageDialog(ExecutionPanel.this.app, img).show();
        }
        catch (Exception exc) {}; 
    }
    
    /**
     * Shows document in an external browser
     */
    protected void showDocument(URL url) {     
        String location = url.getProtocol()+
                            "://"+
                            (url.getHost().equals("") ? "" : url.getHost())+
                            (url.getPort() == -1 ? "" : ":"+url.getPort())+
                            url.getFile();
        Applet applet;
        if ((applet = app.getApplet()) == null) {                            
            try {
                boolean win;
                String os = System.getProperty("os.name");
                if ( os != null && os.startsWith("Windows")) 
                    win = true;
                else    
                    win = false;
                String command = null;
                if (win) {
                    // cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
                    command = "rundll32 url.dll,FileProtocolHandler "+ location;
                    Process p = Runtime.getRuntime().exec(command);
                }
                else {
                    // Under Unix, Netscape has to be running for the "-remote"
                    // command to work.  So, we try sending the command and
                    // check for an exit value.  If the exit command is 0,
                    // it worked, otherwise we need to start the browser.
                    // cmd = 'netscape -remote openURL(http://www.javaworld.com)'
                    command = "netscape -remote openURL (" + location + ")";
                    Process p = Runtime.getRuntime().exec(command);
                    // wait for exit code -- if it's 0, command worked,
                    // otherwise we need to start the browser up.
                    int exitCode = p.waitFor();
                    if (exitCode != 0) {
                        // Command failed, start up the browser
                        // cmd = 'netscape http://www.javaworld.com'
                        command = "netscape " + location;
                        p = Runtime.getRuntime().exec(command);
                    }
                }
                app.getFrame().toBack();
            }
            catch (Exception e) {
                this.internalBrowser(url);
            }
        }
        else {
            applet.getAppletContext().showDocument(url,"_blank");
        }
    }
    
    /**
     * Executes internal browser
     */
    public void internalBrowser(URL url) {
        try {
            URL doc = new URL(url.getProtocol(),url.getHost(),url.getPort(),url.getFile());
            new DocDialog(ExecutionPanel.this.app, doc).show();
            return;
        }
        catch (Exception exc) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(
                app.getFrame(), 
                "Internal web browser cannot be executed!",
                "Error!",
                JOptionPane.ERROR_MESSAGE);             
        };           
    }
}