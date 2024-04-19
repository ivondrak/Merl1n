package merl1n.es;


/**
 * The BooleanNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class BooleanNode extends TreeNode {
    /**
     * Evaluate tree node
     */
    public Object evaluate() {
        int size = this.getNumOfChildren();
        if (size == 1)
            return this.getChild(0).evaluate();
        boolean first = ((Boolean) this.getChild(0).evaluate()).booleanValue();
        boolean second = ((Boolean) this.getChild(1).evaluate()).booleanValue();
        return new Boolean(first || second);
    }
        
}
