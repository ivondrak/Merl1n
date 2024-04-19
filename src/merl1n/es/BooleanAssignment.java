package merl1n.es;
import merl1n.tool.*;

/**
 * The BooleanAssignment class assign variable an expression
 *
 * @author Ivo Vondrak
 * @version 1.0 09/28/99
 */ 
public class BooleanAssignment extends Assignment {
    /**
     * Expression
     */
    protected BooleanNode expression;
    
    /**
     * Gets expression
     */
    public BooleanNode getExpression() {
        return expression;
    }
    
    /**
     * Sets expression
     */
    public void setExpression(BooleanNode expression) {
        this.expression = expression;
    }        
    
    /**
     * Executes action part
     */
    public void execute() {
        if (variable.getType() == Public.BOOLEAN)
            variable.setValue(expression.evaluate());
    }
}