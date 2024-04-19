package merl1n.es;

import java.io.*;

/**
 * The Action class executes action part of a rule
 *
 * @author Ivo Vondrak
 * @version 1.0 09/28/99
 */ 
public abstract class Action implements Serializable {
    /**
     * Executes action
     */
    public abstract void execute();
    
    /**
     * Gets action type
     */
    public abstract int getType();
}