package merl1n.es;


/**
 * The MultiplicativeNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class MultiplicativeNode extends TreeNode {
    /**
     * Mul
     */
    public static final int MUL = 1;
    
    /**
     * Div
     */
    public static final int DIV = 2;
    
    /**
     * Mod
     */
    public static final int MOD = 3;
        
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
        case MUL:
            if (first instanceof Float || second instanceof Float)
                return new Float(first.floatValue() * second.floatValue());
            else
                return new Integer(first.intValue() * second.intValue());
        case DIV:
            if (first instanceof Float || second instanceof Float)
                return new Float(first.floatValue() / second.floatValue());
            else
                return new Integer(first.intValue() / second.intValue());
        case MOD:
            if (first instanceof Float || second instanceof Float)
                return new Integer((int) (first.floatValue() % second.floatValue()));
            else
                return new Integer(first.intValue() % second.intValue());                
        }
        // Better not to get here
        return new Integer(0);
    }
        
}
