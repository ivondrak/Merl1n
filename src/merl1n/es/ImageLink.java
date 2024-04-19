package merl1n.es;

import java.net.*;
import merl1n.tool.*;

/**
 * The ImageLink class prints hyperlink to image on es output
 *
 * @author Ivo Vondrak
 * @version 2.0 09/20/2001
 */ 
public class ImageLink extends Link {
    
    /**
     * Executes action part
     */
    public void execute() {
        String url = (String) location.evaluate()+"#[IMAGE]";
        String ref = (String) label.evaluate();
        project.print("<A HREF="+url+">"+ref+"</A>");
    }
    
    /**
     * Gets action type
     */
    public int getType() {
        return Public.IMAGE;
    }
}
