package merl1n.app;

import java.awt.*;
import java.applet.*;


/**
 * The AppType interface defines the access to core of Merl1n applications 
 * type (applet or standalone) 
 *
 * @author Ivo Vondrak
 * @version 1.0 09/21/99
 */ 
public interface AppType  {
    /**
     * Get main frame of the application
     *
     * @return only frame from AWT package!!!
     */
    Frame getFrame();
    
    /**
     * Gets owning applet
     *
     *@return null if the application is standalone
     */
    public Applet getApplet();
    
    /**
     * Update controls
     */
    void updateControls();
}