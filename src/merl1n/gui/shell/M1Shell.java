package merl1n.gui.shell;

import java.util.*;
import java.util.zip.*;
import java.io.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import merl1n.app.*;
import merl1n.es.*;
import merl1n.es.Module;
import merl1n.gui.*;
import merl1n.tool.*;

/**
 * The main gui class for Merl1n Expert System Shell
 *
 * @author Ivo Vondrak
 * @version 1.1 10/06/99
 */
public class M1Shell extends JPanel implements AppType, ProjectType   {
    /**
     * Flag of knowledge change
     */
    protected boolean projectChanged = false;
    
    /**
     * Reference to owning frame
     */
    protected JFrame frame; 

    /**
     * Reference to project home
     */
    protected String projectHome;

    /**
     * Reference to project object
     */
    protected Project project;
    
    /**
     * File name
     */
    protected String filename;

    /**
     * Project panel
     */
    protected ProjectPanel projectPanel;
    
    /**
     * File menu
     */
    protected JMenu fileMenu;
    
    /**
     * Edit menu
     */
    protected JMenu editMenu;
    
    /**
     * Project menu
     */
    protected JMenu projectMenu;
    
     /**
     * Help menu
     */
    protected JMenu helpMenu; 
    
     /**
      * Toolbar
      */
     protected JToolBar toolBar; 

