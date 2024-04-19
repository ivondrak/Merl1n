package merl1n.es;
import merl1n.tool.*;

/**
 * The TextAssignment class assign variable an expression
 *
 * @author Ivo Vondrak
 * @version 1.0 09/28/99
 */ 
public class TextAssignment extends Assignment {
    
    /**
     * Expression
     */
    protected TextNode expression;
    
    /**
     * Gets expression
     */
    public TextNode getExpression() {
        return expression;
    }
    
    /**
     * Sets expression
     */
    public void setExpression(TextNode expression) {
        this.expression = expression;
    }        
    
    /**
     * Executes action part
     */
    public void execute() {
        if (variable.getType() == Public.TEXT)
            variable.setValue(expression.evaluate());
    }
}