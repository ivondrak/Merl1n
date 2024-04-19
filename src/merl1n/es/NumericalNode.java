package merl1n.es;


/**
 * The NumericalNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class NumericalNode extends TreeNode {
    /**
     * Evaluate tree node
     */
    public Object evaluate() {
        return this.getChild(0).evaluate();
    }       
}
