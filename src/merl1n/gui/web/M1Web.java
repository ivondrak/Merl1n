package merl1n.gui.web;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

import merl1n.app.*;
import merl1n.app.web.*;
import merl1n.gui.*;
import merl1n.gui.exec.*;
import merl1n.es.*;
import merl1n.tool.*;


/**
 * The M1Web enables evaluation of rules from Web browser
 *
 * @author Ivo Vondrak
 * @version 1.2.1 12/10/99
 */ 
public class M1Web extends JPanel implements AppType {
    /**
     * Reference to owning applet
     */
    protected JApplet applet;
    
    /**
     * Reference to owning frame
     */
    protected JFrame frame;       
    
    /**
     * Constructor
     */
    public M1Web(JApplet applet, String label, Project project) {
        this.applet = applet;
	this.frame = new JFrame();
        if (label == null)
            label = "Expert System";
        this.setLayout(new BorderLayout(5,5));	
        this.createIcons();
	// Center panel
	ExecutionPanel center = new ExecutionPanel(this, project);
	// South button panel
	JPanel south = new JPanel(new BorderLayout());
	//South-west panel
	JPanel south_west = new JPanel(new GridLayout(1,2,5,5));
	JButton close = new JButton(" Close ");
	close.setMnemonic('C');
	JButton about = new JButton(" About ");
	about.setMnemonic('A');
	south_west.add(close);
	south_west.add(about);
	south.add("West",south_west);
	// Register listener
	close.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    M1Web.this.close();
		}
	    }
	);
	about.addActionListener(
	    new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		    new AboutDialog(M1Web.this).show();
		}
	    }
	);
	center.setClose(close);
	Border border = new BevelBorder(BevelBorder.RAISED);
	Border margin = new EmptyBorder(5,5,5,5);
	center.setBorder(new CompoundBorder(border, margin));	
	south.setBorder(new EmptyBorder(5,5,5,5));
	this.add("Center",center);
	this.add("South",south);
	this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	this.frame.setTitle("Merl1n - "+label);
	this.frame.getContentPane().setLayout(new BorderLayout());
	this.frame.getContentPane().add("Center",this);
    }

    /**
     * Shows applet window
     */
    public void show() {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	float scale = (float) 2f/3f;
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
	this.getFrame().setLocation(x,y);
	// Sets glass pane
    	this.frame.setGlassPane(new GlassPanel());
	this.frame.show();
	this.validate();
	this.repaint();
        this.requestFocus();
	((run) applet).setStatus("Expert System Merl1n started");
    }
       
    /**
     * Gets owning applet
     */
    public Applet getApplet() {
        return applet;
    }
            
    /**
     * Gets the application frame
     */
    public Frame getFrame() {
        return frame;
    }
    
    /**
     * Updates controls
     */
    public void updateControls() {
        // Nothing to do here
    }
    
    /**
     * Creates icons for an application
     */
    public void createIcons() {
    	String text = Public.M1WEB;
        Public.ICONS.put(text, new ImageIcon(loadImage("/images/m1web.gif"),text));
        this.getFrame().setIconImage(((ImageIcon) Public.ICONS.get(text)).getImage());
        text = Public.M1WEB2X;
        Public.ICONS.put(text, new ImageIcon(loadImage("/images/m1web2x.gif"),text));
        text = Public.EXECUTE;
        Public.ICONS.put(text, new ImageIcon(loadImage("/images/execute.gif"),text));
        text = Public.TRUE;
        Public.ICONS.put(text, new ImageIcon(loadImage("/images/true.gif"),text));
        text = Public.FALSE;
        Public.ICONS.put(text, new ImageIcon(loadImage("/images/false.gif"),text));        
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
    
    /**
     * Close button clicked
     */
    protected void close() {
	this.getFrame().setVisible(false);
	this.getFrame().dispose();
	((run) applet).setStatus("Expert System Merl1n finished");
    }    
}