package merl1n.es;


/**
 * The RelationalNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class RelationalNode extends TreeNode {
    /**
     * Lower than
     */
    public static final int LT = 1;
    
    /**
     * Greater than
     */
    public static final int GT = 2;
    
    /**
     * Lower or equal
     */
    public static final int LE = 3;
    
    /**
     * Greater or equal
     */
    public static final int GE = 4;
    
    /**
     * Evaluate tree node
     */
    public Object evaluate() {
        int size = this.getNumOfChildren();
        if (size == 1)
            return this.getChild(0).evaluate();
        Number first = (Number) this.getChild(0).evaluate();
        Number second = (Number) this.getChild(1).evaluate();
        switch (operator) {
        case LT:
            return new Boolean(first.floatValue() < second.floatValue());
        case GT:
            return new Boolean(first.floatValue() > second.floatValue());
        case LE:
            return new Boolean(first.floatValue() <= second.floatValue());
        case GE:
            return new Boolean(first.floatValue() >= second.floatValue());
        }
        // Better not to get here
        return new Boolean(false);
    }
        
}
