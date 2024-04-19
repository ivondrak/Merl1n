package merl1n.es;

import merl1n.tool.*;

/**
 * The DocLink class prints hyperlink to html on es output
 *
 * @author Ivo Vondrak
 * @version 2.0 09/20/2001
 */ 
public class DocLink extends Link {
    
    /**
     * Executes action part
     */
    public void execute() {
        String url = (String) location.evaluate();
        String ref = (String) label.evaluate();
        project.print("<A HREF="+url+">"+ref+"</A>");
    }
    
    /**
     * Gets action type
     */
    public int getType() {
        return Public.DOCUMENT;
    }
}


