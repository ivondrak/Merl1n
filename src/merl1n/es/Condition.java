package merl1n.es;

import java.io.*;

/**
 * The Condition class evaluates condition expresion for a rule
 *
 * @author Ivo Vondrak
 * @version 1.0 09/23/99
 */ 
public class Condition implements Serializable {
    /**
     * Root of boolean expression
     */
    protected BooleanNode root;
    
    /**
     * Sets root
     */
    public void setRoot(BooleanNode root) {
        this.root = root;
    }
    
    /**
     * Gets root
     */
    public BooleanNode getRoot() {
        return root;
    }
    
    /**
     * Evaluate condition
     */
    public boolean evaluate() {
        return ((Boolean) root.evaluate()).booleanValue();
    }
}