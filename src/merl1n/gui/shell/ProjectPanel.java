package merl1n.gui.shell;

import java.util.*;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

import merl1n.app.*;
import merl1n.gui.*;
import merl1n.es.*;
import merl1n.es.Module;
import merl1n.tool.*;
import merl1n.parser.*;
import merl1n.parser.message.*;


/**
 * The ProjectPanel class is GUI element for project definition
 *
 * @author Ivo Vondrak
 * @version 1.3.2 06/20/00
 */ 
public class ProjectPanel extends JSplitPane {
    /**
     * Reference to application
     */
    protected AppType app;
    
    /**
     * Reference to project
     */
    protected Project project;
    
    /**
     * Vector of knowledge modules
     */
    protected Vector modules;
    
    /**
     * Modules list model
     */
    protected DefaultListModel moduleModel;
    
    /**
     * List of modules
     */
    protected JList moduleList;    
    
    /**
     * Text Editor
     */
    protected JTextArea editor;
    
    /**
     * Vector of messages
     */
    protected Vector messages;
    
    /**
     * List of messages
     */
    protected JList messageList;
    
    /**
     * Messages list model
     */
    protected DefaultListModel messageModel;
    
    /**
     * Document listener
     */
    protected DocumentListener editorListener =
	new DocumentListener() {
	    public void changedUpdate(DocumentEvent e) {
	        ProjectPanel.this.textChanged();
	    }
	    public void insertUpdate(DocumentEvent e) {
	        ProjectPanel.this.textChanged();
	    }
	    public void removeUpdate(DocumentEvent e) {
	        ProjectPanel.this.textChanged();
	    }
	};
    

