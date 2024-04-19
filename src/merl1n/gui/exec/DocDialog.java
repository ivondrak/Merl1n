package merl1n.gui.exec;

import java.util.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

import merl1n.app.*;
import merl1n.tool.*;
import merl1n.gui.*;


/**
 * The DocPanel shows document
 *
 * @author Ivo Vondrak
 * @version 2.0 09/26/2001
 */ 
public class DocDialog extends ModalDialog  {
    /**
     * Reference to editor pane
     */
    protected JEditorPane editor;
    
    /**
     * Pointer to actual document
     */
    protected int pointer;
    
    /**
     * Vector of documents
     */
    protected Vector documents;
    
    /**
     * Reference to URL text field
     */
    protected JTextField text;
    
    /**
     * Reference to toolBar
     */
    protected JToolBar toolBar;
    
    /**
     * Constructor
     */
    public DocDialog(AppType app, URL url) {       
        super(app, "Document Viewer");
        pointer = -1;
        documents = new Vector();
	this.setLayout(new BorderLayout(2,2));
        // North panel
        JPanel north = new JPanel(new BorderLayout());
        // North center panel
        JPanel north_center = new JPanel(new BorderLayout());
        String location = this.url2String(url);
        text = new JTextField(location);
        this.createToolBar(north);
        north_center.add("West",new JLabel("URL: "));
        north_center.add("Center",text);
        new TextEditMenu(text);
        north.add("Center",north_center);
	// Center label panel
        JPanel center = new JPanel(new BorderLayout());
        JLabel label = new JLabel(" Loading document ... ");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.blue);
        center.add("Center",label);        
	JScrollPane scroll = new JScrollPane(center);        
	JScrollBar scrollBar = scroll.getVerticalScrollBar();
	scrollBar.setUnitIncrement(10);
	scrollBar = scroll.getHorizontalScrollBar();
	scrollBar.setUnitIncrement(10);
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
		    DocDialog.this.close();
		}
	    }
	);
        close.setEnabled(false);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add("Center",scroll);
        north.setBorder(new EmptyBorder(0,0,0,5));
	Border border = new BevelBorder(BevelBorder.RAISED);
	Border margin = new EmptyBorder(5,5,5,5);
        panel.setBorder(new CompoundBorder(border, margin));
	south.setBorder(new EmptyBorder(5,5,5,5));
        this.add("North",north);
        this.add("Center",panel);
        this.add("South",south);
        dialog.setSize(580,460);
        this.loadDocument(label, close, url);
    }
    
    /**
     * Loads document in a parallel thread
     */
    protected void loadDocument(final JLabel label, final JButton close, final URL location) {
        new Thread(new Runnable() {
            public void run() {
                editor = new JEditorPane();
                editor.setContentType("text/html");
                editor.setEditable(false);
                editor.addHyperlinkListener(new HyperlinkListener() {
                    public void hyperlinkUpdate(HyperlinkEvent e) {
                        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                            JEditorPane pane = (JEditorPane) e.getSource();                            
                            if (e instanceof HTMLFrameHyperlinkEvent) {
                                HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
                                HTMLDocument doc = (HTMLDocument)pane.getDocument();
                                doc.processHTMLFrameHyperlinkEvent(evt);
                            } 
                            else {
                                String location = e.getDescription();
                                text.setText(location);
                                try {
                                    pane.setPage(e.getURL());
                                }
                                catch (Exception ex) {
                                    pane.setText("<b>Page \""+e.getDescription()+"\" not found!</b>");
                                }
                                pointer++;
                                Vector pair = new Vector();
                                pair.addElement(location);
                                pair.addElement(pane.getDocument());
                                Vector newDocuments = new Vector();
                                for (int i=0; i < pointer; i++) {
                                    newDocuments.addElement(documents.elementAt(i));
                                }
                                newDocuments.addElement(pair);
                                documents = newDocuments; 
                                DocDialog.this.updateToolbar();                                 
                            }                           
                        }
                    }
                });                
                try {
                    editor.setPage(location);
                    JScrollPane scroll = new JScrollPane(editor);        
                    JScrollBar scrollBar = scroll.getVerticalScrollBar();
                    scrollBar.setUnitIncrement(10);
                    scrollBar = scroll.getHorizontalScrollBar();
                    scrollBar.setUnitIncrement(10);
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.add("Center",scroll);
                    Border border = new BevelBorder(BevelBorder.RAISED);
                    Border margin = new EmptyBorder(5,5,5,5);
                    panel.setBorder(new CompoundBorder(border, margin));                    
                    DocDialog.this.remove(1);                    
                    DocDialog.this.add("Center",panel);
                    dialog.setSize(580,460);
                    dialog.invalidate();
                    dialog.validate();
                    pointer = 0;
                    Vector pair = new Vector();
                    pair.addElement(DocDialog.this.url2String(location));
                    pair.addElement(editor.getDocument());
                    documents.addElement(pair);
                    DocDialog.this.updateToolbar();
                }
                catch (Exception e) {
                    Toolkit.getDefaultToolkit().beep();
                    label.setForeground(Color.red);
                    label.setText(" Document not found! ");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                }
                close.setEnabled(true);
            }
        }).start();
    }

    /**
     * Creates tool bar for the Document Viewer
     */
    protected void createToolBar(JPanel panel) {
	toolBar = new JToolBar();
	toolBar.setFloatable(false);
	toolBar.setBorderPainted(false);
	ImageIcon icon = (ImageIcon) Public.ICONS.get(Public.BACK);
	JButton backItem = new JButton(icon);
	backItem.setToolTipText("One page back");
	backItem.setMargin(new Insets(1,1,1,1));
	backItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    DocDialog.this.pageBack();
		}
	    }
	);
	icon = (ImageIcon) Public.ICONS.get(Public.FORWARD);
	JButton forwardItem = new JButton(icon);
	forwardItem.setToolTipText("One page forward");
	forwardItem.setMargin(new Insets(1,1,1,1));
	forwardItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    DocDialog.this.pageForward();
		}
	    }
	);
	icon = (ImageIcon) Public.ICONS.get(Public.HOME);
	JButton homeItem = new JButton(icon);
	homeItem.setToolTipText("Home page");
	homeItem.setMargin(new Insets(1,1,1,1));
	homeItem.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    DocDialog.this.homePage();
		}
	    }
	);        
	toolBar.add(backItem);
	toolBar.add(forwardItem);
        toolBar.add(homeItem);
	toolBar.addSeparator();
        this.updateToolbar();
	panel.add("West",toolBar);
    }     
    
    /**
     * One page back
     */
    protected void pageBack() {
        pointer--;
        Vector pair = (Vector) documents.elementAt(pointer);
        text.setText((String) pair.elementAt(0));
        editor.setDocument((Document) pair.elementAt(1));
        this.updateToolbar();
    }
    
    /**
     * One page forward
     */
    protected void pageForward() {
        pointer++;
        Vector pair = (Vector) documents.elementAt(pointer);
        text.setText((String) pair.elementAt(0));
        editor.setDocument((Document) pair.elementAt(1));
        this.updateToolbar();
    }    

    /**
     * Home page
     */
    protected void homePage() {
        pointer = 0;
        Vector pair = (Vector) documents.elementAt(pointer);
        text.setText((String) pair.elementAt(0));
        editor.setDocument((Document) pair.elementAt(1));
        this.updateToolbar();
    }      
    
    /**
     * Update buttons of a toolbar
     */
    protected void updateToolbar() {
        JButton back = (JButton) toolBar.getComponentAtIndex(0);
        JButton forward = (JButton) toolBar.getComponentAtIndex(1);
        JButton home = (JButton) toolBar.getComponentAtIndex(2);
        if (pointer < 0) {
            back.setEnabled(false);
            forward.setEnabled(false);
            home.setEnabled(false);
        }
        if (pointer == 0) {
            back.setEnabled(false);
            home.setEnabled(true);
        }
        if (pointer > 0) {
            back.setEnabled(true);
            home.setEnabled(true);
        }
        if ((pointer+1) < documents.size()) {
            forward.setEnabled(true);
        }
        else {
            forward.setEnabled(false);
        }
    }
    
    /**
     * Converts URL to String
     */
    protected String url2String(URL url) {
        return url.getProtocol()+
                    "://"+
                    (url.getHost().equals("") ? "" : url.getHost())+
                    (url.getPort() == -1 ? "" : ":"+url.getPort())+
                    url.getFile();
    }
    
    /**
     * Close button clicked
     */
    protected void close() {
	dialog.dispose();
    }    
}