package merl1n.parser.message;

import merl1n.parser.*;
import merl1n.es.*;

/**
 * The Message is a root class for building messages
 *
 * @author Ivo Vondrak
 * @version 1.0 09/25/99
 */ 
public abstract class Message {
    /**
     * Gets line the message is associated with
     *
     * @return -1 if no line is asscoiated
     */
    public int getLine() {
        return -1;
    }
    
    /**
     * Converts to string
     */
    public abstract String toString();
}