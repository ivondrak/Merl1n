package merl1n.es;


/**
 * The EqualityNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class EqualityNode extends TreeNode {
    /**
     * Equality
     */
    public static final int EQUAL = 1;
    
    /**
     * Non Equality
     */
    public static final int NONEQUAL = 2;
    

    /**
     * Evaluate tree node
     */
    public Object evaluate() {
        int size = this.getNumOfChildren();
        if (size == 1)
            return this.getChild(0).evaluate();
        Object first = this.getChild(0).evaluate();
        Object second = this.getChild(1).evaluate();
        switch (operator) {
        case EQUAL:
            return new Boolean(first.equals(second));
        case NONEQUAL:
            return new Boolean(!first.equals(second));
        }
        // Better not to get here
        return new Boolean(false);
    }
        
}
