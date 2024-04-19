package merl1n.parser.message;

import merl1n.parser.*;
import merl1n.es.*;
import merl1n.es.Module;

/**
 * The FatalErrorMessage class displays errors caused by parsing
 *
 * @author Ivo Vondrak
 * @version 1.0 09/25/99
 */ 
public class FatalErrorMessage extends ErrorMessage {
    /**
     * Constructor
     */
    public FatalErrorMessage(Exception error, Module module) {
        super(error, module);
    }
    
    /**
     * Converts to string
     */
    public String toString() {
        return "Error in module <"+module.getName()+">: Fatal uknown error occured";
    }

}