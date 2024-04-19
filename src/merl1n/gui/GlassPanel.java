package merl1n.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The GlassPanel class locks the frame by getting visible
 *
 * @author Ivo Vondrak
 * @version 1.0 07/26/99
 */
public class GlassPanel extends JPanel  {

    /**
     * Constructor
     */
    public GlassPanel() {
    	this.setOpaque(false);
    	this.addMouseListener(
    	    new MouseAdapter() {}
    	);
    	this.addMouseMotionListener(
    	    new MouseMotionAdapter() {}
    	);
    	this.addKeyListener(
    	    new KeyAdapter() {}
    	);
    }
}