package merl1n.es;

import java.io.*;
import java.util.*;

/**
 * The TreeNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public abstract class TreeNode implements Serializable {
    /**
     * Reference to  children
     */
    protected Vector children;
    
    /**
     * Reference to operator type
     */
    protected int operator;
    
    /**
     * Sets operator
     */
    public void setOperator(int operator) {
        this.operator = operator;
    }        
    
    /**
     * Constructor
     */
    public TreeNode() {
        children = new Vector();
    }
    
    /**
     * Adds a node
     */
    public void addChild(TreeNode node) {
        children.addElement(node);
    }
    
    /**
     * Gets a node from a given position
     */
    public TreeNode getChild(int id) {
        return (TreeNode) children.elementAt(id);
    }
     
    /**
     * Gets number of children
     */
    public int getNumOfChildren() {
        return children.size();
    }
    
    /**
     * Evaluate tree node
     */
    public abstract Object evaluate();
    
}
