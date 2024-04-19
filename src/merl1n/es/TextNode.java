package merl1n.es;


/**
 * The TextNode class is a node of a derivation tree
 *
 * @author Ivo Vondrak
 * @version 1.0 09/27/99
 */ 
public class TextNode extends TreeNode {
    /**
     * Evaluate tree node
     */
    public Object evaluate() {
        String result = "";
        for (int i=0; i < this.getNumOfChildren(); i++) {
            Object o = this.getChild(i).evaluate();
            result = result+o;
        }
        return result;
    }
        
}