    /**
     * Constructor.
     */
    public M1Shell() {
	String dir = System.getProperty("user.dir");
	String sep = System.getProperty("file.separator");
	this.setProjectHome(dir+sep+"projects");        
	this.frame = new JFrame();
	filename = "noname.prj";
	Public.ICONS = new Hashtable();
	this.project = new Project();
	// Sets glass pane
    	this.frame.setGlassPane(new GlassPanel());
	// Factory methods
	this.setLayout(new BorderLayout());
	this.createIcons();
	this.createMenuBar();
	this.createToolBar();
	this.createProjectPanel();
	// Listeners' registrations
	this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	this.frame.addWindowListener(
	    new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		    M1Shell.this.exit();
		}
	    }
	);
	this.frame.setTitle("Merl1n - "+filename);
	this.frame.getContentPane().setLayout(new BorderLayout());
	this.frame.getContentPane().add("Center",this);	
    }

    /**
     * Shows main window
     */
    public void show() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	float scale = (float) 4f/5f;
	int width = (int) (scale*screenSize.width);
	int height = (int) (scale*screenSize.height);
	this.getFrame().setSize(width, height);
	int x = screenSize.width/2 - width/2;
	double delta = Math.random();
	delta = delta*50-25;
	x = x + (int) delta;
	int y = screenSize.height/2 - height/2;
	delta = Math.random();
	delta = delta*50-25;
	y = y + (int) delta;
	if (x < 0) x = 0;
	if (y < 0) y = 0;
	this.updateControls();
	this.getFrame().setLocation(x,y);
	this.getFrame().show();
        this.projectPanel.setDividerLocation(2f/3f);
        this.requestDefaultFocus();
    }

    /**
     * Gets owning applet
     */
    public Applet getApplet() {
        return null;
    }    
    
    /**
     * Gets the application frame
     */
    public Frame getFrame() {
        return  frame;
    }
    
    /**
     * Gets project
     */
    public Project getProject() {
	return project;
    }
    
    /**
     * Sets associated project
     */
    public void setProject(Project project) {
	this.project = project;
    } 
    
    /**
     * Sets flag of a project change
     *
     * @param flag flag of project change
     */
    public void setProjectChanged(boolean flag) {
        projectChanged = flag;
    }
    
    /**
     * Gets flag of a project change
     */
    public boolean getProjectChanged() {
        return projectChanged;
    }
    
    /**
     * Sets home directory of project
     *
     * @param home home of project
     */
    public void setProjectHome(String home) {
        projectHome = home;
    }
    
    /**
     * Gets home directory of project
     */
    public String getProjectHome() {
        return projectHome;
    }
    
    /**
     * Creates project pane
     */
    protected void createProjectPanel() {
        projectPanel = new ProjectPanel(this, project);
        this.add("Center",projectPanel);
    }
     
    /**
     * Creates menu bar for the application
     */
    protected void createMenuBar() {
	JMenuBar menuBar = new JMenuBar();
	frame.setJMenuBar(menuBar);
	createFileMenu();
	createEditMenu();
	createProjectMenu();
	createHelpMenu();
    }
    
    /**
     * Creates tool bar for the application
     */
    protected void createToolBar() {
	toolBar = new JToolBar();
	toolBar.setFloatable(false);
	toolBar.setBorderPainted(false);
	createFileTools();
	createEditTools();
	createProjectTools();
	this.add("North",toolBar);
    }    
    
    /**
     * Creates file menu
     */
    protected void createFileMenu() {
	fileMenu = new JMenu("File");
	fileMenu.setMnemonic('F');
        ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.NEW);
	JMenuItem newItem = new JMenuItem("New",icon);
	newItem.setMnemonic('N');
	newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	newItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.newProject();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.OPEN);
	JMenuItem openItem = new JMenuItem("Open...",icon);
	openItem.setMnemonic('O');
	openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));	
	openItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.openProject();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.SAVE);
	JMenuItem saveItem = new JMenuItem("Save",icon);
	saveItem.setMnemonic('S');
	saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	saveItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.saveProject();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.NULL);
	JMenuItem saveAsItem = new JMenuItem("Save As...",icon);
	saveAsItem.setMnemonic('A');
	saveAsItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.saveAsProject();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.NULL);
	JMenuItem exportItem = new JMenuItem("Export Module",icon);
	exportItem.setMnemonic('E');
	exportItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.exportModule();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.NULL);
	JMenuItem importItem = new JMenuItem("Import Module",icon);
	importItem.setMnemonic('I');
	importItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.importModule();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.NULL);
	JMenuItem exitItem = new JMenuItem("Exit", icon);
	exitItem.setMnemonic('x');
	exitItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.exit();
		}
	    }
	);
	fileMenu.add(newItem);
	fileMenu.add(openItem);
	fileMenu.addSeparator();
	fileMenu.add(saveItem);
	fileMenu.add(saveAsItem);
	fileMenu.addSeparator();
        fileMenu.add(exportItem);
	fileMenu.add(importItem);
	fileMenu.addSeparator();
	fileMenu.add(exitItem);
	frame.getJMenuBar().add(fileMenu);   
    }    
    
    /**
     * Creates file tools
     */
    protected void createFileTools() {
	ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.NEW);
	JButton newItem = new JButton(icon);
	newItem.setToolTipText("New project");
	newItem.setMargin(new Insets(1,1,1,1));
	newItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.newProject();
		}
	    }
	);
	icon = (ImageIcon) Public.ICONS.get(Public.OPEN);
	JButton openItem = new JButton(icon);
	openItem.setToolTipText("Open project");
	openItem.setMargin(new Insets(1,1,1,1));
	openItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.openProject();
		}
	    }
	);
	icon = (ImageIcon) Public.ICONS.get(Public.SAVE);
	JButton saveItem = new JButton(icon);
	saveItem.setToolTipText("Save project");
	saveItem.setMargin(new Insets(1,1,1,1));
	saveItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.saveProject();
		}
	    }
	);	
	toolBar.add(newItem);
	toolBar.add(openItem);
	toolBar.add(saveItem);
	toolBar.addSeparator();
    }        

    /**
     * Creates edit menu
     */
    protected void createEditMenu() {
	editMenu = new JMenu("Edit");
	editMenu.setMnemonic('E');
        ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.CUT);
	JMenuItem cutItem = new JMenuItem("Cut",icon);
	cutItem.setMnemonic('t');
	cutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));		
	cutItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.cutText();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.COPY);
	JMenuItem copyItem = new JMenuItem("Copy",icon);
	copyItem.setMnemonic('C');
	copyItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
	copyItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.copyText();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.PASTE);
	JMenuItem pasteItem = new JMenuItem("Paste",icon);
	pasteItem.setMnemonic('P');
	pasteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
	pasteItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.pasteText();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.NULL);
	JMenuItem deleteItem = new JMenuItem("Delete", icon);
	deleteItem.setMnemonic('D');
	deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
	deleteItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.deleteText();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.NULL);
	JMenuItem selectAllItem = new JMenuItem("Select All",icon);
	selectAllItem.setMnemonic('A');
	selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
	selectAllItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.selectAll();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.INSERT);
	JMenuItem insertModuleItem = new JMenuItem("Insert Module",icon);
	insertModuleItem.setMnemonic('I');
	insertModuleItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.insertModule();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.NULL);
	JMenuItem deleteModuleItem = new JMenuItem("Delete Module",icon);
	deleteModuleItem.setMnemonic('l');
	deleteModuleItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.deleteModule();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.NULL);
	JMenuItem renameModuleItem = new JMenuItem("Rename...",icon);
	renameModuleItem.setMnemonic('n');
	renameModuleItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.renameModule();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.MOVEUP);
	JMenuItem moveUpItem = new JMenuItem("Move Module Up",icon);
	moveUpItem.setMnemonic('U');
	moveUpItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.moveUp();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.MOVEDOWN);
	JMenuItem moveDownItem = new JMenuItem("Move Module Down",icon);
	moveDownItem.setMnemonic('D');
	moveDownItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.moveDown();
		}
	    }
	);	
	editMenu.add(cutItem);
	editMenu.add(copyItem);
	editMenu.add(pasteItem);
	editMenu.add(deleteItem);	
	editMenu.addSeparator();
	editMenu.add(selectAllItem);
	editMenu.addSeparator();
	editMenu.add(insertModuleItem);
	editMenu.add(deleteModuleItem);
	editMenu.add(renameModuleItem);
	editMenu.addSeparator();
	editMenu.add(moveUpItem);
	editMenu.add(moveDownItem);
	((JFrame) this.frame).getJMenuBar().add(editMenu);
    }

    /**
     * Creates edit tools
     */
    protected void createEditTools() {
	ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.CUT);
	JButton cutItem = new JButton(icon);
	cutItem.setToolTipText("Cut text");
	cutItem.setMargin(new Insets(1,1,1,1));
	cutItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.cutText();
		}
	    }
	);
	icon = (ImageIcon) Public.ICONS.get(Public.COPY);
	JButton copyItem = new JButton(icon);
	copyItem.setToolTipText("Copy text");
	copyItem.setMargin(new Insets(1,1,1,1));
	copyItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.copyText();
		}
	    }
	);
	icon = (ImageIcon) Public.ICONS.get(Public.PASTE);
	JButton pasteItem = new JButton(icon);
	pasteItem.setToolTipText("Paste text");
	pasteItem.setMargin(new Insets(1,1,1,1));
	pasteItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.pasteText();
		}
	    }
	);	
	icon = (ImageIcon) Public.ICONS.get(Public.INSERT);
	JButton insertItem = new JButton(icon);
	insertItem.setToolTipText("Insert module");
	insertItem.setMargin(new Insets(1,1,1,1));
	insertItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.insertModule();
		}
	    }
	);
 	icon = (ImageIcon) Public.ICONS.get(Public.MOVEUP);
	JButton moveupItem = new JButton(icon);
	moveupItem.setToolTipText("Move module up");
	moveupItem.setMargin(new Insets(1,1,1,1));
	moveupItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.moveUp();
		}
	    }
	);
 	icon = (ImageIcon) Public.ICONS.get(Public.MOVEDOWN);
	JButton movedownItem = new JButton(icon);
	movedownItem.setToolTipText("Move module down");
	movedownItem.setMargin(new Insets(1,1,1,1));
	moveupItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    projectPanel.moveDown();
		}
	    }
	);         
	toolBar.add(cutItem);
	toolBar.add(copyItem);
	toolBar.add(pasteItem);
	toolBar.addSeparator();
	toolBar.add(insertItem);
        toolBar.add(moveupItem);
        toolBar.add(movedownItem);
	toolBar.addSeparator();
    }            

    /**
     * Creates project menu
     */
    protected void createProjectMenu() {
	projectMenu = new JMenu("Project");
	projectMenu.setMnemonic('P');
        ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.BUILD);
	JMenuItem buildItem = new JMenuItem("Build",icon);
	buildItem.setMnemonic('B');
	buildItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		   M1Shell.this.build();
		}
	    }
	);
        icon = (ImageIcon) Public.ICONS.get(Public.EXECUTE);
	JMenuItem executeItem = new JMenuItem("Execute",icon);
	executeItem.setMnemonic('E');
	executeItem.addActionListener(
	    new ActionListener() {
                public void actionPerformed(ActionEvent e) {
		   M1Shell.this.execute();
		}
	    }
	);
	projectMenu.add(buildItem);
	projectMenu.addSeparator();
	projectMenu.add(executeItem);
	frame.getJMenuBar().add(projectMenu);   
    } 
       
    /**
     * Creates project tools
     */
    protected void createProjectTools() {
	ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.BUILD);
	JButton buildItem = new JButton(icon);
	buildItem.setToolTipText("Build application");
	buildItem.setMargin(new Insets(1,1,1,1));
	buildItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Shell.this.build();
		}
	    }
	);
	icon = (ImageIcon) Public.ICONS.get(Public.EXECUTE);
	JButton executeItem = new JButton(icon);
	executeItem.setToolTipText("Execute application");
	executeItem.setMargin(new Insets(1,1,1,1));
	executeItem.addActionListener(
	    new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    M1Shell.this.execute();
                }
	    }
	);
	toolBar.add(buildItem);
	toolBar.add(executeItem);
	toolBar.addSeparator();
    }            
        
        
    /**
     * Creates help menu
     */
    protected void createHelpMenu() {
	helpMenu = new JMenu("Help");
	helpMenu.setMnemonic('H');
	JMenuItem aboutItem = new JMenuItem("About...");
	aboutItem.setMnemonic('A');
	aboutItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		   new AboutDialog(M1Shell.this).show();
		}
	    }
	);
	helpMenu.add(aboutItem);
	frame.getJMenuBar().add(helpMenu);
    }    

    /**
     * Creates icons for an application
     */
    public void createIcons() {
    	// Load icons from jar if this one is available
    	// path is relative to a give class
    	String text = Public.M1SHELL;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/m1shell.gif"),text));
	frame.setIconImage(((ImageIcon) Public.ICONS.get(text)).getImage());
 	text = Public.M1SHELL2X;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/m1shell2x.gif"),text));       
	text = Public.NEW;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/new.gif"),text));
	text = Public.OPEN;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/open.gif"),text));
	text = Public.SAVE;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/save.gif"),text));
	text = Public.CUT;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/cut.gif"),text));
	text = Public.COPY;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/copy.gif"),text));
	text = Public.PASTE;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/paste.gif"),text));
	text = Public.INSERT;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/insert.gif"),text));
	text = Public.MOVEUP;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/moveup.gif"),text));
	text = Public.MOVEDOWN;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/movedown.gif"),text));        
	text = Public.BUILD;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/build.gif"),text));
	text = Public.EXECUTE;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/execute.gif"),text));
	text = Public.TRUE;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/true.gif"),text));
	text = Public.FALSE;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/false.gif"),text));
	text = Public.BACK;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/back.gif"),text));
	text = Public.FORWARD;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/forward.gif"),text)); 
	text = Public.HOME;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/home.gif"),text));
	text = Public.NULL;
	Public.ICONS.put(text, new ImageIcon(loadImage("/images/null.gif"),text));         
    }

    /**
     * Loads image from jar file
     *
     * @param fileName file name of an image
     * @return image or null in not found
     */
    protected Image loadImage(String fileName){
        if( fileName == null ) return null;   
        Image image = null;
        Toolkit toolkit = Toolkit.getDefaultToolkit();   
        try{
            image = toolkit.getImage(getClass().getResource(fileName));
        }
        catch(Exception exc1){
            byte[] buffer = null;
            InputStream in = getClass().getResourceAsStream(fileName);    
    	    try{
       	        int length = in.available();      	    
       	        buffer = new byte[length];
       	        in.read(buffer);
       	        in.close();
       	        image = toolkit.createImage(buffer);  	    
       	    }
            catch(Exception exc2) {}
        }   
        return image;
    }
    
    // This more general approach does not work with Jdk 1.2
    /*
    protected Image loadImage(String fileName){
    	if( fileName == null ) return null;    
    	Image image = null;
    	byte[] buffer = null;
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	InputStream in = getClass().getResourceAsStream(fileName);    
    	try{
       	    int length = in.available();      	    
       	    buffer = new byte[length];
       	    in.read(buffer);
       	    in.close();
       	    image = toolkit.createImage(buffer);  	    
       	}
        catch(Exception exc) {}
        return image;
    } 
    */


    /**
     * Defines new project
     */
    protected void newProject() {
	boolean result = this.confirmDialog();
	if (!result) return;
	project = new Project();
	filename = "noname.prj";
	frame.setTitle("Merl1n - "+filename);
	projectPanel.setProject(project);
	this.updateControls();
	this.setProjectChanged(false);
    }
    
    /**
     * Loads project from a disk.
     */
    protected void openProject() {
	boolean result = this.confirmDialog();
	if (!result) return;
        FileDialog fileDialog = new FileDialog(this.getFrame(),"Load Project",FileDialog.LOAD);
        fileDialog.setDirectory(this.getProjectHome());
	fileDialog.setFile("*.prj");
	fileDialog.setLocation(frame.getLocationOnScreen().x+10, frame.getLocationOnScreen().y+10);
        fileDialog.show();
        String name = fileDialog.getFile();
        if (name == null ) {
            return;
        }
	String directory = fileDialog.getDirectory();
	File file = new File(directory, name);
	if (file.exists()) {    
	    Cursor cursor = this.getFrame().getCursor();
	    this.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
	    ObjectInput input = null;
	    try { 
		String pathname = file.toString();
		input = new ObjectInputStream(new FileInputStream(pathname));	       
		project = (Project) input.readObject();
		projectPanel.setProject(project);
		this.filename = pathname;
		this.setProjectHome(directory);
		frame.setTitle("Merl1n - "+filename);
		this.getFrame().setCursor(cursor);
		this.updateControls();
		this.setProjectChanged(false);
	    }
	    catch (Exception e) {
		this.getFrame().setCursor(cursor);
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(
		    this.getFrame(), 
		    "Project not loaded!",
		    "Error!",
		    JOptionPane.ERROR_MESSAGE);
	    }
	    finally {
		try { 
		    input.close();
		}
		catch (Exception e) {}
	    }
	}
    }    
        
    /**
     * Saves project on a disk.
     */
    protected void saveProject() {
        if (filename.equals("noname.prj")) {
            saveAsProject();
            return;
        }
        this.saveProject(filename);
    }   
    
    /**
     * Saves project with a different name.
     */
    protected void saveAsProject() {
        FileDialog fileDialog = new FileDialog(this.getFrame(),"Save Project",FileDialog.SAVE);
        fileDialog.setDirectory(this.getProjectHome());
        fileDialog.setFile(new File(filename).getName());
        fileDialog.setLocation(frame.getLocationOnScreen().x+10, frame.getLocationOnScreen().y+10);
        fileDialog.show();
        String name = fileDialog.getFile();
        if (name == null ) {
            return;
        }
	if (!name.endsWith(".prj"))
	    name = name+".prj";
	String directory = fileDialog.getDirectory();
	File file = new File(directory,name);
        String pathname = file.toString();
        saveProject(pathname);
    }    
    
    /**
     * Saves project on a disk with a specified name.
     */
    protected void saveProject(String filename) {
	Cursor cursor = this.getFrame().getCursor();
	this.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));	    
	ObjectOutput output = null;
        try {
            output = new ObjectOutputStream(new FileOutputStream(filename));
            output.writeObject(project);
            this.filename = filename;
	    this.setProjectHome(new File(filename).getParent());    
	    frame.setTitle("Merl1n - "+filename);
	    this.getFrame().setCursor(cursor);
	    this.setProjectChanged(false);
        }
        catch (IOException e) {
	    this.getFrame().setCursor(cursor);
	    Toolkit.getDefaultToolkit().beep();
	    JOptionPane.showMessageDialog(
		this.getFrame(), 
		"Project not saved!",
		"Error!",
		JOptionPane.ERROR_MESSAGE);  
	}	
	finally {
	    try { 
		output.close();
	    }
	    catch (Exception e) {}
	}
    }    
    
    /**
     * Imports project from a disk.
     */
    protected void importModule() {
        FileDialog fileDialog = new FileDialog(this.getFrame(),"Import Knowledge Base",FileDialog.LOAD);
        fileDialog.setDirectory(this.getProjectHome());
	fileDialog.setFile("*.kb");
	fileDialog.setLocation(frame.getLocationOnScreen().x+10, frame.getLocationOnScreen().y+10);
        fileDialog.show();
        String name = fileDialog.getFile();
        if (name == null ) {
            return;
        }
	String directory = fileDialog.getDirectory();
	File file = new File(directory, name);
	if (file.exists()) {    
	    Cursor cursor = this.getFrame().getCursor();
	    this.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
	    InputStream input = null;
	    try { 
		String pathname = file.toString();
		int c;
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		input = new FileInputStream(pathname);
                while ((c = input.read()) != -1) {
                    buffer.write(c);
                }
                String source = buffer.toString();
                Module module = new Module();
                module.setSource(source);
		projectPanel.insertModule(module);
		this.getFrame().setCursor(cursor);
		project.setState(Public.IDLE);
		this.updateControls();
		this.setProjectChanged(true);
	    }
	    catch (Exception e) {
		this.getFrame().setCursor(cursor);
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(
		    this.getFrame(), 
		    "Knowledge base not imported!",
		    "Error!",
		    JOptionPane.ERROR_MESSAGE);
	    }
	    finally {
		try { 
		    input.close();
		}
		catch (Exception e) {}
	    }
	}
    }            
    
    /**
     * Exports module.
     */
    protected void exportModule() {
        Module module = projectPanel.getSelectedModule();
        if (module == null) return;
        FileDialog fileDialog = new FileDialog(this.getFrame(),"Export Knowledge Base",FileDialog.SAVE);
        fileDialog.setDirectory(this.getProjectHome());
        fileDialog.setFile("noname.kb");
        fileDialog.setLocation(frame.getLocationOnScreen().x+10, frame.getLocationOnScreen().y+10);
        fileDialog.show();
        String name = fileDialog.getFile();
        if (name == null ) {
            return;
        }
	if (!name.endsWith(".kb"))
	    name = name+".kb";
	String directory = fileDialog.getDirectory();
	File file = new File(directory,name);
        String pathname = file.toString();
	Cursor cursor = this.getFrame().getCursor();
	this.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));	    
	PrintWriter output = null;
        try {
            output = new PrintWriter(new FileOutputStream(pathname));
            output.print(module.getSource());
            output.flush();
	    this.getFrame().setCursor(cursor);
        }
        catch (IOException e) {
	    this.getFrame().setCursor(cursor);
	    Toolkit.getDefaultToolkit().beep();
	    JOptionPane.showMessageDialog(
		this.getFrame(), 
		"Knowledge base not exported!",
		"Error!",
		JOptionPane.ERROR_MESSAGE);  
	}	
	finally {
	    try { 
		output.close();
	    }
	    catch (Exception e) {}
	}
    }    
    
    /**
     * Builds application
     */
    protected void build() {
        Cursor cursor = this.getFrame().getCursor();
	this.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
	projectPanel.build();
	this.updateControls();
	this.getFrame().setCursor(cursor);
    }
    
    /**
     * Executes application
     */
    protected void execute() {
        Cursor cursor = this.getFrame().getCursor();
	this.getFrame().setCursor(new Cursor(Cursor.WAIT_CURSOR));
	new ExecutionDialog(this).show();
	this.getFrame().setCursor(cursor);
    }
    
    /**
     * Updates controls
     */
    public void updateControls() {
        if (project.getState() != Public.READY) {
            JMenuItem execItem = projectMenu.getItem(2);
            JButton execButton = (JButton) toolBar.getComponentAtIndex(13);
            execItem.setEnabled(false);
            execButton.setEnabled(false);
        }
        else {
            JMenuItem execItem = projectMenu.getItem(2);
            JButton execButton = (JButton) toolBar.getComponentAtIndex(13);
            execItem.setEnabled(true);
            execButton.setEnabled(true);
        }
    }
    
    /**
     * Confirm dialog
     * 
     * @return true if thread can continue
     */
    protected boolean confirmDialog() {
	if (!this.getProjectChanged()) return true;
	Toolkit.getDefaultToolkit().beep();
	int result = JOptionPane.showConfirmDialog(
			this.getFrame(), 
			"Do you want to save changes?",
			"Warning!",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.WARNING_MESSAGE); 
	switch (result) {
	    case JOptionPane.CANCEL_OPTION:
		return false;	
	    case JOptionPane.YES_OPTION:
		this.saveProject();
		break;
	    case JOptionPane.NO_OPTION:
		break;
	}
	return true;			   
    }

    /**
     * Exitss the application
     */
    protected void exit() {
	boolean result = this.confirmDialog();
	if (result) {
	    this.getFrame().setVisible(false);
	    this.getFrame().dispose();
	    System.exit(0);
	}
    }
}
