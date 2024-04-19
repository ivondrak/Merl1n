package merl1n.gui.exec;

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import merl1n.app.*;
import merl1n.tool.*;
import merl1n.gui.*;


/**
 * The ImagePanel shows image
 *
 * @author Ivo Vondrak
 * @version 2.0 09/24/2001
 */ 
public class ImageDialog extends ModalDialog  {
    
    /**
     * Constructor
     */
    public ImageDialog(AppType app, URL url) {       
        super(app, "Image Viewer");
	this.setLayout(new BorderLayout(2,2));
        // North panel
        JPanel north = new JPanel(new BorderLayout());
        String location = url.getProtocol()+
                        "://"+
                        (url.getHost().equals("") ? "" : url.getHost())+
                        (url.getPort() == -1 ? "" : ":"+url.getPort())+
                        url.getFile();
        JTextField text = new JTextField(location);
        north.add("West",new JLabel("URL: "));
        north.add("Center",text);
        new TextEditMenu(text);
	// Center label panel
        JPanel center = new JPanel(new BorderLayout());
        JLabel label = new JLabel(" Loading image ... ");
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
		    ImageDialog.this.close();
		}
	    }
	);      
        close.setEnabled(false);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add("Center",scroll);
        north.setBorder(new EmptyBorder(0,5,0,5));
	Border border = new BevelBorder(BevelBorder.RAISED);
	Border margin = new EmptyBorder(5,5,5,5);
        panel.setBorder(new CompoundBorder(border, margin));
	south.setBorder(new EmptyBorder(5,5,5,5));
        this.add("North",north);
        this.add("Center",panel);
        this.add("South",south);
        dialog.setSize(380,320);
        this.loadImage(label, close, url);
    }
    
    /**
     * Loads image in a parallel thread
     */
    protected void loadImage(final JLabel label, final JButton close, final URL location) {
        new Thread(new Runnable() {
            public void run() {
                ImageIcon image = null;
                try {
                    image = new ImageIcon(location);
                }
                catch (Exception e) {}
                if (image != null && image.getIconHeight() > 0 && image.getIconWidth() > 0) {
                    label.setText("");
                    label.setIcon(image);
                }
                else {
                    Toolkit.getDefaultToolkit().beep();
                    label.setForeground(Color.red);
                    label.setText(" Image not found! ");
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                }
                close.setEnabled(true);
            }
        }).start();
    }
    
    /**
     * Close button clicked
     */
    protected void close() {
	dialog.dispose();
    }    
}