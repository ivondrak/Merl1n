package merl1n.es;

import merl1n.tool.*;

/**
 * The Assignment class assign variable an expression
 *
 * @author Ivo Vondrak
 * @version 1.0 09/28/99
 */ 
public abstract class Assignment extends Action {
    /**
     * Reference to variable
     */
    protected Variable variable;
    
    /**
     * Gets variable
     */
    public Variable getVariable() {
        return variable;
    }
    
    /**
     * Sets variable
     */
    public void setVariable(Variable variable) {
        this.variable = variable;
    } 
    
    /**
     * Gets action type
     */
    public int getType() {
        return Public.ASSIGN;
    }
}