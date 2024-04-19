package merl1n.es;

import java.io.*;

/**
 * The LiteralNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class LiteralNode extends TreeNode {
    /**
     * Literal value
     */
    protected Object value;
    
    /**
     * Sets value
     */
    public void setValue(Object value) {
        this.value = value;
    }
    
    /**
     * Gets value
     */
    public Object getValue() {
        return value;
    }


    /**
     * Evaluate tree node
     */
    public Object evaluate() {
        return value;
    }
        
}
