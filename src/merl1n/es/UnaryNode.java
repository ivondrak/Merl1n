package merl1n.es;


/**
 * The UnaryNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class UnaryNode extends TreeNode {
    /**
     * Minus
     */
    public static final int MINUS = 1;
    
    /**
     * Not
     */
    public static final int NOT = 2;
            
    /**
     * Evaluate tree node
     */
    public Object evaluate() {
        Object object = this.getChild(0).evaluate();
        if (operator == 0)
            return object;
        if (object instanceof Boolean) {
            Boolean bool = (Boolean) object;
            return new Boolean(!bool.booleanValue());
        }
        else {
            if (object instanceof Integer) {
                Integer integer = (Integer) object;
                return new Integer(-1*integer.intValue());
            }
            else {
                Float f = (Float) object;
                return new Float(-1*f.doubleValue());
            }
        }
    }        
}
