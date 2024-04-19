package merl1n.es;

import merl1n.tool.*;

/**
 * The Print class sends message to es output
 *
 * @author Ivo Vondrak
 * @version 2.0 09/21/2001
 */ 
public class Print extends Action {
    /**
     * Reference to project
     */
    protected Project project;
    
    /**
     * Expression
     */
    protected TextNode expression;
    
    /**
     * Gets project
     */
    public Project getProject() {
        return project;
    }
    
    /**
     * Sets project
     */
    public void setProject(Project project) {
        this.project = project;
    }  
    
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
        project.print((String) expression.evaluate());
    }
    
    /**
     * Gets action type
     */
    public int getType() {
        return Public.PRINT;
    }
}