package merl1n.parser.message;

import merl1n.parser.*;
import merl1n.es.*;

/**
 * The FailedMessage displayes failure
 *
 * @author Ivo Vondrak
 * @version 1.0 09/25/99
 */ 
public class FailedMessage extends Message {
    
    /**
     * Converts to string
     */
    public String toString() {
        return "Build Failed";
    }
}