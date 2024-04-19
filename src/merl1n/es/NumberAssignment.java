package merl1n.es;

import java.io.*;

import merl1n.tool.*;

/**
 * The NumberAssignment class assign variable an expression
 *
 * @author Ivo Vondrak
 * @version 1.0 09/28/99
 */ 
public class NumberAssignment extends Assignment {
    
    /**
     * Expression
     */
    protected NumericalNode expression;
    
    /**
     * Gets expression
     */
    public NumericalNode getExpression() {
        return expression;
    }
    
    /**
     * Sets expression
     */
    public void setExpression(NumericalNode expression) {
        this.expression = expression;
    }        
    
    /**
     * Executes action part
     */
    public void execute() {
        if (variable.getType() == Public.INTEGER)
            variable.setValue(new Integer(((Number) expression.evaluate()).intValue()));
        if (variable.getType() == Public.REAL)
            variable.setValue(new Float(((Number) expression.evaluate()).floatValue()));
    }
}