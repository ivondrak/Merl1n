package merl1n.es;

import java.util.*;

/**
 * The IdentifierNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class IdentifierNode extends TreeNode {
    /**
     * Reference to variables
     */
    protected Hashtable variables;
    
    /**
     * Reference to variable name
     */
    protected String name;
    
    
    /**
     * Constructor
     */
    public IdentifierNode(Hashtable variables) {
        this.variables = variables;
        name = "";
    }    

    /**
     * Sets name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets name
     */
    public String getName() {
        return name;
    }

    /**
     * Evaluate tree node
     */
    public Object evaluate() {
        return ((Variable) variables.get(name)).getValue();
    }
        
}