    /**
     * Constructor
     */
    public ProjectPanel(AppType app, Project project) {
        super(VERTICAL_SPLIT);
	this.app = app;
	this.project = project;
	this.modules = project.getModules();
	this.messages = new Vector();
	moduleModel = new DefaultListModel();
	for (Enumeration e = modules.elements(); e.hasMoreElements();) {
	    moduleModel.addElement(e.nextElement());
	}
	messageModel = new DefaultListModel();
	for (Enumeration e = modules.elements(); e.hasMoreElements();) {
	    messageModel.addElement(e.nextElement());
	}	
	JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	moduleList = new JList(moduleModel);
	moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	moduleList.setCellRenderer(
	    new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(
	            JList list, 
	            Object value, 
	            int index, 
	            boolean isSelected, 
	            boolean cellHasFocus) {
	                String text = ((Module) value).getName();
	                return super.getListCellRendererComponent(
	                    list, 
	                    text, 
	                    index, 
	                    isSelected, 
	                    cellHasFocus);
                    }
	    }
	);
	// Register listener
	moduleList.addListSelectionListener(
	    new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    ProjectPanel.this.changeModule();
		}
	    }
	);
	moduleList.addMouseListener(
	    new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
		    ProjectPanel.this.modulesMouseClicked(e);
		}
	    }
	);
	JScrollPane leftScroll = new JScrollPane(moduleList);
	editor = new JTextArea();
	new TextEditMenu(editor);
	editor.setLineWrap(false);
        editor.setTabSize(4);
        editor.setForeground(new Color(0,0,125));
	editor.getDocument().addDocumentListener(editorListener);
        editor.addKeyListener(
            new KeyAdapter() {
                public void keyReleased(KeyEvent event) {
                    if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                        ProjectPanel.this.newLine();
                    }
                }
            }
        );
	JScrollPane rightScroll = new JScrollPane(editor);
	leftScroll.setPreferredSize(new Dimension(100,50));
	splitPanel.setLeftComponent(leftScroll);
	splitPanel.setRightComponent(rightScroll);
	messageList = new JList(messageModel);
	messageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	messageList.setCellRenderer(
	    new DefaultListCellRenderer() {
                public Component getListCellRendererComponent(
	            JList list, 
	            Object value, 
	            int index, 
	            boolean isSelected, 
	            boolean cellHasFocus) {
	                String text = ((Message) value).toString();
	                return super.getListCellRendererComponent(
	                    list, 
	                    text, 
	                    index, 
	                    isSelected, 
	                    cellHasFocus);
                    }
	    }
	);
	// Register listener
	messageList.addListSelectionListener(
	    new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    ProjectPanel.this.messageSelected();
		}
	    }
	);
	JScrollPane messageScroll = new JScrollPane(messageList);
	JPanel bottom = new JPanel();
	bottom.setLayout(new BorderLayout());
	JLabel label = new JLabel(" Messages:");	
	bottom.add("North",label);
	bottom.add("Center",messageScroll);     
	this.setTopComponent(splitPanel);
	this.setBottomComponent(bottom);
	this.ensureAtLeastOne();
	moduleList.setSelectedIndex(0);
    }
     
    /**
     * Gets 
    
    /**
     * Sets new project
     */
    public void setProject(Project project) {
        this.project = project;
	this.modules = project.getModules();
	moduleModel.removeAllElements();
	for (Enumeration e = modules.elements(); e.hasMoreElements();) {
	    moduleModel.addElement(e.nextElement());
	} 
	this.ensureAtLeastOne();
	moduleList.setSelectedIndex(0);  
    }
       
    /**
     * Gets selected module
     *
     * @return selected module
     */
    public Module getSelectedModule() {
	Module m = (Module) moduleList.getSelectedValue();
	if (m == null) 
	    return null;
	else
	    return m; 
    }
             
    /**
     * Inserts new module
     */
    public void insertModule() {
	Module m = new Module();
	int index = moduleList.getSelectedIndex();
	if (index < 0) {
	    modules.addElement(m);
	    moduleModel.addElement(m);
	}
	else {
	    modules.insertElementAt(m, index+1);
	    moduleModel.insertElementAt(m, index+1);	    
	}
	moduleList.setSelectedValue(m, true);
	project.setState(Public.IDLE);
	app.updateControls();
	((ProjectType) app).setProjectChanged(true);
    } 
    
    /**
     * Inserts predefined module
     *
     * @param module module to insert
     */
    public void insertModule(Module module) {
	Module m = module;
	int index = moduleList.getSelectedIndex();
	if (index < 0) {
	    modules.addElement(m);
	    moduleModel.addElement(m);
	}
	else {
	    modules.insertElementAt(m, index+1);
	    moduleModel.insertElementAt(m, index+1);	    
	}
	moduleList.setSelectedValue(m, true);
	((ProjectType) app).setProjectChanged(true);
    } 
    
    
    /**
     * Deletes selected module
     */
    public void deleteModule() {
	Module m = (Module) moduleList.getSelectedValue();
	int index = moduleList.getSelectedIndex();
	if (m == null) return;
	// "If" is here because of bug in a Swing:
	// "First element removed from list" event is not fired
	if (modules.indexOf(m) == 0) {
	    modules.removeElement(m);
	    moduleModel.removeRange(0,0);
	}
	else {
	    modules.removeElement(m);
	    moduleModel.removeElement(m);
	}
	this.ensureAtLeastOne();
	if (index == modules.size()) {
	    moduleList.setSelectedIndex(index - 1);
	}
	else {
	    moduleList.setSelectedIndex(index);
	}
	project.setState(Public.IDLE);
	app.updateControls();
	((ProjectType) app).setProjectChanged(true);
    }
    
    /**
     * Renames selected module
     */
    public void renameModule() {
	Module m = (Module) moduleList.getSelectedValue();
	if (m == null) return;
	String name = m.getName();
	new RenameModule(app,m).show();
	moduleList.setSelectedValue(m, true);
	if (!name.equals(m.getName())) {
	    project.setState(Public.IDLE);
	    app.updateControls();
	}
	((ProjectType) app).setProjectChanged(true);   
    }
    
    /**
     * Mouse is clicked
     */
    public void modulesMouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
	    this.renameModule();
        }
    }     
            
    /**
     * Move module up in a list
     */
    public void moveUp() {
	Module m = (Module) moduleList.getSelectedValue();
	int index = moduleList.getSelectedIndex();
	if (m == null) return;
	modules.removeElement(m);
	moduleModel.removeElement(m);
	index = index - 1;
	if (index < 0) index = 0;
	modules.insertElementAt(m,index);
	moduleModel.insertElementAt(m,index);
	moduleList.setSelectedValue(m, true);
	((ProjectType) app).setProjectChanged(true);
    }
    
    /**
     * Move module down in a list
     */
    public void moveDown() {
	Module m = (Module) moduleList.getSelectedValue();
	int index = moduleList.getSelectedIndex();
	if (m == null) return;
	modules.removeElement(m);
	moduleModel.removeElement(m);
	index = index + 1;
	int size = modules.size();
	if (index > size) index = size;
	modules.insertElementAt(m,index);
	moduleModel.insertElementAt(m,index);
	moduleList.setSelectedValue(m, true);
	((ProjectType) app).setProjectChanged(true);
    }
    
    /**
     * Ensure that at least one module exists
     */
    protected void ensureAtLeastOne() {
	if (modules.size() == 0) {
	    merl1n.es.Module m = new merl1n.es.Module();
	    modules.addElement(m);
	    moduleModel.addElement(m);
	}
    }  
    
    /**
     * Change module
     */
    public void changeModule() {
	Module m = (Module) moduleList.getSelectedValue();
	if (m == null) return;
	editor.getDocument().removeDocumentListener(editorListener);
	editor.setText(m.getSource());
	editor.select(0, 0);
	editor.getDocument().addDocumentListener(editorListener);
    } 
        
    /**
     * Text of module changed
     */
    public void textChanged() {
	Module m = (Module) moduleList.getSelectedValue();
	if (m == null) return;
	m.setSource(editor.getText());
	if (project.getState() == Public.READY) {
	    project.setState(Public.IDLE);
	    app.updateControls();
	}
	((ProjectType) app).setProjectChanged(true);   
    }
        
        
    /**
     * Cut text
     */
    protected void cutText() {
        editor.cut();
    }
    
    /**
     * Makes copy of a selected text
     */
    protected void copyText() {
        editor.copy();
    }
    
    /**
     * Paste text
     */
    protected void pasteText() {
	editor.paste();
    }
    
    /**
     * Delete text
     */
    protected void deleteText() {
	editor.replaceSelection("");
    }
    
    /**
     * Selects all text
     */
    public void selectAll() {
        editor.grabFocus();
	editor.selectAll();
    }
    
    /**
     * Builds application
     */
    public void build() {
        project.getVariables().clear();
        for (Enumeration ms = project.getModules().elements(); ms.hasMoreElements();) {
            Module m = (Module) ms.nextElement();
            m.getVariables().removeAllElements();
            m.getRules().removeAllElements();
        }
        boolean success = true;
        this.resetMessages();
        for (Enumeration ms = project.getModules().elements();ms.hasMoreElements();) {
            Module m = (Module) ms.nextElement();
            char[] characters = (m.getSource()+"\n").toCharArray();
            StringBuffer source = new StringBuffer();
            for (int i=0; i < characters.length; i++) {
                char c = characters[i];
                if (Character.isLetter(c)) {
                    source.append("\\u");
                    String s = Integer.toHexString((int) c);
                    while(s.length() < 4)
                        s = "0"+s;
                    source.append(s);
                }
                else {
                    source.append(c);
                }
            }
            Parser parser = new Parser(new StringReader(source.toString()));
            parser.setProject(project);
            parser.setModule(m);
            try {                
                parser.compile();
            }
            catch (TokenMgrError e) {
                this.addMessage(new LexicalErrorMessage(e, m));
                success = false;
            }
            catch (ParseException e) {
                this.addMessage(new SyntaxErrorMessage(e, m));
                success = false;
            }
            catch (SemanticError e) {
                this.addMessage(new SemanticErrorMessage(e, m));
                success = false;
            }            
            catch (Exception e) {
                this.addMessage(new FatalErrorMessage(e, m));
                success = false;
            }
        }
        if (Public.EVAL) {
            int size = 0;
            for (Enumeration ms = project.getModules().elements(); ms.hasMoreElements();) {
                Module m = (Module) ms.nextElement();
                size = size + m.getRules().size();
            }
            if (size > 12) {
                project.setState(Public.IDLE);
                project.getVariables().clear();
                for (Enumeration ms = project.getModules().elements(); ms.hasMoreElements();) {
                    Module m = (Module) ms.nextElement();
                    m.getVariables().removeAllElements();
                    m.getRules().removeAllElements();
                }
                this.addMessage(new FailedMessage());
                messageList.setSelectedIndex(0);
		Toolkit.getDefaultToolkit().beep();
		JOptionPane.showMessageDialog(
		    app.getFrame(), 
		    "Evaluation version allows only 12 rules!",
		    "Error!",
		    JOptionPane.ERROR_MESSAGE);
		return;
            }
        }        
        if (success) {
            project.setState(Public.READY);
            ((ProjectType) app).setProjectChanged(true);
            this.addMessage(new SuccessMessage());
        }
        else {
            project.setState(Public.IDLE);
            project.getVariables().clear();
            for (Enumeration ms = project.getModules().elements(); ms.hasMoreElements();) {
                Module m = (Module) ms.nextElement();
                m.getVariables().removeAllElements();
                m.getRules().removeAllElements();
            }
            this.addMessage(new FailedMessage());
            messageList.setSelectedIndex(0);
        }
    }
    
    /**
     * Resets messages
     */
    public void resetMessages() {
	messageModel.removeAllElements();
    }
    
    /**
     * Adds new message
     */
    public void addMessage(Message message) {
	messages.addElement(message);
	messageModel.addElement(message);
    }
    
    /**
     * Message selected
     */
    public void messageSelected() {
        int line;
	Message m = (Message) messageList.getSelectedValue();
	if (m == null) return;
	line = m.getLine();
	if (line == -1) return;
	Module module = ((ErrorMessage) m).getModule();
	moduleList.setSelectedValue(module,true);	
	try {
            editor.grabFocus();
	    int start = editor.getLineStartOffset(line-1);
	    int end = editor.getLineEndOffset(line-1);
	    editor.select(start, end-1);
	}
	catch (Exception e) {}
    } 
    
    /**
     * New line generated in source code editor
     */
    protected void newLine() {
        int pos = editor.getCaretPosition();
        String frontText = editor.getText().substring(0,pos);
        LineNumberReader reader = new LineNumberReader(new StringReader(frontText));
        String line = "";
        try {
            String l;
            while ((l = reader.readLine()) != null) {
                line = l;
            }
            if (line.startsWith("\t")) {
                line = line.trim();
                if (line.length() > 0) {
                    editor.getDocument().insertString(pos,"\t",null);
                }
            }
            else {
                line = line.trim();
                if (line.startsWith("if") || line.startsWith("then")) {
                    editor.getDocument().insertString(pos,"\t",null);
                } 
            }
        }
        catch (Exception e) {
            return;
        }
    }
    
    
}