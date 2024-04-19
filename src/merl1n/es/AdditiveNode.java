package merl1n.es;


/**
 * The AdditiveNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class AdditiveNode extends TreeNode {
    /**
     * Plus
     */
    public static final int PLUS = 1;
    
    /**
     * Minus
     */
    public static final int MINUS = 2;
    
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
        case PLUS:
            if (first instanceof Float || second instanceof Float)
                return new Float(first.floatValue() + second.floatValue());
            else
                return new Integer(first.intValue() + second.intValue());
        case MINUS:
            if (first instanceof Float || second instanceof Float)
                return new Float(first.floatValue() - second.floatValue());
            else
                return new Integer(first.intValue() - second.intValue());
        }
        // Better not to get here
        return new Integer(0);
    }
        
}
