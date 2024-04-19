package merl1n.es;

import java.util.*;

import merl1n.tool.*;

/**
 * The Exec class executes another module
 *
 * @author Ivo Vondrak
 * @version 1.1 10/06/99
 */ 
public class Exec extends Action {
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
        Module module = null;
        String moduleName = (String) expression.evaluate();
        for (Enumeration modules = project.getModules().elements(); modules.hasMoreElements();) {
            Module m = (Module) modules.nextElement();
            if (m.getName().equals(moduleName)) {
                module = m;
                break;
            }
        }
        if (module != null)
            project.evaluateModule(module);
    }
    
    /**
     * Gets action type
     */
    public int getType() {
        return Public.EXEC;
    }
}